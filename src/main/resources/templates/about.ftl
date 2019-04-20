<table>
  <tr>
    <td><h1>selected company is :---  {{companyMap[companyId].name}}</h1</td>
  </tr>
  <tr>
    <td>select the company </td>
    <td>
      <select ng-model="companyId" >
        <option value="">--Select company --</option>
        <option ng-repeat="company in companyList" value="{{company.id}}">{{company.name}}</option>
      </select>
    </td>
  </tr>
  <tr>
    <td><input type="submit" name="" value="select Company" ng-click="changeCompany()"></td>
  </tr>
</table>
