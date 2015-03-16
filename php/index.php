<?php
/**
 * Copyright (C) 2015 Covisint.
 * Author: Covisint
 */

/*** error reporting on ***/
error_reporting(E_ERROR || E_PARSE);

/*** define the site path ***/
$site_path = realpath(dirname(__FILE__));
define ('__SITE_PATH', $site_path);
define ('__VIEW', __SITE_PATH . '/views/');

/*** include the init.php file ***/
include 'includes/init.php';

/*** load the router ***/
$registry->router = new Router($registry);

/*** set the controller path ***/
$registry->router->setPath (__SITE_PATH . '/controller');

/*** load up the template ***/
$registry->template = new template($registry);

/*** load the controller ***/
$registry->router->loader();

?>
