package com.ebhumi.nayo.ebiiling.dao;


import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "company_type")
@Table(name = "company_type")
public class CompanyType  implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "type" , nullable = false)
    private String type;

    @Column(name = "company_id")
    private int companyId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
}
