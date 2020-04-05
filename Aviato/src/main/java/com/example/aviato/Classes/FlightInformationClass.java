/*************************************************************************************************
 * JANUARY 8, 2018
 * Mentesnot Aboset
 * ************************************************************************************************/
package com.mentesnot.easytravel.models;


import java.util.Date;

public class FlightInformationClass {

    private int flightID;
    private int flightNumber;
    private String origin;
    private String destination;
    private Date departureDate;
    private Date arrivalDate;
    private Date departingTime;
    private Date arrivalTime;
    private Double fare;
    private Double totalCost;
    private int travelTime;
    private String flightClass;


    public FlightInformationClass(int flightNumber, String origin, String destination, Date departureDate,
                                  Date arrivalDate, Date departingTime, Date arrivalTime, Double fare, Double totalCost, int travelTime,
                                  String flightClass) {

        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.departingTime = departingTime;
        this.arrivalTime = arrivalTime;
        this.fare = fare;
        this.totalCost = totalCost;
        this.travelTime = travelTime;
        this.flightClass = flightClass;
    }

    public FlightInformationClass(String origin, String destination, Date departureDate) {

        this.origin = origin;
        this.destination = destination;
        this.departureDate = departureDate;
    }

    public int getFlightID() {
        return flightID;
    }

    public void setFlightID(int flightID) {
        this.flightID = flightID;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDepartingDate() {
        return departureDate;
    }

    public void setDepartingDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Date getDepartingTime() {
        return departingTime;
    }

    public void setDepartingTime(Date departingTime) {
        this.departingTime = departingTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public int getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(int travelTime) {
        this.travelTime = travelTime;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }
}
