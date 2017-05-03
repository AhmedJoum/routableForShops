package com.yazan.roatableShops.models;

import android.content.Context;
import android.database.Cursor;

import com.yazan.roatableShops.dao.DAO;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by Carsiow on 4/19/17.
 */

public class ShopProductCode implements Serializable {
    private String productName;
    private String adCode;
    private String adType;
    private String pointType;

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

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public List<String> getAdCodes(String ad_type, String point_type, Context context) {
        List<String> ad_codes = new ArrayList<String>();
        DAO dao = new DAO(context);
        Cursor cr = dao.getAdCodes(ad_type, point_type);
        if (cr.moveToFirst()) {
            do {
                ad_codes.add(cr.getString(0));
            } while (cr.moveToNext());
        }

        return ad_codes;
    }
}
