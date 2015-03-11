<?php
/**
 * Copyright (C) 2015 Covisint.
 * Author: Pradeep Patro <pradeep.patro@happiestminds.com>
 */

Class indexController Extends baseController {

  public function index() {
    if (isset($_SESSION['covisint']['completed_steps']['success']) &&
      $_SESSION['covisint']['completed_steps']['success'] == true) {
      session_destroy();
      header('Location: '.$this->registry->server_path.'/');
    }
    /*** load the index template ***/
    $this->registry->template->abs_path = $this->registry->server_path;
    $this->registry->template->activeAction = "login";
    $this->registry->template->includeFiles(array('header', 'navbar', 'home', 'footer'));
  }
}

?>
