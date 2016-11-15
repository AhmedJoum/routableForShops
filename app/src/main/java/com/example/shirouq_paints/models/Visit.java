package com.example.shirouq_paints.models;

/**
 * Created by Carsiow on 9/7/16.
 */

import java.io.Serializable;
import  java.util.Date;
public class Visit implements Serializable {

    private Agent agent;

    private SalePoint salePoint;

    private String visit_date;

    private String visit_id;






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


    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id() {
        String agent_id = getAgent().getR_id();
        String sp_id = getSalePoint().getSpCode();
        String v_date = "" + getVisit_date();
        this.visit_id = agent_id + "_" + sp_id + "_" + v_date;
    }

    public String getVisit_date() {
        return visit_date;
    }

    public void setVisit_date(String visit_date) {
        this.visit_date = visit_date;
    }


}
