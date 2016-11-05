package com.example.shirouq_paints.models;

/**
 * Created by Carsiow on 9/7/16.
 */

import java.io.Serializable;
import  java.util.Date;
public class TrnHeader implements Serializable {
    private int trn_id;

    private Agent agent;

    private SalePoint salePoint;

    public Date getTrn_date() {
        return trn_date;
    }

    public void setTrn_date(Date trn_date) {
        this.trn_date = trn_date;
    }

    public SalePoint getSalePoint() {
        return salePoint;
    }

    public void setSalePoint(SalePoint salePoint) {
        this.salePoint = salePoint;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public int getTrn_id() {
        return trn_id;
    }

    public void setTrn_id(int trn_id) {
        this.trn_id = trn_id;
    }

    private Date trn_date;

}
