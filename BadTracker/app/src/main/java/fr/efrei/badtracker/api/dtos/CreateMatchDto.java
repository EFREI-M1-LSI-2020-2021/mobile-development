package fr.efrei.badtracker.api.dtos;

import android.location.Location;

public class CreateMatchDto {
    private final String name;
    private Location location;

    public CreateMatchDto(String name, Location Location) {
        this.name = name;
        this.location = location;
    }
}
