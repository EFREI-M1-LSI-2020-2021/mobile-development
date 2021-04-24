package fr.efrei.badtracker.api.dtos;

import fr.efrei.badtracker.models.MatchLocation;

public class LocationDto {
    private final double latitude;
    private final double longitude;

    public LocationDto(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public MatchLocation toMatchLocation() {
        return new MatchLocation(latitude, longitude);
    }
}
