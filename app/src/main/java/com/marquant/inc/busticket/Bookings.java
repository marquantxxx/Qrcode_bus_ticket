package com.marquant.inc.busticket;

public class Bookings {

    private int id;
    private String travelFrom;
    private String travelTo;
    private String departureDate;
    private String arrivalDate;
    private String noOfTravelers;
    private String ticketNo;
    private String status;
    private String price;

    public Bookings(String travelFrom, String travelTo, String departureDate, String arrivalDate, String noOfTravelers,
                    String ticketNo, String status, String price) {
        this.travelFrom = travelFrom;
        this.travelTo = travelTo;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.noOfTravelers = noOfTravelers;
        this.ticketNo = ticketNo;
        this.status = status;
        this.price = price;
    }

    public Bookings(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTravelFrom() {
        return travelFrom;
    }

    public void setTravelFrom(String travelFrom) {
        this.travelFrom = travelFrom;
    }

    public String getTravelTo() {
        return travelTo;
    }

    public void setTravelTo(String travelTo) {
        this.travelTo = travelTo;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getNoOfTravelers() {
        return noOfTravelers;
    }

    public void setNoOfTravelers(String noOfTravelers) {
        this.noOfTravelers = noOfTravelers;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
