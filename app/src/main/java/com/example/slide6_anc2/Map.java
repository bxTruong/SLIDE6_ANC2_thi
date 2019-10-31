package com.example.slide6_anc2;

public class Map {
    private int id;
    private String name;
    private double longitute, latitute;

    public Map() {
    }

    public Map(String name, double longitute, double latitute) {
        this.name = name;
        this.longitute = longitute;
        this.latitute = latitute;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitute() {
        return longitute;
    }

    public void setLongitute(double longitute) {
        this.longitute = longitute;
    }

    public double getLatitute() {
        return latitute;
    }

    public void setLatitute(double latitute) {
        this.latitute = latitute;
    }
}
