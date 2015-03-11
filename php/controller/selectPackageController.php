<?php
/**
 * Copyright (C) 2015 Covisint.
 * Author: Pradeep Patro <pradeep.patro@happiestminds.com>
 */

Class selectPackageController Extends baseController {

  /**
   * @route /register/select_package
   */
  public function index() {

    //redirecting to login step if user_inf step gets completed
    if (isset($_SESSION['covisint']['completed_steps']['select_package']) &&
        $_SESSION['covisint']['completed_steps']['select_package'] == true) {
      header('Location: '.$this->registry->server_path.'/register/success');
    } else if (!isset($_SESSION['covisint']['completed_steps']['search_ques'])) {
      header('Location: '.$this->registry->server_path.'/register/search_question');
    } else if (!isset($_SESSION['covisint']['completed_steps']['login'])) {
      header('Location: '.$this->registry->server_path.'/register/login');
    } else if (!isset($_SESSION['covisint']['completed_steps']['user_inf'])) {
      header('Location: '.$this->registry->server_path.'/register/user_information');
    } else if (!isset($_SESSION['covisint']['completed_steps']['search_org'])) {
      header('Location: '.$this->registry->server_path.'/register');
    }
    if (empty($_SESSION['covisint']['packages'])) {
      $packagesList = $this->getPackagesList();
      if (is_array($packagesList) && !empty($packagesList)) {
        $_SESSION['covisint']['packages'] = $packagesList;
      }
    }

    $this->registry->template->offline = $_SESSION['offline_status'];
    if (!empty($_SESSION['covisint']['packages']) && sizeof($_SESSION['covisint']['packages']) > 0) {
      $packageList = array();
      foreach($_SESSION['covisint']['packages'] as $key => $package) {
        $packageList[] = $package['servicePackage'];
      }
      $this->registry->template->packageList = $packageList;
    } else {
      $this->registry->template->offline = true;
    }



    if (isset($_POST) && $_POST['laststep'] == 'Register') {
      $error = array();
      if (!isset($_POST['package']) || (isset($_POST['package']) && sizeof($_POST['package']) <= 0)) {
        $error['package'] = "You haven't selected any package. Please select and complete the final step.";
      }

      if (!empty($errors)) {
        $this->registry->template->errors = $errors;
      } else {
        $selectedPackageLength = sizeof($_POST['package']);
        if ($selectedPackageLength > 0) {
          $personCreatedResponse = array();
          $personRequestSettings = $_SESSION['covisint_settings']['person_request'];
          $url = $personRequestSettings['url'];
          $method = $personRequestSettings['type'];
          $headers = $personRequestSettings['header'];
          $headers['Authorization'] = $_SESSION['covisint']['token'];
          foreach ($_POST['package'] as $packageDetail) {
            $registerDetails = array(
              'justification' => 'Requested from PHP demo app',
              'registrant' => array(
                'type' => 'person',
                'id' => $_SESSION['covisint']['user_inf']['id']
              )
            );
            $registerData = array_merge($registerDetails, $packageDetail);
            $data = json_encode($registerData, true);
            $personCreatedResponse[] = Register::requestApi($headers, $url, $method, $data);
          }
        }

        foreach($personCreatedResponse as $package) {
          if (!isset($package['status'])) {
            $_SESSION['covisint']['selected_package'][] = $package['servicePackageRequest']['packageId'];
          }
        }

        if (!empty($_SESSION['covisint']['selected_package'])) {
          $_SESSION['covisint']['completed_steps']['select_package'] = true;
          header('Location: '.$this->registry->server_path.'/success');
        } else {
          $error['resp'] = "We couldn't register you. Please try again selecting package and register.";
          $this->registry->template->errors = $errors;
        }
      }
    }

    $this->registry->template->activeAction = "select_package";
    $this->registry->template->completedSteps = $_SESSION['covisint']['completed_steps'];
    $this->registry->template->includeFiles(array('header', 'navbar', 'register', 'select_package', 'footer'));
  }

  /**
   * @description Get packages list
   * @return array $questions
   */
  private function getPackagesList() {
    $selectPackageSettings = $_SESSION['covisint_settings']['select_package'];
    $url = str_replace('{organizationId}', $_SESSION['covisint']['search_org']['id'], $selectPackageSettings['url']);
    $method = $selectPackageSettings['type'];
    $headers = $selectPackageSettings['header'];
    $headers['Authorization'] = $_SESSION['covisint']['token'];
    $packages = Register::requestApi($headers, $url, $method);
    return $packages;
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
