package com.ebhumi.nayo.ebiiling.repository;

import com.ebhumi.nayo.ebiiling.dao.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Iterator;

@Repository
public interface AddressRepository extends CrudRepository<Address,Integer> {

    Iterable<Address> findByCompanyId(Integer companyMasterId);

}
