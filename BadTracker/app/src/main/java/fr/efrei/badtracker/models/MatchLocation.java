package fr.efrei.badtracker.models;

import android.provider.BaseColumns;

public class MatchLocation {
    private long id;
    private double latitude;
    private double longitude;
    private String street;

    public MatchLocation(long id, double latitude, double longitude, String street) {
        this(latitude, longitude, street);
        this.id = id;
    }

    public MatchLocation(double latitude, double longitude, String street) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.street = street;
    }

    public static class MatchLocationEntry implements BaseColumns {
        public static final String TABLE_NAME = "match_locations";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_STREET = "street";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_LATITUDE + " REAL," +
                        COLUMN_LONGITUDE + " REAL," +
                        COLUMN_STREET + " TEXT)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public long getId() {
        return id;
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

    public void setId(long id) {
        this.id = id;
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
