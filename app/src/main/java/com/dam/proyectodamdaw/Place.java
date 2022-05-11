package com.dam.proyectodamdaw;

import java.io.Serializable;

public class Place implements Serializable {
    private String name;
    private float lat;
    private float lon;
    private String img;

    public Place(String name, float lon, float lat, String img) {
        this.name = name;
        this.lon = lon;
        this.lat = lat;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public float getLon() {
        return lon;
    }

    public float getLat() {
        return lat;
    }

    public String getImg() {
        return img;
    }

    @Override
    public String toString() {
        return name;
    }
}
