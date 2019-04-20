package com.ebhumi.nayo.ebiiling.repository;

import com.ebhumi.nayo.ebiiling.dao.CompanyType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyTypeRepository extends CrudRepository<CompanyType,Integer> {

    Iterable<CompanyType> findByCompanyId(Integer companyMasterId);
}
