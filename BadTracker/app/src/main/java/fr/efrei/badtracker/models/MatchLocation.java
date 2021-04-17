package fr.efrei.badtracker.models;

public class MatchLocation {
    private double latitude;
    private double longitude;
    private String street;

    public MatchLocation(double latitude, double longitude, String street) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.street = street;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getStreet() {
        return street;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
