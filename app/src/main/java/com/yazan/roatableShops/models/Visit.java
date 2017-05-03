package com.yazan.roatableShops.models;

/**
 * Created by Carsiow on 9/7/16.
 */

import java.io.Serializable;

public class Visit implements Serializable {

    private Agent agent;

    private Shop shop;


    private SalePoint salePoint;

    private String visit_date;

    private String visit_id;

    public String getHolderQty() {
        return holderQty;
    }

    public void setHolderQty(String holderQty) {
        this.holderQty = holderQty;
    }

    public String getStandQty() {
        return standQty;
    }

    public void setStandQty(String standQty) {
        this.standQty = standQty;
    }

    private String holderQty;

    private String standQty;

    private String standTypes;


    public SalePoint getSalePoint() {
        return salePoint;
    }

    public void setSalePoint(SalePoint salePoint) {
        this.salePoint = salePoint;
    }

    public String getStandTypes() {
        return standTypes;
    }

    public void setStandTypes(String standTypes) {
        this.standTypes = standTypes;
    }


    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
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
        String sp_id = getShop() != null ?  getShop().getSpCode() : getSalePoint().getSpCode();
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
