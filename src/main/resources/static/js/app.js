'use strict';
var bhumi = angular.module("bhumi", ['ui.router', 'ngResource']);

bhumi.factory('company', ['$rootScope', function ($rootScope) {

    var company = {

       id:1
    }


    return company;
}]);
bhumi.factory('companyService', ['$rootScope', function ($rootScope) {

    var service = {

        model: {
            id: 1,
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
  var sizes = [
  {
        "selected": false,
        "value": "XS"
      },{
      "selected": false,
      "value": "S"
    },
    {
      "selected": false,
      "value": "M"
    },
    {
      "selected": false,
      "value": "L"

    },
    {
      "selected": false,
      "value": "XL"

    },
    {
      "selected": false,
      "value": "XXL"
    },
    {
          "selected": false,
          "value": "XXXL"
     }
     ,
         {
               "selected": false,
               "value": "FS/MT"
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
   companyService.model={};
   companyService.model.id =1;
   companyService.model.companyName ='';

   companyService.SaveState();
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
        $scope.sizesArray[i].selected = false;

      }

    }, function(error) {
      console.log(error);
    });

  }


}]);

bhumi.controller('InvoiceController', ['$scope', 'generateInvoice', 'saveProductMaster','companyService', 'brandSize','saveCompanyMaster','company',function($scope, generateInvoice, saveProductMaster,companyService,brandSize,saveCompanyMaster,company) {
  $scope.sizesArray = brandSize;
  $scope.myDate = new Date();
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
    $scope.companyId = 1;
  }
  saveProductMaster.get({
  //-- TODO -- remove this hard code company id
    "companyMasterId":1
  }, {}, function(response) {
    $scope.productMasterArray = response;

    for (var i = 0; i < $scope.productMasterArray.length; i++) {
      $scope.productMasterMap[$scope.productMasterArray[i].id] = $scope.productMasterArray[i];
      var str = $scope.productMasterArray[i].sizes;
      var sizesArray = $scope.sizesArray;

      $scope.productMasterArray[i].sizesArray = [];
      for (var j = 0; j < sizesArray.length; j++) {

        $scope.productMasterArray[i].sizesArray[j] = {};
        $scope.productMasterArray[i].sizesArray[j].name = sizesArray[j].value;
        $scope.productMasterArray[i].sizesArray[j].value;
      }


    }
    $scope.invoiceProductList[0] ={};
    $scope.invoiceProductList[0].sizesArray=[];
    for( i =0; i< $scope.sizesArray.length; i++){
        $scope.invoiceProductList[0].sizesArray[i] = {};
        $scope.invoiceProductList[0].sizesArray[i].name = $scope.sizesArray[i].value;
        $scope.invoiceProductList[0].sizesArray[i].value=0;
    }

    $scope.invoiceProductList[0].totalQuantity =0;

  }, function(error) {
    console.log(error);
  });
  $scope.companyMasterId;
saveCompanyMaster.get({},function(response){
  $scope.companyList = response;
  $scope.companyMap ={};
  for (var i = 0; i < $scope.companyList.length; i++) {
    $scope.companyMap[$scope.companyList[i].id] = $scope.companyList[i];
  }
},function (error) {

});
$scope.removeProductFromInvoice = function($index){
    if($index !=0 ){
        $scope.invoiceProductList.splice($index,1);
    }
}
$scope.generateInvoice = function(){
var  length = $scope.invoiceProductList.length;
 $scope.invoiceProductList[length-1].styleCode = $scope.productMasterMap[$scope.invoiceProductList[length-1].styleCodeId].styleCode;
    $scope.invoiceProductList[length-1].productMaster = $scope.productMasterMap[$scope.invoiceProductList[length-1].styleCodeId];

  generateInvoice.save({partyCompanyId:$scope.companyMasterId,companyId : company.id},$scope.invoiceProductList,function (response) {
    console.log(response);
     $scope.invoiceProductList=[];
     $scope.invoiceProductList[0] ={};
         $scope.invoiceProductList[0].sizesArray=[];
         for( var i =0; i< $scope.sizesArray.length; i++){
             $scope.invoiceProductList[0].sizesArray[i] = {};
             $scope.invoiceProductList[0].sizesArray[i].name = $scope.sizesArray[i].value;
             $scope.invoiceProductList[0].sizesArray[i].value=0;
         }

         $scope.invoiceProductList[0].totalQuantity =0;

    window.open("http://localhost:8080/getPdf?fileName="+response.filename);

  },function (error) {

    console.log(error);

  });

}

$scope.initializeDate = function($index){

        $scope.invoiceProductList[$index].buyerOrderDate  = $scope.invoice.buyerOrderDate;
        $scope.invoiceProductList[$index].buyerOrderNumber  = $scope.invoice.buyerOrderNumber;
        $scope.invoiceProductList[$index].dateOfInvoice  = $scope.invoice.dateOfInvoice;

        }
$scope.addProductToInvoice = function ($index){
    $scope.invoiceProductList[$index].styleCode = $scope.productMasterMap[$scope.invoiceProductList[$index].styleCodeId].styleCode;
    $scope.invoiceProductList[$index].productMaster = $scope.productMasterMap[$scope.invoiceProductList[$index].styleCodeId];
   // delete $scope.invoiceProductList[$index].styleCodeId;
    //$scope.invoiceProductList[$index].
    //add new object to this array

         var obj ={};

        obj.sizesArray=[];
        obj.buyerOrderDate  = $scope.invoice.buyerOrderDate;
        obj.buyerOrderNumber  = $scope.invoice.buyerOrderNumber;
        obj.dateOfInvoice  = $scope.invoice.dateOfInvoice;
        for( var i =0; i< $scope.sizesArray.length; i++){
                obj.sizesArray[i] = {};
                obj.sizesArray[i].name = $scope.sizesArray[i].value;
                obj.sizesArray[i].value=0;
            }
           obj.totalQuantity =0;
        $scope.invoiceProductList.push(obj);

}
$scope.findTotalQuantity = function($index){
    $scope.invoiceProductList[$index].totalQuantity  =0;
    for (var i = 0; i < $scope.invoiceProductList[$index].sizesArray.length; i++) {
             $scope.invoiceProductList[$index].totalQuantity  +=  $scope.invoiceProductList[$index].sizesArray[i ].value;
      }
      // also need to calculate the total quantity when generating the invoice

}

}]);


