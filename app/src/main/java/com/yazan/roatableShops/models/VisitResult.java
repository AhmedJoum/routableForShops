package com.yazan.roatableShops.models;

import java.io.Serializable;

/**
 * Created by Carsiow on 9/7/16.
 */
public class VisitResult implements Serializable {


    private String visit_id;

    private Agent agent;

    private String ad_Code;

    private String ad_QTY;

    private String when;


    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visit_id) {
        this.visit_id = visit_id;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getAd_Code() {
        return ad_Code;
    }

    public void setAd_Code(String ad_Code) {
        this.ad_Code = ad_Code;
    }

    public String getAd_QTY() {
        return ad_QTY;
    }

    public void setAd_QTY(String ad_QTY) {
        this.ad_QTY = ad_QTY;
    }


    public String getWhen() {
        return when;
    }

    public void setWhen(String when) {
        this.when = when;
    }

}
