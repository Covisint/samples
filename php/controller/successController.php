<?php
/**
 * Copyright (C) 2015 Covisint.
 * Author: Covisint
 */

Class successController Extends baseController {

  public function index() {
    if (!isset($_SESSION['covisint']['completed_steps']['search_org'])) {
      header('Location: '.$this->registry->server_path.'/register');
    }
    /*** load the index template ***/
    $questionsName = array();
    $questionsList = array();
    foreach($_SESSION['covisint']['questions_list'] as $key => $ques) {
      if ($ques['question'][0]['text'] != 'None') {
        $questionsList[$ques['id']] = $ques['question'][0]['text'];
      }
    }
    foreach ($_SESSION['covisint']['questions']['questions'] as $ques) {
      if (array_key_exists($ques['question']['id'], $questionsList)) {
        $questionsName[] = $questionsList[$ques['question']['id']];
      }
    }
    $_SESSION['covisint']['completed_steps']['success'] = true;
    $this->registry->template->abs_path = $this->registry->server_path;
    $this->registry->template->questions = $questionsName;
    $this->registry->template->activeAction = "success";
    $this->registry->template->includeFiles(array('header', 'navbar', 'success', 'footer'));
  }

}

?>
