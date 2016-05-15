package com.bluewave.reservation.model;

/**
 * Created by Developer on 2016-05-12.
 */
public class User {
    private String id;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
