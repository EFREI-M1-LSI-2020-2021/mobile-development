package fr.efrei.badtracker.api.dtos;

import fr.efrei.badtracker.models.Player;
import fr.efrei.badtracker.models.Sex;

public class PlayerDto {
    private final String name;
    private final String firstName;
    private final String sex;
    private final String nationality;

    public PlayerDto(String name, String firstName, String sex, String nationality) {
        this.name = name;
        this.firstName = firstName;
        this.sex = sex;
        this.nationality = nationality;
    }

    public Player toPlayer() {
        return new Player(name, firstName, Sex.valueOf(sex), nationality);
    }
}
