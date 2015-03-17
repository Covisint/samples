<?php
/**
 * Copyright (C) 2015 Covisint.
 * Author: Covisint
 */

Class error404Controller Extends baseController {

  public function index() {
    $this->registry->template->blog_heading = 'This is the 404';
    $this->registry->template->includeFiles(array('header', 'navbar','error404', 'footer'));
  }
}
?>
