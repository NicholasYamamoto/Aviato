package com.example.aviato.Classes;

public class CarrierClass {
    private int carrier_id;
    private String name;

    public CarrierClass(String name) {
        this.name = name;
    }

    public CarrierClass(int carrier_id, String name) {
        this.carrier_id = carrier_id;
        this.name = name;
    }

    public int getCarrier_id() {
        return carrier_id;
    }

    public void setCarrier_id(int carrier_id) {
        this.carrier_id = carrier_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
