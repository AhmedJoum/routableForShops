package com.example.shirouq_paints.models;

import java.io.Serializable;

/**
 * Created by Carsiow on 9/7/16.
 */
public class VisitResult implements Serializable {



    private String visit_id;

    private Agent agent;

    private int operater_id;

    private int ad_type;

    private int ad_state;

    private  int  bad_state_reason;



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


    public int getOperater_id() {
        return operater_id;
    }

    public void setOperater_id(int operater_id) {
        this.operater_id = operater_id;
    }

    public int getAd_type() {
        return ad_type;
    }

    public void setAd_type(int ad_type) {
        this.ad_type = ad_type;
    }

    public int getAd_state() {
        return ad_state;
    }

    public void setAd_state(int ad_state) {
        this.ad_state = ad_state;
    }

    public int getBad_state_reason() {
        return bad_state_reason;
    }

    public void setBad_state_reason(int bad_state_reason) {
        this.bad_state_reason = bad_state_reason;
    }

}
