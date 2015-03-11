<?php
/**
 * Copyright (C) 2015 Covisint.
 * Author: Pradeep Patro <pradeep.patro@happiestminds.com>
 */

Class Template {

  /**
   * @the registry
   * @access private
   */
  private $registry;

  /**
   * @Variables array
   * @access private
   */
  private $vars = array();

  /**
   * @constructor
   * @access public
   * @return void
   */
  function __construct($registry) {
    $this->registry = $registry;
  }


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
   * @access public
   * @description Includes view files
   * @param string $name
   * @return void
   */
  public function show($name) {
    $path = __SITE_PATH . '/views' . '/' . $name . '.tpl.php';

    if (file_exists($path) == false) {
      throw new Exception('Template not found in '. $path);
      return false;
    }

    // Load variables
    foreach ($this->vars as $key => $value) {
      $$key = $value;
    }

    include ($path);
  }

  /**
   * @access public
   * @description Internally calls show method to render views
   * @param array $list
   * @return void
   */
  public function includeFiles($list) {
    foreach ($list as $file) {
      $this->show($file);
    }
  }

}
?>
