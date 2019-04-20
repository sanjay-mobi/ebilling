package com.ebhumi.nayo.ebiiling.controller;

import com.ebhumi.nayo.ebiiling.dao.Address;
import com.ebhumi.nayo.ebiiling.dao.CompanyType;
import com.ebhumi.nayo.ebiiling.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @RequestMapping(method = RequestMethod.POST, path = "/address")
    public ResponseEntity<Address> save(@RequestBody Address address){

        address = addressRepository.save(address);

        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/address")
    public ResponseEntity<Address> find(@RequestParam Integer id){

        return new ResponseEntity<>(addressRepository.findOne(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/addresses")
    public ResponseEntity<Iterable<Address>> findAll(@RequestParam Integer companyMasterId){

        return new ResponseEntity<>(addressRepository.findByCompanyId(companyMasterId), HttpStatus.OK);
    }
}
