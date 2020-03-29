package com.example.aviato.Classes.Cities;


public class HoustonClass {

    public String Itemquantity;
    private String Itemname;
    private String Itemprice;
    private int Itemimage;

    public HoustonClass(String iName, String iPrice, int iImage, String iquantity) {
        Itemname = iName;
        Itemprice = iPrice;
        Itemimage = iImage;
        Itemquantity = iquantity;
    }

    public String getItemName() {
        return Itemname;
    }

    public String getItemPrice() {
        return Itemprice;
    }

    public int getImageResourceId() {
        return Itemimage;
    }

    public String getItemquantity() {
        return Itemquantity;
    }
}