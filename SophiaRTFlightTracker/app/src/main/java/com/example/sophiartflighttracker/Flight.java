package com.example.sophiartflighttracker;

import java.io.Serializable;

public class Flight implements Serializable {
    private String flightNumber;
    private String airline;
    private String departureAirport;
    private String arrivalAirport;
    private String scheduledDepartureTime;
    private String scheduledArrivalTime;
    private String estimatedDepartureTime;
    private String estimatedArrivalTime;
    private String departureTerminal;
    private String arrivalTerminal;
    private String aircraftRegistration;
    private String aircraftIata;
    private String lastUpdated;
    private Double latitude;
    private Double longitude;

    // Constructeur
    public Flight(String flightNumber, String airline,
                  String departureAirport, String arrivalAirport,
                  String scheduledDepartureTime, String scheduledArrivalTime,
                  String estimatedDepartureTime, String estimatedArrivalTime,
                  String departureTerminal, String arrivalTerminal,
                  String aircraftRegistration, String aircraftIata,
                  String lastUpdated, Double latitude, Double longitude) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.scheduledDepartureTime = scheduledDepartureTime;
        this.scheduledArrivalTime = scheduledArrivalTime;
        this.estimatedDepartureTime = estimatedDepartureTime;
        this.estimatedArrivalTime = estimatedArrivalTime;
        this.departureTerminal = departureTerminal;
        this.arrivalTerminal = arrivalTerminal;
        this.aircraftRegistration = aircraftRegistration;
        this.aircraftIata = aircraftIata;
        this.lastUpdated = lastUpdated;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters
    public String getFlightNumber() { return flightNumber; }
    public String getAirline() { return airline; }
    public String getDepartureAirport() { return departureAirport; }
    public String getArrivalAirport() { return arrivalAirport; }
    public String getScheduledDepartureTime() { return scheduledDepartureTime; }
    public String getScheduledArrivalTime() { return scheduledArrivalTime; }
    public String getEstimatedDepartureTime() { return estimatedDepartureTime; }
    public String getEstimatedArrivalTime() { return estimatedArrivalTime; }
    public String getDepartureTerminal() { return departureTerminal; }
    public String getArrivalTerminal() { return arrivalTerminal; }
    public String getAircraftRegistration() { return aircraftRegistration; }
    public String getAircraftIata() { return aircraftIata; }
    public String getLastUpdated() { return lastUpdated; }
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
}
