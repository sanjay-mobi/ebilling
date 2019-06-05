package com.ebhumi.nayo.ebiiling.controller;

import com.ebhumi.nayo.ebiiling.dao.ProductMaster;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class MyDeserializer extends StdDeserializer<ProductMaster> {

    public MyDeserializer() {
        this(null);
    }

    protected MyDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ProductMaster deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        double costPrice = node.get("costPrice").doubleValue();
        double mrp;
        if(node.get("MRP")!=null){
            mrp = node.get("MRP").doubleValue();
        }else{
            mrp = node.get("mrp").doubleValue();
        }



        ProductMaster test = new ProductMaster();
        test.setMRP(mrp);
        test.setCostPrice(costPrice);
        if(node.get("companyId") !=null) {
            test.setCompanyId(node.get("companyId").asInt());
        }
        else {
            test.setCompanyId(0);
        }
        if(node.get("id") !=null) {
            test.setId(node.get("id").asInt());
        }
        else {
            test.setId(0);
        }

        test.setBrand(node.get("brand").textValue());
        test.setColor(node.get("color").textValue());
        test.setHsnCode(node.get("hsnCode").textValue());
        String str = node.get("sizes").textValue();
        if (!str.isEmpty()){
            str = str.substring(1);
        }
        test.setSizes(str);
        test.setStyleCode(node.get("styleCode").textValue());

        return test;
    }
}
