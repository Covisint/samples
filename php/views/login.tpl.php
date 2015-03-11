           <?php if(isset($errors['resp'])){ ?>
           <div class="alert alert-danger"><strong>Error!!!</strong> <?php print $errors['resp'];?></div>
           <?php } ?>
           <form class="form-horizontal" action='' method="post">
             <legend class="text-primary">Create User ID & Password</legend>
             <div class="alert alert-info">
               <strong><span class="text-danger">*</span> fields are required.</strong>
               <button class="close" data-dismiss="alert">&times;</button>
             </div>
             <div class="form-group">
               <label class="control-label col-sm-3">User ID</label>
               <div class="controls col-sm-9">
                 <h5 class="text-success"><?php print $userInformation['id'] ?></h5>
               </div>
             </div>
             <div class="form-group">
               <label class="control-label col-sm-3">Name</label>
               <div class="controls col-sm-9">
                 <h5 class="text-success"><?php print $userInformation['name']['prefix']." ".$userInformation['name']['given']." ".$userInformation['name']['M']. " ". $userInformation['name']['surname']; ?></h5>
               </div>
             </div>
             <div class="form-group">
               <label class="control-label col-sm-3">Email Id</span></label>
               <div class="controls col-sm-9">
                 <h5 class="text-success"><?php print $userInformation['email']; ?></h5>
               </div>
             </div>
             <div class="form-group <?php print (isset($errors['username'])? 'has-error': ''); ?>">
               <label class="col-sm-3 control-label">Username<span class="text-danger">*</span></label>
               <div class="controls col-sm-9">
                 <input type="text"  name="username" class="form-control" required autocomplete="off" placeholder="Enter one username ..." value="<?php print $submitted['username']; ?>"/>
                 <?php if(isset($errors['username'])){ ?>
                 <p class="help-block"><?php print $errors['username'];?></p>
                 <?php } ?>
               </div>
             </div>
             <?php if (sizeof($passwordPolicyDescription) > 0) { ?>
             <div class="form-group">
               <div class="controls col-sm-9 col-sm-offset-3">
                 <div class="alert alert-info">
                   <strong>Information!!!</strong>
                   <ul>
                     <?php   foreach($passwordPolicyDescription as $policy) {
                     print "<li>".$policy."</li>";
                     } ?>
                   </ul>
                 </div>
               </div>
             </div>
             <?php } ?>
             <div class="form-group <?php print (isset($errors['password'])? 'has-error': ''); ?>">
               <label class="col-sm-3 control-label">Password<span class="text-danger">*</span></label>
               <div class="controls col-sm-9">
                 <input type="password"  name="password" class="form-control" required autocomplete="off" placeholder="xxxxxxxx"/>
                 <?php if(isset($errors['password'])){ ?>
                 <p class="help-block"><?php print $errors['password'];?></p>
                 <?php } ?>
               </div>
             </div>
             <div class="form-group <?php print (isset($errors['rpassword'])? 'has-error': ''); ?>">
               <label class="col-sm-3 control-label">Re-enter Password<span class="text-danger">*</span></label>
               <div class="controls col-sm-9">
                 <input type="password"  name="rpassword" class="form-control" required autocomplete="off" placeholder="xxxxxxxx"/>
                 <?php if(isset($errors['rpassword'])){ ?>
                 <p class="help-block"><?php print $errors['rpassword'];?></p>
                 <?php } ?>
               </div>
             </div>
             <div class="form-group">
               <div class="controls col-sm-9 col-sm-offset-3">
                 <!--a href="/register/user_information" class="btn btn-default col-sm-offset-3">Back</a-->
                 <input type="reset" class="btn btn-default col-sm-offset-3" value="Reset">
                 <input type="submit" name="thirdstep" class="btn btn-primary col-sm-offset-4" value="Next"/>
               </div>
             </div>
           </form>
         </div>
       </div><!-- /.panel -->
     </div><!-- /.col-md-12 -->
  </div><!-- /.row-fluid -->
</div><!-- /.container-fluid -->
