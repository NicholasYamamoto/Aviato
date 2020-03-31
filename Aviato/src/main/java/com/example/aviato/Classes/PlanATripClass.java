package com.example.aviato.Classes;


//TODO: This needs to be setup so it handles the first phases of the Trip creation:
//      - Choosing a Destination City.
//      - Selecting tourist destinations for the trip.
//      Then, the rest of the functioanlity can be combined with the BookAFlightClass,
//          which is essentially all of this minus the Tourist Destination selection
public class PlanATripClass {
    private int cityID;
    private String name;
    private String picFile;


    /*
     * @param cityID the Primary Key identifier for the city selection in the Cities table
     * @param name a String containing the City name
     * @param picFile a Drawable of the selected City skyline
     */
    public PlanATripClass(int cityID, String name, String picFile) {
        this.cityID = cityID;
        this.name = name;
        this.picFile = picFile;
    }

    public PlanATripClass(String name, String picFile) {
        this.name = name;
        this.picFile = picFile;
    }

    public int getCityID() {
        return cityID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
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
