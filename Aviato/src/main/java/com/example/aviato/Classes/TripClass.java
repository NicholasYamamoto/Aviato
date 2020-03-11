package com.example.aviato.Classes;

public class TripClass {
    private int trip_id;
    private int customer_id;
    private int carrier_id;
    private String departDate;
    private String departTime;
    private String departLoc;
    private String departGate;
    private String destinationLoc;
    private String destinationTime;
    private String getDestinationGate;
    private String returnDate;
    private String returnTime;
    private String returnGate;
    private String ticketNumber;

    public TripClass(int trip_id, int customer_id, int carrier_id, String departDate, String departTime, String departLoc, String departGate, String destinationLoc, String destinationTime, String getDestinationGate, String returnDate, String returnTime, String returnGate, String ticketNumber) {
        this.trip_id = trip_id;
        this.customer_id = customer_id;
        this.carrier_id = carrier_id;
        this.departDate = departDate;
        this.departTime = departTime;
        this.departLoc = departLoc;
        this.departGate = departGate;
        this.destinationLoc = destinationLoc;
        this.destinationTime = destinationTime;
        this.getDestinationGate = getDestinationGate;
        this.returnDate = returnDate;
        this.returnTime = returnTime;
        this.returnGate = returnGate;
        this.ticketNumber = ticketNumber;
    }

    public TripClass(int customer_id, int carrier_id, String departDate, String departTime, String departLoc, String departGate, String destinationLoc, String destinationTime, String getDestinationGate, String returnDate, String returnTime, String returnGate, String ticketNumber) {
        this.customer_id = customer_id;
        this.carrier_id = carrier_id;
        this.departDate = departDate;
        this.departTime = departTime;
        this.departLoc = departLoc;
        this.departGate = departGate;
        this.destinationLoc = destinationLoc;
        this.destinationTime = destinationTime;
        this.getDestinationGate = getDestinationGate;
        this.returnDate = returnDate;
        this.returnTime = returnTime;
        this.returnGate = returnGate;
        this.ticketNumber = ticketNumber;
    }

    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getCarrier_id() {
        return carrier_id;
    }

    public void setCarrier_id(int carrier_id) {
        this.carrier_id = carrier_id;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public String getDepartTime() {
        return departTime;
    }

    public void setDepartTime(String departTime) {
        this.departTime = departTime;
    }

    public String getDepartLoc() {
        return departLoc;
    }

    public void setDepartLoc(String departLoc) {
        this.departLoc = departLoc;
    }

    public String getDepartGate() {
        return departGate;
    }

    public void setDepartGate(String departGate) {
        this.departGate = departGate;
    }

    public String getDestinationLoc() {
        return destinationLoc;
    }

    public void setDestinationLoc(String destinationLoc) {
        this.destinationLoc = destinationLoc;
    }

    public String getDestinationTime() {
        return destinationTime;
    }

    public void setDestinationTime(String destinationTime) {
        this.destinationTime = destinationTime;
    }

    public String getGetDestinationGate() {
        return getDestinationGate;
    }

    public void setGetDestinationGate(String getDestinationGate) {
        this.getDestinationGate = getDestinationGate;
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
}
