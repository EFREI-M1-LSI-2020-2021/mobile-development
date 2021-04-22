package fr.efrei.badtracker.api.dtos;

public class LocationDto {
    private final double latitude;
    private final double longitude;

    public LocationDto(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
