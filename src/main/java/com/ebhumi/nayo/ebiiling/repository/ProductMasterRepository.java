package com.ebhumi.nayo.ebiiling.repository;

import com.ebhumi.nayo.ebiiling.dao.ProductMaster;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductMasterRepository extends  CrudRepository<ProductMaster,Integer> {
    Iterable<ProductMaster> findByCompanyId(Integer companyMasterId);

}
