<?php
/**
 * Copyright (C) 2015 Covisint.
 * Author: Pradeep Patro <pradeep.patro@happiestminds.com>
 */

Class Registry {

  /**
   * @the vars array
   * @access private
   */
  private $vars = array();

  /**
   * @set undefined vars
   * @param string $index
   * @param mixed $value
   * @return void
   */
  public function __set($index, $value) {
    $this->vars[$index] = $value;
  }

  /**
   * @get variables
   * @param mixed $index
   * @return mixed
   */
  public function __get($index) {
    return $this->vars[$index];
  }
}
?>
