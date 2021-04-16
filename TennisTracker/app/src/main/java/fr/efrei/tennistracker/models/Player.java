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

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
