package fr.efrei.badtracker.models;

import android.provider.BaseColumns;

import java.util.List;

public class Match {
    private long id;
    private MatchLocation location;
    private Player winner;
    private Player loser;
    private List<Set> sets;

    public Match(long id, MatchLocation location, Player winner, Player loser, List<Set> sets) {
        this(location, winner, loser, sets);
        this.id = id;
    }

    public Match(MatchLocation location, Player winner, Player loser, List<Set> sets) {
        this.location = location;
        this.winner = winner;
        this.loser = loser;
        this.sets = sets;
    }

    public static class MatchEntry implements BaseColumns {
        public static final String TABLE_NAME = "matches";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_WINNER = "winner";
        public static final String COLUMN_LOSER = "loser";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_LOCATION + " INTEGER," +
                        COLUMN_WINNER + " INTEGER," +
                        COLUMN_LOSER + " INTEGER)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public long getId() {
        return id;
    }

    public MatchLocation getLocation() {
        return location;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getLoser() {
        return loser;
    }

    public List<Set> getSets() {
        return sets;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLocation(MatchLocation location) {
        this.location = location;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void setLoser(Player loser) {
        this.loser = loser;
    }

    public void setSets(List<Set> sets) {
        this.sets = sets;
    }
}
