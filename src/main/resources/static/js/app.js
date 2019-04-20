'use strict';
var bhumi = angular.module("bhumi", ['ui.router', 'ngResource']);

bhumi.factory('companyService', ['$rootScope', function ($rootScope) {

    var service = {

        model: {
            id: '',
            companyName: ''
        },

        SaveState: function () {
            sessionStorage.userService = angular.toJson(service.model);
        },

        RestoreState: function () {
            service.model = angular.fromJson(sessionStorage.userService);
        }
    }

    $rootScope.$on("savestate", service.SaveState);
    $rootScope.$on("restorestate", service.RestoreState);

    return service;
}]);


bhumi.factory("brandSize", function() {
  var sizes = [{
      "selected": false,
      "value": "Small"
    },
    {
      "selected": false,
      "value": "Medium"
    },
    {
      "selected": false,
      "value": "Large"

    },
    {
      "selected": false,
      "value": "XL"

    },
    {
      "selected": false,
      "value": "XLL"
    }
  ];
  return sizes;
});

bhumi.factory('saveCompanyMaster', function($resource) {
  return $resource('/companyMaster', {}, {
    'save': {
      method: 'POST',
      isArray: false
    },'get': {
      method: 'GET',
      isArray: true
    }
  });
});
bhumi.factory('saveProductMaster', function($resource) {
  return $resource('/productMaster', {}, {
    'save': {
      method: 'POST',
      isArray: false
    },
    'get': {
      method: 'GET',
      isArray: true
    }
  });
});
bhumi.factory('generateInvoice', function($resource) {
  return $resource('/generateInvoice', {}, {
    'save': {
      method: 'POST',
      isArray: false
    }
  });
});

bhumi.controller('CompanyMasterController', ['$scope', 'saveCompanyMaster','companyService', function($scope, saveCompanyMaster,companyService) {
  $scope.companyMaster = {};
  $scope.gstOption = [{
      "name": true,
      "value": "True"
    },
    {
      "name": false,
      "value": "False"
    }
  ];
  $scope.companyType = [{
      "name": "supplier", //biller, who will genrate the bill
      "value": "Supplier"
    },
    {
      "name": "party",
      "value": "Party"
    }
  ];
  //  $scope.companyMaster.name = "Nayo";
  $scope.companyMaster.addresses = [];
  $scope.companyMaster.addresses[0] = {};
  $scope.companyMaster.addresses[1] = {};
  $scope.companyMaster.addresses[0].addressType = "billing";


  $scope.companyMaster.companyType = [];
  $scope.companyMaster.companyType[0] = {};
  $scope.companyMaster.companyType[0].id = 0;
  $scope.shippingAddressSame;
  $scope.copyBillingToShippingAddress = function() {
    if ($scope.shippingAddressSame == true) {
      $scope.companyMaster.addresses[1].address1 = $scope.companyMaster.addresses[0].address1;
      $scope.companyMaster.addresses[1].address2 = $scope.companyMaster.addresses[0].address2;
      $scope.companyMaster.addresses[1].city = $scope.companyMaster.addresses[0].city;
      $scope.companyMaster.addresses[1].state = $scope.companyMaster.addresses[0].state;
      $scope.companyMaster.addresses[1].pincode = $scope.companyMaster.addresses[0].pincode;
      $scope.companyMaster.addresses[1].addressType = "shipping";
    } else {
      $scope.companyMaster.addresses[1] = {};
    }
    console.log($scope.shippingAddressSame);
    console.log($scope.companyMaster.addresses);
  }

  $scope.save = function() {
    console.log("save clicked");
    saveCompanyMaster.save($scope.companyMaster,
      function(response) {
        console.log(response);
        //resetting the form....
        $scope.companyMaster = {};
        $scope.companyMaster.addresses = [];
        $scope.companyMaster.addresses[0] = {};
        $scope.companyMaster.addresses[1] = {};
        $scope.companyMaster.addresses[0].addressType = "billing";
        $scope.companyMaster.companyType = [];
        $scope.companyMaster.companyType[0] = {};
        $scope.companyMaster.companyType[0].id = 0;

      },
      function(error) {
        console.log(error);

      });

    //call save companyMaster service here
  }
}]);

bhumi.controller('ProductMasterController', ['$scope', 'saveProductMaster', 'brandSize', 'companyService',function($scope, saveProductMaster, brandSize,companyService) {
  console.log(brandSize);
  $scope.sizesArray = brandSize;
  $scope.productMaster = {};

  $scope.saveProductMaster = function() {
    var sizeString = "";
    for (var i = 0; i < $scope.sizesArray.length; i++) {
      if ($scope.sizesArray[i].selected == true) {
        sizeString = sizeString + "," + $scope.sizesArray[i].value;
      }

    }
      $scope.productMaster.companyId=companyService.model.id;
    $scope.productMaster.sizes = sizeString;
    saveProductMaster.save($scope.productMaster, function(response) {
      console.log(response);
      $scope.productMaster = {};
      for (var i = 0; i < $scope.sizesArray.length; i++) {
        $scope.sizesArra[i] = false;

      }

    }, function(error) {
      console.log(error);
    });

  }


}]);

