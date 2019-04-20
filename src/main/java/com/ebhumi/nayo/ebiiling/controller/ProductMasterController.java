package com.ebhumi.nayo.ebiiling.controller;

import com.ebhumi.nayo.ebiiling.dao.CompanyMaster;
import com.ebhumi.nayo.ebiiling.dao.ProductMaster;
import com.ebhumi.nayo.ebiiling.repository.ProductMasterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductMasterController {

    @Autowired
    private ProductMasterRepository productMasterRepository;

    @RequestMapping(method = RequestMethod.POST, path = "/productMaster", consumes="application/json")
    public ResponseEntity<ProductMaster> save(@RequestBody ProductMaster productMaster){
        String str = productMaster.getSizes();

        productMaster =  productMasterRepository.save(productMaster);
        return new ResponseEntity<>(productMaster,HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.GET, path = "/productMaster1")
    public ResponseEntity<ProductMaster> find(@RequestParam Integer id){


        return new ResponseEntity<>(productMasterRepository.findOne(id),HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/productMaster")
    public ResponseEntity<Iterable<ProductMaster>> findAll(@RequestParam Integer companyMasterId){


        return new ResponseEntity<>(productMasterRepository.findByCompanyId(companyMasterId),HttpStatus.OK);
    }
}
