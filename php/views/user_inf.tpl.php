           <?php if(isset($errors['resp'])){ ?>
           <div class="alert alert-danger"><strong>Error!!!</strong> <?php print $errors['resp'];?></div>
           <?php } ?>
           <form class="form-horizontal" action='' method="post">
             <legend class="text-primary">Enter following information</legend>
             <div class="alert alert-info">
               <strong><span class="text-danger">*</span> fields are required.</strong>
               <button class="close" data-dismiss="alert">&times;</button>
             </div>
             <div class="form-group">
               <label class="control-label col-sm-3">Organisation Name</label>
               <div class="controls col-sm-9">
                 <h5><?php print $_SESSION['covisint']['search_org']['name']; ?></h5>
                 <input type="hidden" name="organizationId" value="<?php print $_SESSION['covisint']['search_org']['id']; ?>"/>
               </div>
             </div>
             <div class="form-group">
               <label class="col-sm-3 control-label">Prefix</label>
               <div class="controls col-sm-9">
                 <input type="text" placeholder='e.g. (Mr., Mrs., Ms., Miss.)' name="name[prefix]" class="form-control" autocomplete="off" value="<?php print $submitted['name']['prefix']; ?>"/>
               </div>
             </div>
             <div class="form-group <?php print (isset($errors['given'])? 'has-error': ''); ?>" >
               <label class="col-sm-3 control-label">First Name<span class="text-danger">*</span></label>
               <div class="controls col-sm-9">
                 <input type="text" name="name[given]" class="form-control" autocomplete="off" required value="<?php print $submitted['name']['given']; ?>"/>
                 <?php if(isset($errors['given'])){ ?>
                 <p class="help-block"><?php print $errors['given'];?></p>
                 <?php } ?>
               </div>
             </div>
             <div class="form-group">
               <label class="col-sm-3 control-label">Middle Name</label>
               <div class="controls col-sm-9">
                 <input type="text" name="name[M]" class="form-control" autocomplete="off" value="<?php print $submitted['name']['M']; ?>"/>
               </div>
             </div>
             <div class="form-group <?php print (isset($errors['surname'])? 'has-error': ''); ?>" >
               <label class="col-sm-3 control-label">Last Name<span class="text-danger">*</span></label>
               <div class="controls col-sm-9">
                 <input type="text" name="name[surname]" class="form-control" autocomplete="off" required value="<?php print $submitted['name']['surname']; ?>"/>
                 <?php if(isset($errors['surname'])){ ?>
                 <p class="help-block"><?php print $errors['surname'];?></p>
                 <?php } ?>
               </div>
             </div>
             <div class="form-group">
               <label class="col-sm-3 control-label">Job Title</label>
               <div class="controls col-sm-9">
               <input type="text" name="title" class="form-control" autocomplete="off" value="<?php print $submitted['title']; ?>"/>
               </div>
             </div>
             <div class="form-group <?php print (isset($errors['address1'])? 'has-error"': ''); ?>" >
               <label class="col-sm-3 control-label">Address 1<span class="text-danger">*</span></label>
               <div class="controls col-sm-9">
                 <textarea name="addresses[0][streets][]" class="form-control" style="resize:vertical;" required ><?php print (isset($submitted['addresses'][0]['streets'][0]) ? $submitted['addresses'][0]['streets'][0] : (preg_replace('/(\s+)|^([\s]*NA[\s]*)$/', ' ', $_SESSION['covisint']['search_org']['addresses'][0]['streets'][0]))); ?></textarea>
                 <?php if(isset($errors['address1'])){ ?>
                 <p class="help-block"><?php print $errors['address1'];?></p>
                 <?php } ?>
               </div>
             </div>
             <div class="form-group">
               <label class="col-sm-3 control-label">Address 2</label>
               <div class="controls col-sm-9">
                 <textarea name="addresses[0][streets][]" class="form-control" style="resize:vertical;"><?php print (isset($submitted['addresses'][0]['streets'][1]) ? $submitted['addresses'][0]['streets'][1] : (preg_replace('/(\s+)|^([\s]*NA[\s]*)$/', ' ', $_SESSION['covisint']['search_org']['addresses'][0]['streets'][1]))); ?></textarea>
               </div>
             </div>
             <div class="form-group">
               <label class="col-sm-3 control-label">Address 3</label>
               <div class="controls col-sm-9">
                 <textarea name="addresses[0][streets][]" class="form-control" style="resize:vertical;"><?php print (isset($submitted['addresses'][0]['streets'][2]) ? $submitted['addresses'][0]['streets'][2] : (preg_replace('/\s+|^[\s]*NA[\s]*$/', ' ', $_SESSION['covisint']['search_org']['addresses'][0]['streets'][2]))); ?></textarea>
               </div>
             </div>
             <div class="form-group <?php print (isset($errors['city'])? 'has-error': ''); ?>">
               <label class="col-sm-3 control-label">City/Region<span class="text-danger">*</span></label>
               <div class="controls col-sm-9">
                 <input type="text" name="addresses[0][city]" class="form-control" required value="<?php print (isset($submitted['addresses'][0]['city']) ? $submitted['addresses'][0]['city'] : $_SESSION['covisint']['search_org']['addresses'][0]['city']); ?>" autocomplete="off" />
                 <?php if(isset($errors['city'])){ ?>
                 <p class="help-block"><?php print $errors['city'];?></p>
                 <?php } ?>
               </div>
             </div>
             <div class="form-group <?php print (isset($errors['state'])? 'has-error': ''); ?>" >
               <label class="col-sm-3 control-label">State/Province<span class="text-danger">*</span></label>
               <div class="controls col-sm-9">
                 <input type="text" name="addresses[0][state]" class="form-control" required value="<?php print (isset($submitted['addresses'][0]['state']) ? $submitted['addresses'][0]['state'] : $_SESSION['covisint']['search_org']['addresses'][0]['state']); ?>" autocomplete="off" />
                 <?php if(isset($errors['state'])){ ?>
                 <p class="help-block"><?php print $errors['state'];?></p>
                 <?php } ?>
               </div>
             </div>
             <div class="form-group <?php print (isset($errors['postal'])? 'has-error': ''); ?>">
               <label class="col-sm-3 control-label">Postal Code<span class="text-danger">*</span></label>
               <div class="controls col-sm-9">
                 <input type="text" name="addresses[0][postal]" class="form-control" required value="<?php print (isset($submitted['addresses'][0]['postal']) ? $submitted['addresses'][0]['postal'] : $_SESSION['covisint']['search_org']['addresses'][0]['postal']); ?>" autocomplete="off"/>
                 <?php if(isset($errors['postal'])){ ?>
                 <p class="help-block"><?php print $errors['postal'];?></p>
                 <?php } ?>
               </div>
             </div>
             <div class="form-group <?php print (isset($errors['country'])? 'has-error': ''); ?>">
               <label class="col-sm-3 control-label">Country<span class="text-danger">*</span></label>
               <div class="controls col-sm-9">
                 <input id="countryTypeahed" type="text" name="country" class="form-control" required data-provide="typeahead" data-items="5" autocomplete="off" value="<?php print $submitted['country']; ?>"/>
                 <input id="countryValue" type="hidden" name="addresses[0][country]" value="<?php print (isset($submitted['addresses'][0]['country']) ? $submitted['addresses'][0]['country'] : $_SESSION['covisint']['search_org']['addresses'][0]['country']); ?>"/>
                 <?php if(isset($errors['country'])){ ?>
                 <p class="help-block"><?php print $errors['country'];?></p>
                 <?php } ?>
               </div>
             </div>
             <div class="form-group <?php print (isset($errors['phone_main'])? 'has-error': ''); ?>">
               <label class="col-sm-3 control-label">Phone Number<span class="text-danger">*</span></label>
               <div class="controls col-sm-9">
                 <div class="input-group">
                   <span class="input-group-addon">
                     <span class="phone-index"></span>
                     <input type="hidden" name="phone[main][phone_index]" />
                   </span>
                   <input type="text" name="phone[main][number]" class="form-control" autocomplete="off" required value="<?php print $submitted['phone']['main']['number']; ?>"/>
                 </div>
                 <?php if(isset($errors['phone_main'])){ ?>
                 <p class="help-block"><?php print $errors['phone_main'];?></p>
                 <?php } ?>
               </div>
             </div>
             <div class="form-group <?php print (isset($errors['phone_mobile'])? 'has-error': ''); ?>">
               <label class="col-sm-3 control-label">Mobile Phone Number</label>
               <div class="controls col-sm-9">
                 <div class="input-group">
                   <span class="input-group-addon">
                     <span class="phone-index"></span>
                     <input type="hidden" name="phone[mobile][phone_index]" />
                   </span>
                   <input type="text" name="phone[mobile][number]" class="form-control" autocomplete="off" value="<?php print $submitted['phone']['mobile']['number']; ?>"/>
                 </div>
                 <?php if(isset($errors['phone_mobile'])){ ?>
                 <p class="help-block"><?php print $errors['phone_mobile'];?></p>
                 <?php } ?>
               </div>
             </div>
             <div class="form-group <?php print (isset($errors['phone_fax'])? 'has-error': ''); ?>">
               <label class="col-sm-3 control-label">Fax Number</label>
               <div class="controls col-sm-9">
                 <div class="input-group">
                   <span class="input-group-addon">
                     <span class="phone-index"></span>
                     <input type="hidden" name="phone[fax][phone_index]" />
                   </span>
                   <input type="text" name="phone[fax][number]" class="form-control" autocomplete="off" value="<?php print $submitted['phone']['fax']['number']; ?>"/>
                 </div>
                 <?php if(isset($errors['phone_fax'])){ ?>
                 <p class="help-block"><?php print $errors['phone_fax'];?></p>
                 <?php } ?>
               </div>
             </div>
             <div class="form-group <?php print (isset($errors['email'])? 'has-error': ''); ?>">
               <label class="col-sm-3 control-label">Email Address<span class="text-danger">*</span></label>
               <div class="controls col-sm-9">
                 <input type="email" name="email" class="form-control" required autocomplete="off" value="<?php print $submitted['email']; ?>"/>
                 <?php if(isset($errors['email'])){ ?>
                 <p class="help-block"><?php print $errors['email'];?></p>
                 <?php } ?>
               </div>
             </div>
             <div class="form-group <?php print (isset($errors['remail'])? 'has-error': ''); ?>">
               <label class="col-sm-3 control-label">Re-enter Email Address<span class="text-danger">*</span></label>
               <div class="controls col-sm-9">
                 <input type="email" name="remail" class="form-control" required autocomplete="off" />
                 <?php if(isset($errors['remail'])){ ?>
                 <p class="help-block"><?php print $errors['remail'];?></p>
                 <?php } ?>
               </div>
             </div>
             <div class="form-group <?php print (isset($errors['timezone'])? 'has-error': ''); ?>">
               <label class="col-sm-3 control-label">Time Zone<span class="text-danger">*</span></label>
               <div class="controls col-sm-9">
                 <input id="timezoneTypeahed" type="text" name="timezone" class="form-control" required data-provide="typeahead" data-items="5" autocomplete="off" value="<?php print $submitted['timezone']; ?>" required/>
                 <?php if(isset($errors['timezone'])){ ?>
                 <p class="help-block"><?php print $errors['timezone'];?></p>
                 <?php } ?>
               </div>
             </div>
             <div class="form-group <?php print (isset($errors['language'])? 'has-error': ''); ?>">
               <label class="col-sm-3 control-label">Language Preference<span class="text-danger">*</span></label>
               <div class="controls col-sm-9">
                 <select name="language" class="form-control" required>
                   <option disabled>Select your preferred language</option>
                   <option selected value="en">English</option>
                 </select>
                 <?php if(isset($errors['language'])){ ?>
                 <p class="help-block"><?php print $errors['language'];?></p>
                 <?php } ?>
               </div>
             </div>
             <div class="form-group">
               <div class="controls col-sm-9 col-sm-offset-3">
                 <!--a href="/register" class="btn btn-default col-sm-offset-3">Back</a-->
                 <input type="reset" class="btn btn-default col-sm-offset-3" value="Reset">
                 <input type="submit" name="secondstep" class="btn btn-primary col-sm-offset-4" value="Next"/>
               </div>
             </div>
           </form>
         </div>
       </div><!-- /.panel -->
     </div><!-- /.col-md-12 -->
  </div><!-- /.row-fluid -->
</div><!-- /.container-fluid -->
<?php if (isset($countryList) && sizeof($countryList) > 0) { ?>
<script>
  var countryList = <?php print $countryList ?>;
</script>
<?php } ?>
