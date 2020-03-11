package com.example.aviato.Classes;

public class PlanATripClass {
    private int city_ID;
    private String name;
    private String picFile;

    public PlanATripClass(int city_ID, String name, String picFile) {
        this.city_ID = city_ID;
        this.name = name;
        this.picFile = picFile;
    }

    public PlanATripClass(String name, String picFile) {
        this.name = name;
        this.picFile = picFile;
    }

    public int getCity_ID() {
        return city_ID;
    }

    public void setCity_ID(int city_ID) {
        this.city_ID = city_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicFile() {
        return picFile;
    }

    public void setPicFile(String picFile) {
        this.picFile = picFile;
    }
}
