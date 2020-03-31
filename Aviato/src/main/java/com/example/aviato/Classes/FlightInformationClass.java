package com.example.aviato.Classes;

public class FlightInformationClass {
    private int tripID;
    private int customerID;
    private String carrier_name;

    private String departingDate;
    private String departingTime;
    private String departingCity;
    private String departingGate;

    private String destinationDate;
    private String destinationTime;
    private String destinationCity;
    private String destinationGate;

    private String returnDate;
    private String returnTime;
    private String returnGate;

    private String ticketNumber;
    //TODO: Remove this from FlightInformation and move to Order Details or Checkout when it gets calculated
    private int orderTotal;

    // Constructor WITH Trip ID when Plan a Trip is used
    public FlightInformationClass(int tripID, int customerID, String carrierName,
                                  String departingDate, String departingCity,
                                  String departingTime, String departingGate,
                                  String destinationDate, String destinationCity,
                                  String destinationTime, String destinationGate,
                                  String returnDate, String returnTime, String returnGate,
                                  String ticketNumber, int orderTotal)
    {
        this.tripID = tripID;
        this.customerID = customerID;
        this.carrier_name = carrierName;

        this.departingDate = departingDate;
        this.departingTime = departingTime;
        this.departingCity = departingCity;
        this.departingGate = departingGate;

        this.destinationCity = destinationCity;
        this.destinationTime = destinationTime;
        this.destinationGate = destinationGate;
        this.destinationDate = destinationDate;

        this.returnDate = returnDate;
        this.returnTime = returnTime;
        this.returnGate = returnGate;

        this.ticketNumber = ticketNumber;
        this.orderTotal = orderTotal;
    }

    // Constructor WITHOUT Trip ID when Plan a Trip is used
    public FlightInformationClass(int customerID, String carrier_name, String departDate,
                                  String departingTime, String departingCity, String departingGate,
                                  String destinationCity, String destinationTime,
                                  String destinationGate, String destinationDate,
                                  String returnDate, String returnTime, String returnGate,
                                  String ticketNumber, int orderTotal)
    {
        this.customerID = customerID;
        this.carrier_name = carrier_name;

        this.departingDate = departDate;
        this.departingTime = departingTime;
        this.departingCity = departingCity;
        this.departingGate = departingGate;

        this.destinationDate = destinationDate;
        this.destinationTime = destinationTime;
        this.destinationCity = destinationCity;
        this.destinationGate = destinationGate;

        this.returnDate = returnDate;
        this.returnTime = returnTime;
        this.returnGate = returnGate;

        this.ticketNumber = ticketNumber;
        this.orderTotal = orderTotal;
    }

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCarrierID() {
        return carrier_name;
    }

    public void setCarrierID(String carrier_name) {
        this.carrier_name = carrier_name;
    }

    public String getDepartDate() {
        return departingDate;
    }

    public void setDepartDate(String departDate) {
        this.departingDate = departDate;
    }

    public String getDepartingTime() {
        return departingTime;
    }

    public void setDepartingTime(String departingTime) {
        this.departingTime = departingTime;
    }

    public String getDepartingCity() {
        return departingCity;
    }

    public void setDepartingCity(String departingCity) {
        this.departingCity = departingCity;
    }

    public String getDepartingGate() {
        return departingGate;
    }

    public void setDepartingGate(String departingGate) {
        this.departingGate = departingGate;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getDestinationDate() {
        return destinationDate;
    }

    public void setDestinationDate(String destinationDate) {
        this.destinationDate = destinationDate;
    }

    public String getDestinationTime() {
        return destinationTime;
    }

    public void setDestinationTime(String destinationTime) {
        this.destinationTime = destinationTime;
    }

    public String getDestinationGate() {
        return destinationGate;
    }

    public void setDestinationGate(String destinationGate) {
        this.destinationGate = destinationGate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getReturnGate() {
        return returnGate;
    }

    public void setReturnGate(String returnGate) {
        this.returnGate = returnGate;
    }

    public String getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(String ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public int getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(int orderTotal) { this.orderTotal = orderTotal; }
}
