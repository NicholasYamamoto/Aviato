package com.example.aviato.Classes;

public class PastOrdersClass {
    public String Itemprice;
    private String Itemid;
    private String Itemname;
    private String Itemquantity;

    public PastOrdersClass(String iId, String iName, String iQuantity, String iPrice) {
        Itemid = iId;
        Itemname = iName;
        Itemquantity = iQuantity;
        Itemprice = iPrice;
    }

    public String getItemId() {
        return Itemid;
    }

    public String getItemName() {
        return Itemname;
    }

    public String getItemPrice() {
        return Itemprice;
    }

    public String getItemquantity() {
        return Itemquantity;
    }

}
