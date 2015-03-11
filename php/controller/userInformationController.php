<?php
/**
 * Copyright (C) 2015 Covisint.
 * Author: Pradeep Patro <pradeep.patro@happiestminds.com>
 */

Class userInformationController Extends baseController {

  /**
   * @route /register/user_information
   */
  public function index() {
    //redirecting to register if session expires
    if (!isset($_SESSION['covisint'])) {
      header('Location: '.$this->registry->server_path.'/register');
    }
    //redirecting to login step if user_inf step gets completed
    if (isset($_SESSION['covisint']['completed_steps']['user_inf']) &&
        $_SESSION['covisint']['completed_steps']['user_inf'] == true) {
      header('Location: '.$this->registry->server_path.'/login');
    } else if (!isset($_SESSION['covisint']['completed_steps']['search_org'])) {
      header('Location: '.$this->registry->server_path.'/register');
    }
    $country_list = array();
    $country_name = $_SESSION['covisint']['search_org']['addresses'][0]['country'];
    $index = 0;
    foreach($this->registry->country_list as $key => $list) {
      foreach ($list as $index => $detail) {
        if (!is_array($country_list[$index])) {
          $country_list[$index] = array();
        }
        $country_list[$index][$key] = $detail;
        if ($key == 'id' && $detail == $country_name) {
          $country_list[$index]['selected'] = true;
        }
      }
    }

    // handling submit
    $this->registry->template->submitted = array();
    if (isset($_POST) && $_POST['secondstep'] == 'Next') {
      $errors = array();
      if (!isset($_POST['organizationId']) ||
        (isset($_POST['organizationId']) && $_POST['organizationId'] !== $_SESSION['covisint']['search_org']['id'])) {
        session_destroy();
        header('Location: '.$this->registry->server_path);
      }

      if (!isset($_POST['name']['given']) || (isset($_POST['name']['given']) &&
        (sizeof($this->test_input($_POST['name']['given'])) == 0 ||
          $this->test_input($_POST['name']['given']) == ''))) {
        $errors['given'] = "Required & cannot be empty.";
      }

      if (!isset($_POST['name']['surname']) || (isset($_POST['name']['surname']) &&
        (sizeof($this->test_input($_POST['name']['surname'])) == 0 ||
          $this->test_input($_POST['name']['surname']) == ''))) {
        $errors['surname'] = "Required & cannot be empty.";
      }

      if (!isset($_POST['addresses'][0]['streets'][0]) || (isset($_POST['addresses'][0]['streets'][0]) &&
        (sizeof($this->test_input($_POST['addresses'][0]['streets'][0])) == 0 ||
          $this->test_input($_POST['addresses'][0]['streets'][0]) == ''))) {
        $errors['address1'] = "Required & cannot be empty.";
      }

      if (!isset($_POST['addresses'][0]['city']) || (isset($_POST['addresses'][0]['city']) &&
        (sizeof($this->test_input($_POST['addresses'][0]['city'])) == 0 ||
          $this->test_input($_POST['addresses'][0]['city']) == ''))) {
        $errors['city'] = "Required & cannot be empty.";
      }

      if (!isset($_POST['addresses'][0]['state']) || (isset($_POST['addresses'][0]['state']) &&
        (sizeof($this->test_input($_POST['addresses'][0]['state'])) == 0 ||
          $this->test_input($_POST['addresses'][0]['state']) == ''))) {
        $errors['state'] = "Required & cannot be empty.";
      }

      if (!isset($_POST['addresses'][0]['postal']) && (isset($_POST['addresses'][0]['postal']) &&
        (sizeof($this->test_input($_POST['addresses'][0]['postal'])) == 0 ||
          $this->test_input($_POST['addresses'][0]['postal']) == ''))) {
        $errors['country'] = "Required & cannot be empty.";
      }

      if (!isset($_POST['addresses'][0]['country']) || (isset($_POST['addresses'][0]['country']) &&
        (sizeof($this->test_input($_POST['addresses'][0]['country'])) == 0 ||
          $this->test_input($_POST['addresses'][0]['country']) == ''))) {
        $errors['country'] = "Required & cannot be empty.";
      }

      if (!isset($_POST['phone']['main']['number']) || (isset($_POST['phone']['main']['number']) &&
        (sizeof($this->test_input($_POST['phone']['main']['number'])) == 0 ||
          $this->test_input($_POST['phone']['main']['number']) == ''))) {
        $errors['phone_main'] = "Required & cannot be empty.";
      } else if (isset($_POST['phone']['main']['number'])) {
        $number = str_replace(array(" ", "-"), array("", ""), $_POST['phone']['main']['number']);
        if (!is_numeric($number)) {
          $errors['phone_main'] = "Invalid Phone Number.";
        }
      }

      if (isset($_POST['phone']['mobile']['number']) && !empty($_POST['phone']['mobile']['number'])) {
        $number = str_replace(array(" ", "-"), array("", ""), $_POST['phone']['mobile']['number']);
        if (!is_numeric($number)) {
          $errors['phone_mobile'] = "Invalid Mobile Number.";
        }
      }

      if (isset($_POST['phone']['fax']['number']) && !empty($_POST['phone']['fax']['number'])) {
        $number = str_replace(array(" ", "-"), array("", ""), $_POST['phone']['fax']['number']);
        if (!is_numeric($number)) {
          $errors['phone_fax'] = "Invalid Fax Number.";
        }
      }

      if (!isset($_POST['email']) || (isset($_POST['email']) &&
        !(bool)(filter_var($_POST['email'], FILTER_VALIDATE_EMAIL)))) {
        $errors['email'] = "Invalid email supplied.";
      }

      if (!isset($_POST['remail']) || (isset($_POST['email']) && isset($_POST['remail']) &&
        !(bool)(filter_var($_POST['remail'], FILTER_VALIDATE_EMAIL)) &&
        ($_POST['email'] != $_POST['remail']))) {
        $errors['remail'] = "Invalid email supplied or Not matching with the above.";
      }

      if (!isset($_POST['timezone']) || (isset($_POST['timezone']) &&
        (sizeof($this->test_input($_POST['timezone'])) == 0 ||
          $this->test_input($_POST['timezone']) == ''))) {
        $errors['timezone'] = "Required & cannot be empty.";
      }

      if (!isset($_POST['language']) || (isset($_POST['language']) &&
        (sizeof($this->test_input($_POST['language'])) == 0 ||
          $this->test_input($_POST['language']) == ''))) {
        $errors['language'] = "Required & cannot be empty.";
      }

      if (!empty($errors)) {
        $this->registry->template->errors = $errors;
        $this->registry->template->submitted = $_POST;
      } else {
        $userInformation = array(
          'organizationId' => $_POST['organizationId'],
          'name' => $_POST['name'],
          'title' => $_POST['title'],
          'addresses' => $_POST['addresses'],
          'phones' => array(),
          'email' => $_POST['email'],
          'timezone' => $_POST['timezone'],
          'language' => $_POST['language']
        );
        foreach($_POST['phone'] as $type => $details) {
          array_push($userInformation['phones'], array(
            'type' => $type,
            'number' => $details['phone_index'] ."-". $details['number']
          ));
        }

        $userInformationSettings = $_SESSION['covisint_settings']['create_person'];
        $url = $userInformationSettings['url'];
        $method = $userInformationSettings['type'];
        $headers = $userInformationSettings['header'];
        $headers['Authorization'] = $_SESSION['covisint']['token'];
        $data = json_encode($userInformation, true);
        $userCreatedResponse = Register::requestApi($headers, $url, $method, $data);
        if (is_array($userCreatedResponse)) {
          if (array_key_exists('apiMessage', $userCreatedResponse) && $userCreatedResponse['status'] == 400) {
            $errors['resp'] = $userCreatedResponse['apiMessage'];
            $this->registry->template->errors = $errors;
          } else {
            $_SESSION['covisint']['user_inf'] = $userCreatedResponse;
            $_SESSION['covisint']['completed_steps']['user_inf'] = true;
            header('Location: '.$this->registry->server_path.'/register/login');
          }
        } else {
          $errors['resp'] = "We are unable to create the person with given information. Please try again later.";
          $this->registry->template->errors = $errors;
          $this->registry->template->submitted = $_POST;
        }
      }
    }
    $this->registry->template->countryList = json_encode($country_list, true);
    $this->registry->template->activeAction = "user_inf";
    $this->registry->template->completedSteps = $_SESSION['covisint']['completed_steps'];
    $this->registry->template->includeFiles(array('header', 'navbar', 'register', 'user_inf', 'footer'));
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
