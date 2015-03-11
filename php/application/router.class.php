<?php
/**
 * Copyright (C) 2015 Covisint.
 * Author: Pradeep Patro <pradeep.patro@happiestminds.com>
 */

Class Router {
  /**
   * @the registry
   */
  private $registry;

  /**
   * @the controller path
   */
  private $path;

  private $args = array();

  public $file;

  public $controller;

  public $action;

  function __construct($registry) {
    $this->registry = $registry;
  }

  /**
   * @set controller directory path
   * @param string $path
   * @return void
   */
  function setPath($path) {

    /*** check if path i sa directory ***/
    if (is_dir($path) == false) {
      throw new Exception ('Invalid controller path: `' . $path . '`');
    }
    /*** set the path ***/
    $this->path = $path;
  }


  /**
   * @load the controller
   * @access public
   * @return void
   */
  public function loader() {
    /*** check the route ***/
    $this->getController();

    /*** if the file is not there diaf ***/
    if (is_readable($this->file) == false) {
      $this->file = $this->path.'/error404.php';
      $this->controller = 'error404';
    }

    /*** include the controller ***/
    include $this->file;

    /*** a new controller class instance ***/
    $class = $this->controller . 'Controller';
    $controller = new $class($this->registry);

    /*** check if the action is callable ***/
    if (is_callable(array($controller, $this->action)) == false) {
      $action = 'index';
    } else {
      $action = $this->action;
    }
    /*** run the action ***/
    $controller->$action();
  }

  /**
   * @get the controller
   * @access private
   * @return void
   */
  private function getController() {

    /*** get the route from the url ***/
    $route = (empty($_GET['rt'])) ? '' : $_GET['rt'];

    if (empty($route)) {
      $route = 'index';
    } else {
      /*** get the parts of the route ***/
      $parts = explode('/', $route);
      $partsLen = sizeof($parts) - 1;
      $controllerName = '';

      //making underscore based route to camelcase controllerName
      if (strpos($parts[$partsLen], '_') !== false) {
        $arr = explode('_', $parts[$partsLen]);
        for ($i = 0; $i < sizeof($arr); $i++) {
          if ($i > 0) {
            $controllerName .= ucfirst($arr[$i]);
          } else {
            $controllerName .= $arr[$i];
          }
        }
      } else {
        $controllerName = $parts[$partsLen];
      }

      // if controller exists then take that else check error controller
      $file = $this->path . '/' . $controllerName. "Controller.php";
      if (!empty($controllerName) && file_exists($file) == false) {
        $controllerName = 'error404';
      } else if (empty($controllerName)) {
        header('Location: '.$this->registry->server_path.'/'.$parts[$partsLen-1]);
      }

      $this->controller = $controllerName;

      if(isset( $parts[1])) {
        $this->action = $parts[1];
      }
    }

    if (empty($this->controller)) {
      $this->controller = 'index';
    }

    /*** Get action ***/
    if (empty($this->action)) {
      $this->action = 'index';
    }

    /*** set the file path ***/
    $this->file = $this->path .'/'. $this->controller . 'Controller.php';
  }
}
?>
