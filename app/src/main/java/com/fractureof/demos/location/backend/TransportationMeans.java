package com.fractureof.demos.location.backend;

/**
 * Created by hanan on 15/03/2016.
 */
public enum TransportationMeans {
    WALK("walk"),TAXI("taxi"),CAR("car");
    private String name;
    private TransportationMeans(String name) {
        this.name = name;
    }
}
