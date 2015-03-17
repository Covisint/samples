<?php
/**
 * Copyright (C) 2015 Covisint.
 * Author: Covisint
 */

Class registerController Extends baseController {

  /**
   * @route /register
   */
  public function index() {
    if (isset($_SESSION['covisint']['completed_steps']['success']) &&
      $_SESSION['covisint']['completed_steps']['success'] == true) {
      session_destroy();
      header('Location: '.$this->registry->server_path.'/register');
    }

    if (!isset($_SESSION['covisint'])) {
      $_SESSION['covisint'] = array();
      $_SESSION['covisint']['completed_steps'] = array();
      $_SESSION['covisint']['organizations'] = array();
      Register::getToken();
    }

    //redirecting to user_inf step if search_org step gets completed
    if (isset($_SESSION['covisint']['completed_steps']['search_org']) &&
        $_SESSION['covisint']['completed_steps']['search_org'] == true) {
      header('Location: '.$this->registry->server_path.'/register/user_information');
    }

    // Very first time fetching all organization list and details
    if (empty($_SESSION['covisint']['organizations'])) {
      $organizationsList = $this->organizations();
      if (is_array($organizationsList) && !empty($organizationsList)) {
        $_SESSION['covisint']['organizations'] = $organizationsList;
      }
    }

    $this->registry->template->offline = $_SESSION['offline_status'];
    if (!empty($_SESSION['covisint']['organizations']) && sizeof($_SESSION['covisint']['organizations']) > 0) {
      $orgList = array();
      foreach($_SESSION['covisint']['organizations'] as $key => $org) {
        $obj = array('id' => $org['id'], 'name' => $org['name']);
        array_push($orgList, $obj);
      }
      $this->registry->template->companyList = json_encode($orgList, true);
    }

    // handling submit
    if ($_POST['firststep'] == 'Next' && isset($_POST['org_id'])) {
      foreach($_SESSION['covisint']['organizations'] as $key => $org) {
        if ($org['id'] == $_POST['org_id']) {
          $_SESSION['covisint']['search_org'] = $org;
          $_SESSION['covisint']['completed_steps']['search_org'] = true;
          header('Location: '.$this->registry->server_path.'/register/user_information');
        }
      }
    }

    if ($_POST['ajax'] == 'true' && $_POST['getorg'] == true &&
      isset($_POST['comp_id']) && sizeof($_SESSION['covisint']['organizations']) > 0) {
      $op = $this->getOrganiZationDetails();
      if ($op == false) {
        header("HTTP/1.0 404 Not Found");
      } else {
        header('Content-Type: application/json');
        print $op;
      }
      die();
    }

    $this->registry->template->activeAction = "search_org";
    $this->registry->template->completedSteps = $_SESSION['covisint']['completed_steps'];
    $this->registry->template->includeFiles(array('header', 'navbar', 'register', 'search_org', 'footer'));
  }

  /**
   * @description Get organizations list
   * @return array $organizations
   */
  private function organizations() {
    $searchOrganizationSettings = $_SESSION['covisint_settings']['search_organization'];
    $url = $searchOrganizationSettings['url'];
    $method = $searchOrganizationSettings['type'];
    $headers = $searchOrganizationSettings['header'];
    $headers['Authorization'] = $_SESSION['covisint']['token'];
    $organizations = Register::requestApi($headers, $url, $method);
    return $organizations;
  }

  /**
   * @description getOrganiZationDetails
   */
  private function getOrganiZationDetails() {
    if ($_POST['ajax'] == 'true' && isset($_POST['comp_id']) && sizeof($_SESSION['covisint']['organizations']) > 0) {
      foreach($_SESSION['covisint']['organizations'] as $key => $org) {
        if ($_POST['comp_id'] == $org['id']) {
          $arr = array(
            'id'=> $org['id'],
            'name'=>$org['name'],
            'address'=>$org['addresses']
          );
          return json_encode($arr, true);
        }
      }
    } else {
      return false;
    }
  }

}
?>
