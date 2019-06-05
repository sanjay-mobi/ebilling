<!DOCTYPE html>
<html lang="en" dir="ltr">

<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta charset="utf-8">
  <title>bhumi invoice system</title>

  <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
  <link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/themes/smoothness/jquery-ui.css" />
  <link rel="stylesheet" href="js/css/main.css" />
</head>

<body ng-app="bhumi">
  <div class="content">


    <div class="topnav">
                  <a ui-sref-active="active"  ui-sref="companyMaster">Create Company</a>
                  <a ui-sref-active="active" ui-sref="createProduct">Create Product </a>
                  <a ui-sref-active="active" ui-sref="invoice" >Invoice</a>
                  <a ui-sref-active="active" ui-sref="download_invoice">Download Invoice</a>
              </div>
      <div class="bodyContent">
          <div ui-view >

          </div>
      </div>

  </div>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
  <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.9.2/jquery-ui.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/es5-shim/4.5.8/es5-shim.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.3/angular.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.19.2/moment.js"></script>


<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.0/angular.js"> </script>

      <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-router/1.0.1/angular-ui-router.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-resource/1.5.0/angular-resource.min.js"></script>
        <script type="text/javascript" src="js/app.js"></script>


</body>
</html>
