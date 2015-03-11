           <?php if(isset($errors['resp'])){ ?>
           <div class="alert alert-danger"><strong>Error!!!</strong> <?php print $errors['resp'];?></div>
           <?php } ?>
           <form class="form-horizontal" action='' method="post">
             <legend class="text-primary">Select Packages</legend>
             <?php if(isset($errors['package'])){ ?>
             <div class="alert alert-danger"><strong>Error!!!</strong> <?php print $errors['package'];?></div>
             <?php } ?>
             <?php if (sizeof($packageList) > 0 ) { ?>
             <div class="form-group">
               <label class="control-label col-sm-3">Select Package(s)</label>
               <div class="controls col-sm-9 well well-lg">
                 <ul class="list-group">
                 <?php foreach ($packageList as $package) { ?>
                   <li class="list-group-item">
                     <div class="checkbox">
                       <label class="col-sm-12">
                         <input type="checkbox" name="package[][servicePackageRequest][packageId]" value="<?php print $package['id']; ?>" /><?php print $package['id']; ?>
                       </label>
                     </div>
                   </li>
                 <?php } ?>
                 </ul>
             </div>
             <?php } ?>
             <div class="form-group">
               <div class="controls col-sm-9 col-sm-offset-3">
                 <!--a href="/register/login" class="btn btn-default col-sm-offset-3">Back</a-->
                 <input type="reset" class="btn btn-default col-sm-offset-3" value="Reset">
                 <input type="submit" name="laststep" class="btn btn-primary col-sm-offset-4" value="Register"/>
               </div>
             </div>
           </form>
         </div>
       </div><!-- /.panel -->
     </div><!-- /.col-md-12 -->
  </div><!-- /.row-fluid -->
</div><!-- /.container-fluid -->
