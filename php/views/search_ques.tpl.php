          <form class="form-horizontal" action='' method="post">
             <legend class="text-primary">Select & Answer Security Question</legend>
             <div class="alert alert-info">
               <strong><span class="text-danger">*</span> fields are required</strong>
               <button class="close" data-dismiss="alert">&times;</button>
             </div>


             <?php if (sizeof($questionList) > 0 ) { ?>
             <div class="form-group <?php print ((isset($errors['question1_id']) || isset($errors['question1_ans']))? 'has-error': ''); ?>">
               <label class="control-label col-sm-3">Select Question 1<span class="text-danger">*</span></label>
               <div class="controls col-sm-9">
                 <select class="form-control" required name="questions[0][question][id]">
                 <option disabled value='' selected>Select question</option>
                 <?php foreach($questionList as $key => $ques) {
                   print "<option value='".$ques['id']."'>".$ques['question']."</option>"; } ?>
                 </select>
                 <?php if(isset($errors['question1_id'])){ ?>
                 <p class="help-block"><?php print $errors['question1_id'];?></p>
                 <?php } ?>
                 <br/>
                 <input type="text" class="form-control" placeholder="Answer to question 1" name="questions[0][answer]" required autocomplete="off"/>
                 <?php if(isset($errors['question1_ans'])){ ?>
                 <p class="help-block"><?php print $errors['question1_ans'];?></p>
                 <?php } ?>
               </div>
             </div>
             <?php } ?>

             <?php if (sizeof($questionList) > 0 ) { ?>
             <div class="form-group">
               <label class="control-label col-sm-3">Select Question 2</label>
               <div class="controls col-sm-9">
                 <select class="form-control" name="questions[1][question][id]">
                 <option selected value='' disabled>Select question</option>
                 <?php foreach($questionList as $key => $ques) {
                   print "<option value='".$ques['id']."'>".$ques['question']."</option>"; } ?>
                 </select>
                 <br/>
                 <input type="text" class="form-control" placeholder="Answer to question 2" name="questions[1][answer]" autocomplete="off"/>
               </div>
             </div>
             <?php } ?>
             <div class="form-group">
               <div class="controls col-sm-9 col-sm-offset-3">
                 <!--a href="/register/login" class="btn btn-default col-sm-offset-3">Back</a-->
                 <input type="reset" class="btn btn-default col-sm-offset-3" value="Reset">
                 <input type="submit" name="fourthstep" class="btn btn-primary col-sm-offset-4" value="Next"/>
               </div>
             </div>
           </form>
         </div>
       </div><!-- /.panel -->
     </div><!-- /.col-md-12 -->
  </div><!-- /.row-fluid -->
</div><!-- /.container-fluid -->