bhumi.controller('DownloadInvoiceController', ['$scope', 'companyService','saveCompanyMaster','company',function($scope,companyService,saveCompanyMaster,company) {

    $scope.openInvoice  = function($fileName){
    var regex = /\//gi ;
    $fileName = $fileName.replace(regex, '_');
//--TODO remove hard coded url and its port and /context path /url
        window.open("http://localhost:8080/getPdf?fileName="+$fileName);
    }


}]);



bhumi.controller('AboutController', ['$scope', 'companyService','saveCompanyMaster','company',function($scope,companyService,saveCompanyMaster,company) {
$scope.companyId=null;
 companyService.RestoreState();
 if(companyService.model != undefined){
   $scope.companyId =companyService.model.id;
   company.id= $scope.companyId;
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

    })
    .state('download_invoice', {
          url: '/',
          templateUrl: 'partials/downloadInvoice',
          controller: 'DownloadInvoiceController',
          controllerAs: 'ctrl'

        });

  $urlRouterProvider.otherwise('/');
});



(function(){

  var bhumi = angular.module('bhumi');

  bhumi.directive('datePicker', function(){
    return{
      restrict: 'A',
      require: 'ngModel',
      link: function(scope, elm, attr, ctrl){

        // Format date on load
        ctrl.$formatters.unshift(function(value) {
          if(value && moment(value).isValid()){
               return moment(new Date(value)).format('MM/DD/YYYY');
          }
          return value;
        })

        //Disable Calendar
        scope.$watch(attr.ngDisabled, function (newVal) {
          if(newVal === true)
            $(elm).datepicker("disable");
          else
            $(elm).datepicker("enable");
        });

        // Datepicker Settings
        elm.datepicker({
          autoSize: true,
          changeYear: true,
          changeMonth: true,
          dateFormat: attr["dateformat"] || 'mm/dd/yy',
          showOn: 'button',
          buttonText: '<i class="glyphicon glyphicon-calendar"></i>',
          onSelect: function (valu) {
            scope.$apply(function () {
                ctrl.$setViewValue(valu);
            });
            elm.focus();
          },

           beforeShow: function(){
             debugger;
            if(attr["minDate"] != null)
                $(elm).datepicker('option', 'minDate', attr["minDate"]);

            if(attr["maxDate"] != null )
                $(elm).datepicker('option', 'maxDate', attr["maxDate"]);
          },


        });
      }
    }
  });

})();