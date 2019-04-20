package com.ebhumi.nayo.ebiiling.dao;

import java.io.Serializable;

public class ArrayOfString  implements Serializable {
    private String [] sizes;

    public String[] getSizes() {
        return sizes;
    }

    public void setSizes(String[] sizes) {
        this.sizes = sizes;
    }
}
