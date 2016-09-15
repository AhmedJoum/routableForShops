package com.example.shirouq_paints.models;

/**
 * Created by Carsiow on 9/7/16.
 */

import java.io.Serializable;
import  java.util.Date;
public class TrnHeader implements Serializable {
    private int trn_id;

    private Salesman salesman;

    private Customer customer;

    public Date getTrn_date() {
        return trn_date;
    }

    public void setTrn_date(Date trn_date) {
        this.trn_date = trn_date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Salesman getSalesman() {
        return salesman;
    }

    public void setSalesman(Salesman salesman) {
        this.salesman = salesman;
    }

    public int getTrn_id() {
        return trn_id;
    }

    public void setTrn_id(int trn_id) {
        this.trn_id = trn_id;
    }

    private Date trn_date;

}
