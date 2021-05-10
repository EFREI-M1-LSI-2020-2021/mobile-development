package fr.efrei.badtracker.api.dtos;

import android.location.Location;

public class CreateMatchDto {
    private final String name;
    private Location location;
    private final long date;

    public CreateMatchDto(String name, Location Location, long date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }
}
