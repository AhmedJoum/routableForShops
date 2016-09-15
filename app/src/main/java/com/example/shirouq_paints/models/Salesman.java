package com.example.shirouq_paints.models;

import java.io.Serializable;

/**
 * Created by Carsiow on 9/7/16.
 */
public class Salesman implements Serializable {


    private int sm_id;

    private String sm_name;

    private String sm_email;

    private String sm_password;

    private String sm_phone;


    // setters and getters

    public String getSm_password() {
        return sm_password;
    }

    public void setSm_password(String sm_password) {
        this.sm_password = sm_password;
    }

    public String getSm_name() {
        return sm_name;
    }

    public void setSm_name(String sm_name) {
        this.sm_name = sm_name;
    }

    public String getSm_email() {
        return sm_email;
    }

    public void setSm_email(String sm_email) {
        this.sm_email = sm_email;
    }

    public String getSm_phone() {
        return sm_phone;
    }

    public void setSm_phone(String sm_phone) {
        this.sm_phone = sm_phone;
    }

    public int getSm_id() {
        return sm_id;
    }

    public void setSm_id(int sm_id) {
        this.sm_id = sm_id;
    }


}
