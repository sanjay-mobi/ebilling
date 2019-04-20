package com.ebhumi.nayo.ebiiling.dao;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity(name = "company_master")
@Table(name = "company_master")
public class CompanyMaster  implements Serializable {

    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    @Column(name = "company_id" )
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Set<Address> addresses ;

    @Column(name = "gst_applicable", nullable = false)
    private boolean gstApplicable;

    @Column(name = "gst_no")
    private String gstNumber;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private List<CompanyType> companyType;

    @Column(name = "invoice_prefix")
    private String invoiceNumberPrefix;

    @Column(name = "invoice_counter")
    private int invoiceCounter = 1;

    /*@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private List<ProductMaster> productMasters;*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public boolean isGstApplicable() {
        return gstApplicable;
    }

    public void setGstApplicable(boolean gstApplicable) {
        this.gstApplicable = gstApplicable;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public List<CompanyType> getCompanyType() {
        return companyType;
    }

    public void setCompanyType(List<CompanyType> companyType) {
        this.companyType = companyType;
    }
    public int getInvoiceCounter(){
        return invoiceCounter;
    }

    public void setInvoiceCounter(int invoiceCounter){
        this.invoiceCounter = invoiceCounter;
    }
    public String getInvoiceNumberPrefix() {
        return invoiceNumberPrefix;
    }

    public void setInvoiceNumberPrefix(String invoiceNumberPrefix) {
        this.invoiceNumberPrefix = invoiceNumberPrefix;
    }
}
