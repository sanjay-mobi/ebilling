<table style ="table-layout: fixed; width: 100%; " ng-hide = "invoiceProductList.length == 0">
    <tr>
      <td  >style code </td>
      <td > Quantity </td>
    </tr>
    <tr ng-repeat="productMaster in invoiceProductList" >
        <td>{{productMaster.styleCode}}</td>
        <td>
         <table>
           <tr ng-repeat="size in productMaster.sizesArray">
             <td >{{size.name}} = </td>
             <td >{{size.value}}</td>
           </tr>
           <tr>
             <td>total quantity : </td>
             <td>{{productMaster.totalQuantity}}</td>
           </tr>
         </table>
        </td>
      </tr>
</table>
<table>
  <tr>
    <td>Date of Invoice : </td>
    <td><input type="text" name="" value="" ng-model="invoice.dateOfInvoice"></td>
  </tr>

  <tr>
    <td>Buy order no : </td>
    <td><input type="text" name="" value="" ng-model="invoice.buyerOrderNumber"></td>
  </tr>

  <tr>
    <td>buyer order date : </td>
    <td><input type="text" name="" value="" ng-model="invoice.buyerOrderDate"></td>
  </tr>

  <tr>
    <td>Product code/style code </td>
    <td>
      <select ng-model="styleCodeId">
        <option value="">--Select style code --</option>
        <option ng-repeat="productMaster in productMasterArray" value="{{productMaster.id}}">{{productMaster.styleCode}}</option>
      </select>
    </td>
  </tr>

  <tr>
    <td>Quantity :- </td>
    <td>
      <ul style="list-style-type: none;">
        <li ng-repeat="album in productMasterMap[styleCodeId].sizesArray">
          <span id="content1">
            {{album.name}}

          </span>
          <span id="content2">
            <input type="number" ng-model="album.value" />

          </span>


        </li>
      </ul>

    </td>
  </tr>

  <tr>
  <td><input type="submit" name="" value = "Add More Record" ng-click= "addProductToInvoice()">: </td>
  <td><input type="submit" name="" value="Generate Invoice " ng-click="generateInvoice()"></td>
  </tr>


</table>
