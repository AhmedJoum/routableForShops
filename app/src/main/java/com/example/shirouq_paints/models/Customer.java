package com.example.shirouq_paints.models;

import java.io.Serializable;


public class Customer implements Serializable {

    private String custName;

    private String custCode;

    private String custPhone;

    private String custEmail;

    private String custAddress;

    private String lon;

    private String lat;

    public void Customer() {

    }

    //setters

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getCustCode() {
        return custCode;
    }



    public void setCustPhone(String custPhone) {
        this.custPhone = custPhone;
    }

    public void setCustEmail(String custEmail) {
        this.custEmail = custEmail;
    }

    public void setCustAddress(String custAddress) {
        this.custAddress = custAddress;
    }


    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    //getters
    public String getLon() {
        return lon;
    }

    public String getLat() {
        return lat;
    }

    public String getCustEmail() {
        return custEmail;
    }

    public String getCustPhone() {
        return custPhone;
    }

    public String getCustName() {
        return custName;
    }

    public String getCustAddress() {
        return custAddress;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }



}
