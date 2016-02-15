package com.example.carsiow.rmsapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CustOrder implements Serializable {
    private int smId;

    private String CustName;
    private String CustPhone;
    private String CustEmail;
    private String CustAddress;

    private List<Integer> Services = new ArrayList<>();
    private HashMap<Integer,String> ServicesDescription = new HashMap<Integer, String>();

    private String lon;
    private String lat;

    public void CustOrder() {

    }

    //setters
    public void setSmId(int smId){ this.smId = smId; }

    public void setCustName(String custName) { CustName = custName; }

    public void setCustPhone(String custPhone) { CustPhone = custPhone; }

    public void setCustEmail(String custEmail) { CustEmail = custEmail; }

    public void setCustAddress(String custAddress) { CustAddress = custAddress; }

    public void setServices(List<Integer> services) { Services = services; }

    public void setServicesDescription(HashMap<Integer,String> servicesDescription) { ServicesDescription = servicesDescription; }

    public void setLat(String lat) { this.lat = lat; }

    public void setLon(String lon) { this.lon = lon; }

    //getters
    public String getLon() { return lon; }

    public String getLat() { return lat; }

    public String getCustEmail() { return CustEmail; }

    public String getCustPhone() { return CustPhone; }

    public String getCustName() { return CustName; }

    public String getCustAddress() { return CustAddress; }

    public List<Integer> getServices() { return Services; }

    public HashMap<Integer,String> getServicesDescription() { return ServicesDescription; }

    public int getSmId() { return smId; }
}
