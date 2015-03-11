<div class='container'>
  <div class='row'>
     <div class="col-md-12" style="min-height: 500px;">
       <legend><h3>Register</h3></legend>
       <?php if ($offline) { ?>
       <div class="alert alert-danger"><strong>Error!!!</strong> Server is not responding currently. Please try again later.</div>
       <?php
            print "</div></div></div>";
            include_once(__SITE_PATH . '/views/footer.tpl.php');
            die();
          }
       ?>
       <ul class="nav nav-pills nav-stacked col-md-3">
         <li class="<?php print ($activeAction == 'search_org')? 'active': 'disabled'; ?>">
           <a href="#">Search Orginizations
             <?php if (array_key_exists('search_org', $completedSteps)) { ?>
             <span class="glyphicon glyphicon-ok pull-right text-success"></span>
             <?php } ?>
           </a>
         </li>
         <li class="<?php print ($activeAction == 'user_inf')? 'active': 'disabled'; ?>">
           <a href="#">Enter User Information
             <?php if (array_key_exists('user_inf', $completedSteps)) { ?>
             <span class="glyphicon glyphicon-ok pull-right text-success"></span>
             <?php } ?>
           </a>
         </li>
         <li class="<?php print ($activeAction == 'login')? 'active': 'disabled'; ?>">
           <a href="#">Create User Id & Password
             <?php if (array_key_exists('login', $completedSteps)) { ?>
             <span class="glyphicon glyphicon-ok pull-right text-success"></span>
             <?php } ?>
           </a>
         </li>
         <li class="<?php print ($activeAction == 'search_ques')? 'active': 'disabled'; ?>">
           <a href="#">Security Questions
             <?php if (array_key_exists('search_ques', $completedSteps)) { ?>
             <span class="glyphicon glyphicon-ok pull-right text-success"></span>
             <?php } ?>
           </a>
         </li>
         <li class="<?php print ($activeAction == 'select_package')? 'active': 'disabled'; ?>">
           <a href="#">Select Package
             <?php if (array_key_exists('select_package', $completedSteps)) { ?>
             <span class="glyphicon glyphicon-ok pull-right text-success"></span>
             <?php } ?>
           </a>
         </li>
       </ul>
       <div class="panel panel-default col-md-9">
         <div class="panel-body" style="min-height: 600px;">
