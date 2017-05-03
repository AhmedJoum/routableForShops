package com.yazan.roatableShops.models;

import java.io.Serializable;


public class Shop implements Serializable {


    private String spID;

    private String spName;

    private String spCode;

    private int spType;

    private String lng;

    private String lat;

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    private String cityCode;


    public String getLastCheckDate() {
        return lastCheckDate;
    }

    private String lastCheckDate;


    private String route_desc;


    public Shop() {

    }

    //setters

    public int getSpType() {
        return spType;
    }

    public void setSpType(int spType) {
        this.spType = spType;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getSpCode() {
        return spCode;
    }


    public void setSpID(String spID) {
        this.spID = spID;
    }


    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    //getters
    public String getLng() {
        return lng;
    }

    public String getLat() {
        return lat;
    }


    public String getSpName() {
        return spName;
    }

    public void setSpCode(String spCode) {
        this.spCode = spCode;
    }


    public String getSpID() {
        return spID;
    }

    public String getRoute_desc() {
        return route_desc;
    }

    public void setRoute_desc(String route_desc) {
        this.route_desc = route_desc;
    }


    public void setLastCheckDate(String lastCheckDate) {
        this.lastCheckDate = lastCheckDate;
    }
}
