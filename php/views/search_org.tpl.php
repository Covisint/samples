          <form class="form-horizontal" action="" method="post">
             <legend class="text-primary">Select your organization</legend>
             <div class="alert alert-info">
               <strong><span class="text-danger">*</span> fields are required.</strong>
               <button class="close" data-dismiss="alert">&times;</button>
             </div>
             <div class="form-group">
               <label class="control-label col-sm-3">Search Orginization <span class="text-danger">*</span></label>
               <div class="controls col-sm-9">
                 <input type="text" id="organisationSearch" placeholder="e.g. Covisint." class="form-control" data-provide="typeahead" data-items="5" name="org_name" autocomplete="off" required/>
                 <input type="hidden" id="organisationSearchValue" name="org_id" required/>
                 <br/>
                 <table id="compPreview" class="table table-bordered table-condensed table-striped" style="display: none"></table>
               </div>
             </div>
             <div class="form-group">
               <div class="controls col-sm-9 col-sm-offset-3">
                 <input type="reset" class="btn btn-default col-sm-offset-3" value="Reset"/>
                 <input type="submit" name="firststep" class="btn btn-primary col-sm-offset-4" value="Next"/>
               </div>
             </div>
           </form>
         </div>
       </div><!-- /.panel -->
     </div><!-- /.col-md-12 -->
  </div><!-- /.row-fluid -->
</div><!-- /.container-fluid -->
<?php if (isset($companyList) && sizeof($companyList) > 0) { ?>
<script>
  var companyList = <?php print $companyList ?>;
</script>
<?php } ?>
