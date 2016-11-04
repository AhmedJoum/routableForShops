package com.example.shirouq_paints.models;

import java.io.Serializable;

/**
 * Created by Carsiow on 9/7/16.
 */
public class TrnDetails  implements Serializable {

    private TrnHeader trnHeader;

    private Agent agent;

    private String itemDes;

    private int qty;

    private int dlv;

    public TrnHeader getTrnHeader() {
        return trnHeader;
    }

    public void setTrnHeader(TrnHeader trnHeader) {
        this.trnHeader = trnHeader;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getItemDes() {
        return itemDes;
    }

    public void setItemDes(String itemDes) {
        this.itemDes = itemDes;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getDlv() {
        return dlv;
    }

    public void setDlv(int dlv) {
        this.dlv = dlv;
    }

}
