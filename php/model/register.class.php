<?php
/**
 * Copyright (C) 2015 Covisint.
 * Author: Covisint
 */

Class Register {

  private static $retry = 0;
  public static function getToken () {
    self::$retry++;
    $getTokenDetails = $_SESSION['covisint_settings']['get_token'];
    $url = $getTokenDetails['url'];
    $method = $getTokenDetails['type'];
    $headers = $getTokenDetails['header'];
    $getToken = Register::requestApi($headers, $url, $method);
    if (is_array($getToken)) {
      $_SESSION['covisint']['token'] = ucfirst($getToken['token_type']) . " " . $getToken['access_token'];
    }
    return (bool)!!$getToken;
  }

  public static function requestApi($header_section=array(), $uri='', $method='GET', $data=null) {
    $header_array = array();
    if (sizeof($header_section) > 0 ) {
      foreach ($header_section as $key => $header) {
        array_push($header_array, $key .': '. $header);
      }
    }

    $ch = curl_init();
    curl_setopt($ch, CURLOPT_CUSTOMREQUEST, $method);
    if ($method == 'POST') {
      curl_setopt($ch, CURLOPT_POST, true);
      curl_setopt($ch, CURLOPT_BINARYTRANSFER, true);
      curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
    } else if ($method == 'PUT') {
      curl_setopt($ch, CURLOPT_BINARYTRANSFER, true);
      curl_setopt($ch, CURLOPT_POSTFIELDS, $data);
    }

    curl_setopt($ch, CURLOPT_FOLLOWLOCATION, true);
    curl_setopt($ch, CURLOPT_URL, $uri);
    curl_setopt($ch, CURLOPT_HEADER, 0);
    curl_setopt($ch, CURLOPT_HTTPHEADER, $header_array);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_VERBOSE, false);
    curl_setopt($ch, CURLOPT_FRESH_CONNECT, true);
    curl_setopt($ch, CURLOPT_FORBID_REUSE, true);
    curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, false);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);

    $buffer = curl_exec($ch);
    $httpStatus = curl_getinfo($ch, CURLINFO_HTTP_CODE);

    if ($httpStatus == 401 && !$_SESSION['offline_status']) {
      // retrying only 3 times
      $_SESSION['covisint']['token'] = '';
      if (self::$retry <= 3 && empty($_SESSION['covisint']['token'])) {
        self::getToken();
      } else {
        unset($_SESSION['covisint']);
        return false;
      }
      $header_section['Authorization'] = $_SESSION['covisint']['token'];
      return self::requestApi($header_section, $uri, $method, $data);
    } else if ($httpStatus >= 200 && $httpStatus <= 400) {
      $_SESSION['offline_status'] = false;
      $buffer = json_decode($buffer, true);
      curl_close($ch);
      return $buffer;
    } else {
      //@TODO handle error by showing to user or write to a log file
      $_SESSION['offline_status'] = true;
      return false;
    }
  }
}
?>
