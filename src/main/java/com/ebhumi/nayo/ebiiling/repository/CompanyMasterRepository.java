package com.ebhumi.nayo.ebiiling.repository;

import com.ebhumi.nayo.ebiiling.dao.CompanyMaster;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyMasterRepository  extends CrudRepository<CompanyMaster,Integer > {
    @Query("select  company from company_master as company, company_type as type where company.id = type.companyId and type.type ='party'")
    Iterable<CompanyMaster> findByCompanyType();
}
