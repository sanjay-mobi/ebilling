<!DOCTYPE html>
<html lang="en" dir="ltr">

<head>
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta charset="utf-8">
  <title>bhumi invoice system</title>
  <link rel="stylesheet" href="js/css/main.css" />
</head>

<body ng-app="bhumi">
  <div class="content">


    <div class="topnav">
                  <a ui-sref-active="active"  ui-sref="companyMaster">Create Company</a>
                  <a ui-sref-active="active" ui-sref="createProduct">Create Product </a>
                  <a ui-sref-active="active" ui-sref="invoice" >Invoice</a>
                  <a ui-sref-active="active" ui-sref="about">About</a>
              </div>
      <div class="bodyContent">
          <div ui-view >

          </div>
      </div>

  </div>



<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.5.0/angular.js"> </script>

      <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-ui-router/1.0.1/angular-ui-router.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/angular-resource/1.5.0/angular-resource.min.js"></script>
        <script type="text/javascript" src="js/app.js"></script>


</body>
</html>
