package com.yazan.roatableShops.models;

import java.io.Serializable;

/**
 * Created by Carsiow on 11/8/16.
 */

public class Route implements Serializable {

    private int route_id;

    private String route_name;

    private String route_description;

    private int r_id;

    public int getRoute_id() {
        return route_id;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }

    public String getRoute_name() {
        return route_name;
    }

    public void setRoute_name(String route_name) {
        this.route_name = route_name;
    }

    public String getRoute_description() {
        return route_description;
    }

    public void setRoute_description(String route_description) {
        this.route_description = route_description;
    }

    public int getR_id() {
        return r_id;
    }

    public void setR_id(int r_id) {
        this.r_id = r_id;
    }

}
