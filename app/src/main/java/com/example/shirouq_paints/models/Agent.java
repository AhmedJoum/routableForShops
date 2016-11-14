package com.example.shirouq_paints.models;

import java.io.Serializable;

/**
 * Created by Carsiow on 9/7/16.
 */
public class Agent implements Serializable {


    private int a_id;

    private String a_name;

    private String a_email;

    private String a_password;

    private String r_id;// this should call the roat object to be setted


    // setters and getters

    public String getA_password() {
        return a_password;
    }

    public void setA_password(String a_password) {
        this.a_password = a_password;
    }

    public String getA_name() {
        return a_name;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }

    public String getA_email() {
        return a_email;
    }

    public void setA_email(String a_email) {
        this.a_email = a_email;
    }

    public String getR_id() {
        return r_id;
    }

    public void setR_id(String r_id) {
        this.r_id = r_id;
    }

    public int getA_id() {
        return a_id;
    }

    public void setA_id(int a_id) {
        this.a_id = a_id;
    }


}
