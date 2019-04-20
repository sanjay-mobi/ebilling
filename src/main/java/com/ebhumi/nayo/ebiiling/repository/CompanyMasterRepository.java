package com.ebhumi.nayo.ebiiling.repository;

import com.ebhumi.nayo.ebiiling.dao.CompanyMaster;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyMasterRepository  extends CrudRepository<CompanyMaster,Integer > {
}
