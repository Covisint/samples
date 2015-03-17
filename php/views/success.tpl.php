<div class="container">
  <div class="row">
    <div class="col-md-12" style="min-height:600px">
      <legend><h2 class="text-success">Congratulations. Your registration is completed successfully.</h2></legend>
      <div class="col-sm-8">
        <div class="panel panel-success">
          <div class="panel-heading">
            <h3 class="panel-title">Infomation</h3>
          </div>
          <table class="table table-bordered table-condensed">
            <tr>
              <th>Name</th>
              <td><?php print $_SESSION['covisint']['user_inf']['name']['prefix']." ".$_SESSION['covisint']['user_inf']['name']['given']." ".$_SESSION['covisint']['user_inf']['name']['M']. " ". $_SESSION['covisint']['user_inf']['name']['surname']; ?></td>
            </tr>
            <tr>
              <th>Username</th>
              <td><?php print $_SESSION['covisint']['login']['username']; ?></td>
            </tr>
            <tr>
              <th>Email Id</th>
              <td><?php print $_SESSION['covisint']['user_inf']['email'];?></td>
            </tr>
            <tr>
              <th>Phone Number</th>
              <td><?php print $_SESSION['covisint']['user_inf']['phones'][0]['number'];?></td>
            </tr>
            <tr>
              <th>Address</th>
              <td>
                  <address>
                     <?php print implode($_SESSION['covisint']['user_inf']['addresses'][0]['streets'], ',');?><br/>
                     <?php print $_SESSION['covisint']['user_inf']['addresses'][0]['city'];?>, <?php print $_SESSION['covisint']['user_inf']['addresses'][0]['state'];?><br/>
                     <?php print $_SESSION['covisint']['user_inf']['addresses'][0]['country'];?>, <?php print $_SESSION['covisint']['user_inf']['addresses'][0]['postal'];?><br/>
                  </address>
              </td>
            </tr>
            <tr>
              <th>Organization</th>
              <td><?php print $_SESSION['covisint']['search_org']['name'];?></td>
            </tr>
            <tr>
              <th>Expiry Date</th>
              <td><?php $dt = new DateTime($_SESSION['covisint']['login']['expiry']);
print $dt->format('d-m-Y H:i:s');?></td>
            </tr>
            <tr>
              <th>Selected Security Questions</th>
              <td>
              <?php
                  print "<ul class='list-unstyled'>";
                  foreach ($questions as $question) {
                      print "<li><span class='badge badge-primary'>".$question."</span></li>";
                  }
                  print "</ul>";
              ?>
              </td>
            </tr>
            <tr>
              <th>Selected Packages</th>
              <td>
              <?php
                  print "<ul class='list-unstyled'>";
                  foreach ($_SESSION['covisint']['selected_package'] as $package) {
                      print "<li><span class='label label-primary'>".$package."</span></li>";
                  }
                  print "</ul>";
              ?>
              </td>
            </tr>
          </table>
        </div>
      </div>
    </div>
  </div>
</div>
