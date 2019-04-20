package com.ebhumi.nayo.ebiiling.controller;

import com.ebhumi.nayo.ebiiling.dao.ProductMaster;

import java.io.Serializable;
import java.util.List;

public class InvoiceProduct implements Serializable {
    private String dateOfInvoice;
    private String buyerOrderNumber;
    private String buyerOrderDate;
    private String styleCode;
    private List<SizeAndQuanity> sizesArray;
    private ProductMaster productMaster;
    private String totalQuantity;

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public ProductMaster getProductMaster() {
        return productMaster;
    }

    public void setProductMaster(ProductMaster productMaster) {
        this.productMaster = productMaster;
    }

    public String getDateOfInvoice() {
        return dateOfInvoice;
    }

    public void setDateOfInvoice(String dateOfInvoice) {
        this.dateOfInvoice = dateOfInvoice;
    }

    public String getBuyerOrderNumber() {
        return buyerOrderNumber;
    }

    public void setBuyerOrderNumber(String buyerOrderNumber) {
        this.buyerOrderNumber = buyerOrderNumber;
    }

    public String getBuyerOrderDate() {
        return buyerOrderDate;
    }

    public void setBuyerOrderDate(String buyerOrderDate) {
        this.buyerOrderDate = buyerOrderDate;
    }

    public String getStyleCode() {
        return styleCode;
    }

    public void setStyleCode(String styleCode) {
        this.styleCode = styleCode;
    }

    public List<SizeAndQuanity> getSizesArray() {
        return sizesArray;
    }

    public void setSizesArray(List<SizeAndQuanity> sizesArray) {
        this.sizesArray = sizesArray;
    }
}
class SizeAndQuanity{
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
