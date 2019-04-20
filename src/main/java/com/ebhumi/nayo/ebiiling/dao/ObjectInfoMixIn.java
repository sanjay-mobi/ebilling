package com.ebhumi.nayo.ebiiling.dao;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = ObjectInfoMixIn.PROPERTY_NAME)
public class ObjectInfoMixIn {

    public static final String PROPERTY_NAME = "@type";
}
