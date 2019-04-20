package com.ebhumi.nayo.ebiiling.controller;

import com.ebhumi.nayo.ebiiling.dao.Address;
import com.ebhumi.nayo.ebiiling.dao.CompanyType;
import com.ebhumi.nayo.ebiiling.repository.CompanyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CompanyTypeController {

    @Autowired
    private CompanyTypeRepository companyTypeRepository;

    @RequestMapping(method = RequestMethod.POST, path = "/companyType")
    public ResponseEntity<CompanyType> save(@RequestBody CompanyType companyType){

        companyType = companyTypeRepository.save(companyType);

        return new ResponseEntity<>(companyType, HttpStatus.OK);
    }
    @RequestMapping(method = RequestMethod.GET, path = "/companyType")
    public ResponseEntity<CompanyType> find(@RequestParam Integer id){

        return new ResponseEntity<>(companyTypeRepository.findOne(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/companyTypes")
    public ResponseEntity<Iterable<CompanyType>> findAll(@RequestParam Integer companyMasterId){

        return new ResponseEntity<>(companyTypeRepository.findByCompanyId(companyMasterId), HttpStatus.OK);
    }

}
