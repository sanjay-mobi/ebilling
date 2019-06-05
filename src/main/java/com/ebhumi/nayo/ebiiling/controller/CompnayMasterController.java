package com.ebhumi.nayo.ebiiling.controller;

import com.ebhumi.nayo.ebiiling.dao.Address;
import com.ebhumi.nayo.ebiiling.dao.CompanyMaster;
import com.ebhumi.nayo.ebiiling.dao.CompanyType;


import com.ebhumi.nayo.ebiiling.repository.AddressRepository;
import com.ebhumi.nayo.ebiiling.repository.CompanyMasterRepository;
import com.ebhumi.nayo.ebiiling.repository.CompanyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
class Home{
    @RequestMapping( path = "/")
    public String test( ){
        return "home";
    }
    @RequestMapping("/partials/{page}")
    String partialHandler(@PathVariable("page") final String page) {
        return page;
    }
}
@RestController
public class CompnayMasterController {

    @Autowired
    private CompanyMasterRepository companyMasterRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    CompanyTypeRepository companyTypeRepository;

    @RequestMapping(method = RequestMethod.POST, path = "/companyMaster")
    public ResponseEntity<CompanyMaster> save(@RequestBody CompanyMaster companyMaster){

        Model model;

        Set<Address> addresses = companyMaster.getAddresses();

        List<CompanyType> companyTypes = companyMaster.getCompanyType();
        companyMaster.setAddresses(null);
        companyMaster.setCompanyType(null);

        CompanyMaster companyMaster1 = companyMasterRepository.save(companyMaster);
        companyTypes.forEach(companyType ->{
            companyType.setCompanyId(companyMaster1.getId());
        });
        addresses.forEach(address ->{

            address.setCompanyId(companyMaster1.getId());
        });
        Iterable<CompanyType> companyTypes1  = companyTypeRepository.save(companyTypes);
        Iterable<Address> addresses1 = addressRepository.save(addresses);
        Set<Address> addressSet = new HashSet<>();
        addresses1.forEach(address2->{
            addressSet.add(address2);
        });
        List<CompanyType> companyTypeList = new ArrayList<>();
        companyTypes1.forEach( companyType ->companyTypeList.add(companyType) );
        companyMaster1.setAddresses(addressSet);
        companyMaster1.setCompanyType(companyTypeList);

        return new ResponseEntity<>(companyMaster1, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/companyMaster1")
    public ResponseEntity<CompanyMaster> find(@RequestParam Integer companyMasterId){
        CompanyMaster companyMaster = companyMasterRepository.findOne(companyMasterId);

        return new ResponseEntity<>(companyMaster,HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/companyMaster")
    public ResponseEntity<Iterable<CompanyMaster>> findAll(){
        Iterable<CompanyMaster> companyMasterList = companyMasterRepository.findByCompanyType();

        return new ResponseEntity<>(companyMasterList,HttpStatus.OK);
    }


}
