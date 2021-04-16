package fr.efrei.tennistracker.models;

public class MatchLocation {
    private double latitude;
    private double longitude;
    private String street;

    public MatchLocation(double latitude, double longitude, String street) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.street = street;
    }
}
