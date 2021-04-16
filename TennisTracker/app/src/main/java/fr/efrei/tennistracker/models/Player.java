package fr.efrei.tennistracker.models;

public class Player {
    private String name;
    private String firstname;
    private Sex sex;
    private String nationality;

    public Player(String name, String firstname, Sex sex, String nationality) {
        this.name = name;
        this.firstname = firstname;
        this.sex = sex;
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public String getFirstname() {
        return firstname;
    }

    public Sex getSex() {
        return sex;
    }

    public String getNationality() {
        return nationality;
    }
}
