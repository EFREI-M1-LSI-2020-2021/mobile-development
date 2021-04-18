package fr.efrei.badtracker.models;

import android.provider.BaseColumns;

import java.util.List;

public class Match {
    private long id;
    private String name;
    private MatchLocation location;
    private List<Player> team1;
    private List<Player> team2;
    private List<Set> sets;

    public Match(long id, String name, MatchLocation location, List<Player> team1,
                 List<Player> team2, List<Set> sets) {
        this(name, location, team1, team2, sets);
        this.id = id;
    }

    public Match(String name, MatchLocation location, List<Player> team1, List<Player> team2,
                 List<Set> sets) {
        this.name = name;
        this.location = location;
        this.team1 = team1;
        this.team2 = team2;
        this.sets = sets;
    }

    public static class MatchEntry implements BaseColumns {
        public static final String TABLE_NAME = "matches";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LOCATION = "locationId";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME + " TEXT," +
                        COLUMN_LOCATION + " INTEGER);";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MatchLocation getLocation() {
        return location;
    }

    public List<Player> getTeam1() {
        return team1;
    }

    public List<Player> getTeam2() {
        return team2;
    }

    public List<Set> getSets() {
        return sets;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(MatchLocation location) {
        this.location = location;
    }

    public void setTeam1(List<Player> team1) {
        this.team1 = team1;
    }

    public void setTeam2(List<Player> team2) {
        this.team2 = team2;
    }

    public void setSets(List<Set> sets) {
        this.sets = sets;
    }
}
