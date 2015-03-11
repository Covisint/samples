<?php
/**
 * Copyright (C) 2015 Covisint.
 * Author: Pradeep Patro <pradeep.patro@happiestminds.com>
 */

//setting error to be displayed
ini_set('display_errors', true);
error_reporting(E_PARSE|E_WARNING|E_ERROR);

//setting infinite time for execution
ini_set("max_execution_time", 0);
set_time_limit(0);

//Making the datetime zone default to UTC
date_default_timezone_set("UTC");
session_start();

/*** include the controller class ***/
include __SITE_PATH . '/application/' . 'controller_base.class.php';

/*** include the registry class ***/
include __SITE_PATH . '/application/' . 'registry.class.php';

/*** include the router class ***/
include __SITE_PATH . '/application/' . 'router.class.php';

/*** include the template class ***/
include __SITE_PATH . '/application/' . 'template.class.php';

/*** auto load model classes ***/
function __autoload($class_name) {
  $filename = strtolower($class_name) . '.class.php';
  $file = __SITE_PATH . '/model/' . $filename;

  if (file_exists($file) == false) {
    return false;
  }
  include ($file);
}

/*** a new registry object ***/
$registry = new registry;
$protocol = (!empty($_SERVER['HTTPS']) && $_SERVER['HTTPS'] !== 'off' || $_SERVER['SERVER_PORT'] == 443) ? "https://" : "http://";
$registry->server_path = $protocol . $_SERVER['HTTP_HOST'] . preg_split('/\/index.php/', $_SERVER['PHP_SELF'])[0];
/*** create the settings object ***/
if (!isset($_SESSION['covisint_settings'])) {
  $ini_file = __SITE_PATH . '/includes/setting.ini';
  $ini_array = parse_ini_file($ini_file, true);
  $client_secret = base64_encode($ini_array['global']['client_id'].":".$ini_array['global']['client_secret']);
  $ini_array['get_token']['header']['Authorization'] = "Basic ".$client_secret;
  $_SESSION['covisint_settings'] = $ini_array;
}
if (!isset($_SESSION['offline_status'])) {
  $_SESSION['offline_status'] = true;
}

/*** create the country object ***/
$country_file = __SITE_PATH . '/includes/country.ini';
$country_array = parse_ini_file($country_file);
$registry->country_list = $country_array;

?>
