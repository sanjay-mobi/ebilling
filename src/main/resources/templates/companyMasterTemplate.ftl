<div>
  <form>

    <table>
      <tr>
        <td>Company Name : </td>
        <td><input type="text" ng-model="companyMaster.name"> </td>
      </tr>
      <tr>
        <td>GST Applicable : </td>
        <td>
          <select ng-model="companyMaster.gstApplicable">
            <option value="">--Select gst option --</option>
            <option ng-repeat="option in gstOption" value="{{option.name}}">{{option.value}}</option>
          </select>
        </td>
      </tr>
      <tr>
        <td>GST Number : </td>
        <td><input type="text" ng-model="companyMaster.gstNumber"> </td>
      </tr>

      <tr>
        <td>Company type : </td>
        <td>
          <select ng-model="companyMaster.companyType[0].type">
            <option value="">--Select company type --</option>
            <option ng-repeat="option in companyType" value="{{option.name}}">{{option.value}}</option>
          </select>
        </td>
      </tr>
      <tr>
        <td>Invoice Prefix : </td>
        <td><input type="text" ng-model="companyMaster.invoiceNumberPrefix"> </td>
      </tr>
      <tr>
        <td>InvoiceCounter : </td>
        <td><input type="text" ng-model="companyMaster.invoiceCounter"> </td>
      </tr>
      <tr>
        <td>Enter Billing Address</td>
        <td>
      </tr>
      <tr>
        <td>address Line 1:</td>
        <td><input type="text" name="" value="" ng-model="companyMaster.addresses[0].address1"></td>
      </tr>
      <tr>
        <td>address Line 2:</td>
        <td><input type="text" name="" value="" ng-model="companyMaster.addresses[0].address2"></td>
      </tr>
      <tr>
        <td>city:</td>
        <td><input type="text" name="" value="" ng-model="companyMaster.addresses[0].city"></td>
      </tr>
      <tr>
        <td>state:</td>
        <td><input type="text" name="" value="" ng-model="companyMaster.addresses[0].state"></td>
      </tr>
      <tr>
        <td>Pincode:</td>
        <td><input type="text" name="" value="" ng-model="companyMaster.addresses[0].pincode"></td>
      </tr>


      <!-- company shippping address -->

      <tr>
        <td>Enter Shipping Address </td>
        <td>Same : <input type="checkbox" ng-model="shippingAddressSame" ng-change="copyBillingToShippingAddress()"></td>
      </tr>

      <tr>
        <td>address Line 1:</td>
        <td><input type="text" name="" value="" ng-model="companyMaster.addresses[1].address1"></td>
      </tr>
      <tr>
        <td>address Line 2:</td>
        <td><input type="text" name="" value="" ng-model="companyMaster.addresses[1].address2"></td>
      </tr>
      <tr>
        <td>city:</td>
        <td><input type="text" name="" value="" ng-model="companyMaster.addresses[1].city"></td>
      </tr>
      <tr>
        <td>state:</td>
        <td><input type="text" name="" value="" ng-model="companyMaster.addresses[1].state"></td>
      </tr>
      <tr>
        <td>Pincode:</td>
        <td><input type="text" name="" value="" ng-model="companyMaster.addresses[1].pincode"></td>
      </tr>

      <tr>
        <td><input type="button" ng-click="save()" value="Save" name="Submit " /> </td>
      </tr>
    </table>

  </form>



</div>
