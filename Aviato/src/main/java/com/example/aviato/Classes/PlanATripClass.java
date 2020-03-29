package com.example.aviato.Classes;


//TODO: This needs to be setup so it handles the first phases of the Trip creation:
//      - Choosing a Destination City.
//      - Selecting tourist destinations for the trip.
//      Then, the rest of the functioanlity can be combined with the BookAFlightClass,
//          which is essentially all of this minus the Tourist Destination selection
public class PlanATripClass {
    private int city_ID;
    private String name;
    private String picFile;


    /*
     * @param city_ID the Primary Key identifier for the city selection in the Cities table
     * @param name a String containing the City name
     * @param picFile a Drawable of the selected City skyline
     */
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
