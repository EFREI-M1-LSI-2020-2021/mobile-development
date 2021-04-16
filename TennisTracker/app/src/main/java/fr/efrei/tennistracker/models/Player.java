package fr.efrei.tennistracker.models;

import android.provider.BaseColumns;

public class Player {
    private int id;
    private String name;
    private String firstName;
    private Sex sex;
    private String nationality;

    public Player(int id, String name, String firstName, Sex sex, String nationality) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.sex = sex;
        this.nationality = nationality;
    }

    public static class PlayerEntry implements BaseColumns {
        public static final String TABLE_NAME = "players";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_FIRSTNAME = "firstName";
        public static final String COLUMN_NAME_SEX = "sex";
        public static final String COLUMN_NAME_NATIONALITY = "nationality";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_NAME + " TEXT," +
                        COLUMN_NAME_FIRSTNAME + " TEXT," +
                        COLUMN_NAME_SEX + " TEXT," +
                        COLUMN_NAME_NATIONALITY + " TEXT)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstName;
    }

    public Sex getSex() {
        return sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
