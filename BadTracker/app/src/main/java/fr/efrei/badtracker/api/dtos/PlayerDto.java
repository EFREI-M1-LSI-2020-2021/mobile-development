package fr.efrei.badtracker.api.dtos;

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
}
