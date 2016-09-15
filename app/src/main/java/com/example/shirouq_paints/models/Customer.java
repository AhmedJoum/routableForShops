package com.example.shirouq_paints.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Customer implements Serializable {

    private String CustName;

    private String CustPhone;

    private String CustEmail;

    private String CustAddress;

    private String lon;

    private String lat;

    public void Customer() {

    }

    //setters

    public void setCustName(String custName) {
        CustName = custName;
    }

    public void setCustPhone(String custPhone) {
        CustPhone = custPhone;
    }

    public void setCustEmail(String custEmail) {
        CustEmail = custEmail;
    }

    public void setCustAddress(String custAddress) {
        CustAddress = custAddress;
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
        return CustEmail;
    }

    public String getCustPhone() {
        return CustPhone;
    }

    public String getCustName() {
        return CustName;
    }

    public String getCustAddress() {
        return CustAddress;
    }


}
