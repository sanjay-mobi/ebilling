

<table>
  <tr>
    <td>Generate Bill For  company</td>
    <td >
      <select ng-model="companyMasterId"  >
        <option value="">--Select company for Billing --</option>
        <option ng-repeat="companyMaster in companyList" value="{{companyMaster.id}}">{{companyMaster.name}}</option>
      </select>
    </td>
  </tr>
  <tr>
    <td>Date of Invoice : </td>
    <td>
      <input type=text date-picker
             placeholder='MM/DD/YYYY'
             maxlength="10"
             ng-required='true'
             class='form-control'
             ng-model="invoice.dateOfInvoice" >
    </td>
  </tr>

  <tr>
    <td>Buy order no : </td>
    <td><input type="text" name="" value="" ng-model="invoice.buyerOrderNumber"></td>
  </tr>

  <tr>
    <td>buyer order date : </td>
    <td>
      <input type=text date-picker
             placeholder='MM/DD/YYYY'
             maxlength="10"
             ng-required='true'
             class='form-control'
             ng-model="invoice.buyerOrderDate" >
    </td>
  </tr>
</table>
<table>
  <tr>
    <td>Product code/style code </td>
    <td ng-repeat="album in sizesArray" > {{album.value}} </td>
    <td>Total Quantity </td>
  </tr>
  <tr ng-repeat="invoiceProduct in invoiceProductList track by $index">

    <td >
      <select ng-model="invoiceProduct.styleCodeId"  ng-change ="initializeDate($index)">
        <option value="">--Select style code --</option>
        <option ng-repeat="productMaster in productMasterArray" value="{{productMaster.id}}">{{productMaster.styleCode}}</option>
      </select>
    </td>
    <td ng-repeat="album in invoiceProduct.sizesArray" >
        <input type="number" ng-model="album.value" style="width: 4em; padding: 2px;" ng-blur ="findTotalQuantity($parent.$index)" >
    </td>

    <td><p >{{invoiceProduct.totalQuantity}}</p></td>
    <td> <input type="button" value="Remove" ng-click="removeProductFromInvoice($index)" > </td>
    <td> <input type="button" value="add more Record" ng-click="addProductToInvoice($index)" > </td>
  </tr>

  <tr>
    <!--<td><input type="submit" name="" value = "Add More Record" ng-click= "addProductToInvoice()">: </td> -->
    <td><input type="submit" name="" value="Generate Invoice " ng-click="generateInvoice()"></td>
  </tr>


</table>
