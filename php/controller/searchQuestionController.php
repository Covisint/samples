<?php
/**
 * Copyright (C) 2015 Covisint.
 * Author: Pradeep Patro <pradeep.patro@happiestminds.com>
 */

Class searchQuestionController Extends baseController {

  /**
   * @route /register/search_question
   */
  public function index() {

    //redirecting to login step if user_inf step gets completed
    if (isset($_SESSION['covisint']['completed_steps']['search_ques']) &&
        $_SESSION['covisint']['completed_steps']['search_ques'] == true) {
      header('Location: '.$this->registry->server_path.'/register/select_package');
    } else if (!isset($_SESSION['covisint']['completed_steps']['login'])) {
      header('Location: '.$this->registry->server_path.'/register/login');
    } else if (!isset($_SESSION['covisint']['completed_steps']['user_inf'])) {
      header('Location: '.$this->registry->server_path.'/register/user_information');
    } else if (!isset($_SESSION['covisint']['completed_steps']['search_org'])) {
      header('Location: '.$this->registry->server_path.'/register');
    }


    if (isset($_POST) && $_POST['fourthstep'] == "Next") {
      $errors = array();
      if (!isset($_POST['questions']) || (isset($_POST['questions']) &&
        is_array($_POST['questions']) && isset($_POST['questions'][0]['question']['id']) &&
        empty($_POST['questions'][0]['question']['id']))) {
        $errors['question1_id'] = "Required & cannot be empty.";
      }
      if (!isset($_POST['questions']) || (isset($_POST['questions']) &&
        is_array($_POST['questions']) && isset($_POST['questions'][0]['question']['id']) &&
        (sizeof($this->test_input($_POST['questions'][0]['answer'])) == 0 ||
          $this->test_input($_POST['questions'][0]['answer']) == ''))) {
        $errors['question1_ans'] = "Required & cannot be empty.";
      }


      if (!empty($errors)) {
        $this->registry->template->errors = $errors;
        $this->registry->template->submitted = $_POST;
      } else {
        $addQuestionData = array(
          'questions' => array(),
          'id' => $_SESSION['covisint']['user_inf']['id'],
          'version' => 1
        );

        foreach ($_POST['questions'] as $key => $data) {
          if (isset($data['answer']) && !empty($data['answer'])) {
            $data['question']['type'] = 'question';
            array_push($addQuestionData['questions'], $data);
          }
        }
        $addQuestionSettings = $_SESSION['covisint_settings']['add_security_question'];
        $url = str_replace('{personId}', $_SESSION['covisint']['user_inf']['id'], $addQuestionSettings['url']);
        $method = $addQuestionSettings['type'];
        $headers = $addQuestionSettings['header'];
        $headers['Authorization'] = $_SESSION['covisint']['token'];
        $data = json_encode($addQuestionData, true);
        $addQuestionResponse = Register::requestApi($headers, $url, $method, $data);
        if (is_array($addQuestionResponse)) {
          if (array_key_exists('apiMessage', $addQuestionResponse) && $addQuestionResponse['status'] == 400) {
            $errors['resp'] = $addQuestionResponse['apiMessage'];
            $this->registry->template->errors = $errors;
          } else {
            $_SESSION['covisint']['questions'] = $addQuestionResponse;
            $_SESSION['covisint']['completed_steps']['search_ques'] = true;
            header('Location: '.$this->registry->server_path.'/register/select_package');
          }
        } else {
          $errors['resp'] = "We are unable to create the userid with given information. Please try again later.";
          $this->registry->template->errors = $errors;
        }
      }

    }

    if (empty($_SESSION['covisint']['questions_list'])) {
      $questionsList = $this->getQuestions();
      if (is_array($questionsList) && !empty($questionsList)) {
        $_SESSION['covisint']['questions_list'] = $questionsList;
      }
    }

    $this->registry->template->offline = $_SESSION['offline_status'];
    if (!empty($_SESSION['covisint']['questions_list']) && sizeof($_SESSION['covisint']['questions_list']) > 0) {
      $questionList = array();
      foreach($_SESSION['covisint']['questions_list'] as $key => $ques) {
        if ($ques['question'][0]['text'] != 'None') {
          $questionList[] = array('id' => $ques['id'], 'question' => $ques['question'][0]['text']);
        }
      }
      $this->registry->template->questionList = $questionList;
    } else {
      $this->registry->template->offline = true;
    }

    $this->registry->template->activeAction = "search_ques";
    $this->registry->template->completedSteps = $_SESSION['covisint']['completed_steps'];
    $this->registry->template->includeFiles(array('header', 'navbar', 'register', 'search_ques', 'footer'));
  }

  /**
   * @description Get questions list
   * @return array $questions
   */
  private function getQuestions() {
    $securityQuestionSettings = $_SESSION['covisint_settings']['search_security_question'];
    $url = $securityQuestionSettings['url'];
    $method = $securityQuestionSettings['type'];
    $headers = $securityQuestionSettings['header'];
    $headers['Authorization'] = $_SESSION['covisint']['token'];
    $questions = Register::requestApi($headers, $url, $method);
    return $questions;
  }

  // checking data for extra characters
  private function test_input($data) {
    $data = trim($data);
    $data = preg_replace('/\s+/', ' ', $data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data);
    return $data;
  }
}
?>
