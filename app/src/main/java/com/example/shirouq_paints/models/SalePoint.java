package com.example.shirouq_paints.models;

import java.io.Serializable;


public class SalePoint implements Serializable {


    private String spID;

    private String spName;

    private String spCode;



    private  int spType;

    private String spPhone;

    private String spOwnerName;

    private String spAddress;

    private String rout_id;

    private String lng;

    private String lat;


    private  int block_type;

    private int street_type;

    public SalePoint() {

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

    public void setRout_id(String rout_id) {
        this.rout_id = rout_id;
    }


    public void setSpPhone(String spPhone) {
        this.spPhone = spPhone;
    }

    public void setSpOwnerName(String spOwnerName) {
        this.spOwnerName = spOwnerName;
    }

    public void setSpAddress(String spAddress) {
        this.spAddress = spAddress;
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

    public String getSpOwnerName() {
        return spOwnerName;
    }

    public String getSpPhone() {
        return spPhone;
    }

    public String getSpName() {
        return spName;
    }

    public String getSpAddress() {
        return spAddress;
    }

    public void setSpCode(String spCode) {
        this.spCode = spCode;
    }


    public String getSpID() {
        return spID;
    }


    public String getRout_id() {
        return rout_id;
    }

    public int getBlock_type() {
        return block_type;
    }

    public void setBlock_type(int block_type) {
        this.block_type = block_type;
    }

    public int getStreet_type() {
        return street_type;
    }

    public void setStreet_type(int street_type) {
        this.street_type = street_type;
    }



}
