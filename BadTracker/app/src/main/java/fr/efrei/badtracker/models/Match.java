package fr.efrei.badtracker.models;

import android.provider.BaseColumns;

import java.util.List;

public class Match {
    private long id;
    private String name;
    private MatchLocation location;
    private List<Player> winners;
    private List<Player> losers;
    private List<Set> sets;

    public Match(long id, String name, MatchLocation location, List<Player> winners,
                 List<Player> losers, List<Set> sets) {
        this(name, location, winners, losers, sets);
        this.id = id;
    }

    public Match(String name, MatchLocation location, List<Player> winners, List<Player> losers,
                 List<Set> sets) {
        this.name = name;
        this.location = location;
        this.winners = winners;
        this.losers = losers;
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

    public List<Player> getWinners() {
        return winners;
    }

    public List<Player> getLosers() {
        return losers;
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

    public void setWinners(List<Player> winners) {
        this.winners = winners;
    }

    public void setLosers(List<Player> losers) {
        this.losers = losers;
    }

    public void setSets(List<Set> sets) {
        this.sets = sets;
    }
}
