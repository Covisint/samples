<?php
/**
 * Copyright (C) 2015 Covisint.
 * Author: Covisint
 */

Class loginController Extends baseController {

  /**
   * @route /register/login
   */
  public function index() {
    //redirecting to login step if user_inf step gets completed
    if (isset($_SESSION['covisint']['completed_steps']['login']) &&
        $_SESSION['covisint']['completed_steps']['login'] == true) {
      header('Location: '.$this->registry->server_path.'/register/search_question');
    } else if (!isset($_SESSION['covisint']['completed_steps']['user_inf'])) {
      header('Location: '.$this->registry->server_path.'/register/user_information');
    } else if (!isset($_SESSION['covisint']['completed_steps']['search_org'])) {
      header('Location: '.$this->registry->server_path);
    }
    $passwordPolicyId = $_SESSION['covisint']['search_org']['passwordPolicy']['id'];
    $authenticationPolicyId = $_SESSION['covisint']['search_org']['authenticationPolicy']['id'];

    /**
     * Password Policy Information
     */
    /**
     * if (!isset($_SESSION['covisint']['password_policy']) ||
     *   (isset($_SESSION['covisint']['password_policy']) && empty($_SESSION['covisint']['password_policy']))) {
     *   $passwordPolicy = $this->getPasswordPolicy($passwordPolicyId);
     *   $_SESSION['covisint']['password_policy'] = $passwordPolicy;
     * } else {
     *   $passwordPolicy = $_SESSION['covisint']['password_policy'];
     * }
     * if (is_array($passwordPolicy)) {
     *   $this->registry->template->offline = false;
     *   $policy = array();
     *   $passwordCreationRules = $passwordPolicy['rules'][0];
     *   $passwordValidationRules = $passwordPolicy['rules'][1];

     *   foreach ($passwordCreationRules as $key => $rule) {
     *     switch ($key) {
     *       case "allowUpperChars":
     *         if ($rule == 1) {
     *           $policy[] = "Uppercase characters are allowed. (A-Z).";
     *         } else {
     *           $policy[] = "Uppercase characters are not allowed. (A-Z).";
     *         }
     *         break;
     *       case "allowLowerChars":
     *         if ($rule == 1) {
     *           $policy[] = "Lowercase characters are allowed. (a-z).";
     *         } else {
     *           $policy[] = "Lowercase characters are not allowed. (a-z).";
     *         }
     *         break;
     *       case "allowNumChars":
     *         if ($rule == 1) {
     *           $policy[] = "Numerals are allowed. (0-9).";
     *         } else {
     *           $policy[] = "Numerals are not allowed. (0-9).";
     *         }
     *         break;
     *       case "allowSpecialChars":
     *         if ($rule == 1) {
     *           $policy[] = "Special characters are allowed. (! @ $ # % & ...).";
     *         } else {
     *           $policy[] = "Special characters are not allowed. (! @ $ # % & ...).";
     *         }
     *         break;
     *       case "requiredNumberOfCharClasses":
     *         if ($rule > 0) {
     *           $policy[] = "Should contain any ".$rule." of the above allowed rules.";
     *         }
     *         break;
     *     }
     *   }
     *   foreach ($passwordValidationRules as $key => $rule) {
     *     switch ($key) {
     *       case "min":
     *         $policy[] = "Minimum ".$rule." characters are required.";
     *         break;
     *       case "max":
     *         $policy[] = "Maximum ".$rule." characters are allowed.";
     *         break;
     *     }
     *   }
     *   $this->registry->template->passwordPolicyDescription = $policy;
     * } else {
     *   $this->registry->template->offline = true;
     * }
     */

    if (isset($_POST) && $_POST['thirdstep'] == 'Next') {
      $errors = array();
      if (!isset($_POST['username']) || (isset($_POST['username']) &&
        (sizeof($this->test_input($_POST['username'])) == 0 ||
          $this->test_input($_POST['username']) == ''))) {
        $errors['username'] = "Required & cannot be empty.";
      }

      if (!isset($_POST['password'])) {
        $errors['password'] = "Required & cannot be empty.";
      } else if (isset($_POST['password']) && sizeof(preg_replace('/\s+/', '', $_POST['password'])) == 0) {
        $errors['password'] = "Required & cannot be empty.";
      } else if (preg_match("/\\s/", $_POST['password'])) {
        $errors['password'] = "Spaces are not allowed in password.";
      }

      if (!isset($_POST['rpassword'])) {
        $errors['rpassword'] = "Required & cannot be empty.";
      } else if (isset($_POST['rpassword']) && sizeof(preg_replace('/\s+/', '', $_POST['rpassword'])) == 0) {
        $errors['rpassword'] = "Required & cannot be empty.";
      } else if (preg_match("/\\s/", $_POST['password'])) {
        $errors['rpassword'] = "Spaces are not allowed in password.";
      } else if ($_POST['password'] !== $_POST['rpassword']) {
        $errors['rpassword'] = "Reentered password is not matching the above given password.";
      }


      if (!empty($errors)) {
        $this->registry->template->errors = $errors;
        $this->registry->template->submitted = $_POST;
      } else {
        $addPasswordArray = array(
          'version' => 1,
          'username' => $_POST['username'],
          'password' => $_POST['password'],
          'passwordPolicyId' => $passwordPolicyId,
          'authenticationPolicyId' => $authenticationPolicyId,
          'subject' => $_SESSION['covisint']['user_inf']['id']
        );
        $createIdPasswordSettings = $_SESSION['covisint_settings']['add_password'];
        $url = str_replace('{personId}', $_SESSION['covisint']['user_inf']['id'], $createIdPasswordSettings['url']);
        $method = $createIdPasswordSettings['type'];
        $headers = $createIdPasswordSettings['header'];
        $headers['Authorization'] = $_SESSION['covisint']['token'];
        $data = json_encode($addPasswordArray, true);
        $createUserIdPasswordResponse = Register::requestApi($headers, $url, $method, $data);
        if (is_array($createUserIdPasswordResponse)) {
          if (array_key_exists('apiMessage', $createUserIdPasswordResponse) && $createUserIdPasswordResponse['status'] == 400) {
            $errors['resp'] = $createUserIdPasswordResponse['apiMessage'];
            $this->registry->template->errors = $errors;
            $this->registry->template->submitted = $_POST;
          } else {
            $_SESSION['covisint']['login'] = $createUserIdPasswordResponse;
            $_SESSION['covisint']['completed_steps']['login'] = true;
            header('Location: '.$this->registry->server_path.'/register/search_question');
          }
        } else {
          $errors['resp'] = "We are unable to create the userid with given information. Please try again later.";
          $this->registry->template->errors = $errors;
          $this->registry->template->submitted = $_POST;
        }
      }
    }

    $this->registry->template->userInformation = $_SESSION['covisint']['user_inf'];
    $this->registry->template->activeAction = "login";
    $this->registry->template->completedSteps = $_SESSION['covisint']['completed_steps'];
    $this->registry->template->includeFiles(array('header', 'navbar', 'register', 'login', 'footer'));
  }

  private function getPasswordPolicy ($id) {
    $passwordPolicySettings = $_SESSION['covisint_settings']['select_password_policy'];
    $url = $passwordPolicySettings['url'];
    $method = $passwordPolicySettings['type'];
    $headers = $passwordPolicySettings['header'];
    $headers['Authorization'] = $_SESSION['covisint']['token'];
    $passwordPolicies = Register::requestApi($headers, $url, $method);
    if (is_array($passwordPolicies)) {
      foreach ($passwordPolicies as $passwordPolicy) {
        if ($id == $passwordPolicy['id']) {
         return $passwordPolicy;
        }
      }
    } else {
      // Error case gives false as response
      return $passwordPolicies;
    }
  }

  /**
   * @description checking password if according to policy
   * @param string $passwd : User inputted passwordPolicyId
   *        array $charPolicy : password character required policy
   *        array $lengthPolicy: password length validation policy
   * @return string|bool
   */
  private function checkPasswordValidity($passwd, $charPolicy, $lengthPolicy) {

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
