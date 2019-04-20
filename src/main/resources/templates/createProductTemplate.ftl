<div class="">

  <form class="">
    <table>
      <tr>
        <td>Style Code/ Product Code </td>
        <td><input type="text" name="" value="" ng-model="productMaster.styleCode"></td>
      </tr>
      <tr>
        <td>hsn code </td>
        <td><input type="text" name="" value="" ng-model="productMaster.hsnCode"></td>
      </tr>

      <tr>
        <td>Brand :</td>
        <td><input type="text" name="" value="" ng-model="productMaster.brand"></td>
      </tr>
      <tr>
        <td>Cost Price : </td>
        <td><input type="number" name="" value="" ng-model="productMaster.costPrice"></td>
      </tr>

      <tr>
        <td>MRP : </td>
        <td><input type="number" name="" value="" ng-model="productMaster.MRP"></td>
      </tr>

      <tr>
        <td>Color : </td>
        <td><input type="text" name="" value="" ng-model="productMaster.color"></td>
      </tr>
      <tr>
        <td>sizes : </td>
        <td>

          <ul style="list-style-type: none;">
            <li ng-repeat="album in sizesArray">
              <span id="content1">
                {{album.value}}

              </span>
                <span id="content2">
                  <input type="checkbox" ng-model="album.selected"  value={{album.value}} />

                </span>


            </li>
            </ul>

        </td>
      </tr>
      <tr>

        <td><input type="Submit" name="" value="Submit" ng-click="saveProductMaster()"></td>
      </tr>

    </table>

  </form>
</div>