bhumi.controller('InvoiceController', ['$scope', 'generateInvoice', 'saveProductMaster','companyService', function($scope, generateInvoice, saveProductMaster,companyService) {
  $scope.invoiceProductList = [];
  $scope.invoice = {};
  $scope.productMasterArray = [];
  $scope.styleCodeId;
  $scope.productMasterMap = {};
  companyService.RestoreState();
  companyService.RestoreState();

  if(companyService.model != undefined ){
    $scope.companyId = companyService.model.id;
  }else {
    companyService.model = {};
    $scope.companyId = 0;
  }
  saveProductMaster.get({
    "companyMasterId":$scope.companyId
  }, {}, function(response) {
    $scope.productMasterArray = response;

    for (var i = 0; i < $scope.productMasterArray.length; i++) {
      $scope.productMasterMap[$scope.productMasterArray[i].id] = $scope.productMasterArray[i];
      var str = $scope.productMasterArray[i].sizes;
      var sizesArray = str.split(',');

      $scope.productMasterArray[i].sizesArray = [];
      for (var j = 0; j < sizesArray.length; j++) {

        $scope.productMasterArray[i].sizesArray[j] = {};
        $scope.productMasterArray[i].sizesArray[j].name = sizesArray[j];
        $scope.productMasterArray[i].sizesArray[j].value;
      }


    }
  }, function(error) {
    console.log(error);
  });


  $scope.addProductToInvoice = function() {
    $scope.invoice.styleCode = $scope.productMasterMap[$scope.styleCodeId].styleCode;
    //$scope.invoice.totalQuantity = $scope.productMasterMap[$scope.styleCodeId].totalQuantity;
    $scope.invoice.sizesArray = $scope.productMasterMap[$scope.styleCodeId].sizesArray;
    var data = angular.copy($scope.invoice);
    var totalQuantity = 0;
    for (var i = 0; i < data.sizesArray.length; i++) {
      totalQuantity = totalQuantity + parseInt(data.sizesArray[i].value);
    }
    data.totalQuantity = totalQuantity;

    $scope.invoice.styleCode=null;
    $scope.invoice.sizesArray =[];
    data.productMaster = angular.copy($scope.productMasterMap[$scope.styleCodeId]);
    $scope.styleCodeId=null;
    delete data.productMaster.sizesArray;
    $scope.invoiceProductList.push(data);
  }

$scope.generateInvoice = function(){
  generateInvoice.save($scope.invoiceProductList,function (response) {
    console.log(response);

  },function (error) {

    console.log(error);

  });

}


}]);


bhumi.controller('AboutController', ['$scope', 'companyService','saveCompanyMaster',function($scope,companyService,saveCompanyMaster) {
$scope.companyId=null;
 companyService.RestoreState();
 if(companyService.model != undefined){
   $scope.companyId =companyService.model.id;
 }else{
   companyService.model={};
   $scope.companyId =1;
 }


//console.log(companyService.RestoreState());
saveCompanyMaster.get({},function(response){
  $scope.companyList = response;
  $scope.companyMap ={};
  for (var i = 0; i < $scope.companyList.length; i++) {
    $scope.companyMap[$scope.companyList[i].id] = $scope.companyList[i];
  }
},function (error) {

});
$scope.changeCompany = function () {
  companyService.model.id=$scope.companyId;

  companyService.model.companyName=  $scope.companyMap[$scope.companyId].name;
  companyService.SaveState();

}
}]);



// routes
bhumi.config(function($stateProvider, $urlRouterProvider) {
  $stateProvider
    .state('home', {
      url: '/',
      templateUrl: 'partials/companyMasterTemplate',
      controller: 'CompanyMasterController',
      controllerAs: 'ctrl'

    })
    .state('companyMaster', {
      url: '/',
      templateUrl: 'partials/companyMasterTemplate',
      controller: 'CompanyMasterController',
      controllerAs: 'ctrl'

    })
    .state('createProduct', {
      url: '/',
      templateUrl: 'partials/createProductTemplate',
      controller: 'ProductMasterController',
      controllerAs: 'ctrl'

    })
    .state('invoice', {
      url: '/',
      templateUrl: 'partials/invoiceTemplate',
      controller: 'InvoiceController',
      controllerAs: 'ctrl'

    })
    .state('about', {
      url: '/',
      templateUrl: 'partials/about',
      controller: 'AboutController',
      controllerAs: 'ctrl'

    });

  $urlRouterProvider.otherwise('/');
});
