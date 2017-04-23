package com.yazan.roatableShops.models;

import java.io.Serializable;

/**
 * Created by Carsiow on 4/19/17.
 */

public class ShopProductCode implements Serializable {
    private String productName;
    private String adCode;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getAdCode() {
        return adCode;
    }

    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    public String getAdType() {
        return adType;
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    private String adType;
}
