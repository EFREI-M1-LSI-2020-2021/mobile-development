package fr.efrei.badtracker.models;

import android.provider.BaseColumns;

import fr.efrei.badtracker.models.Player.PlayerEntry;
import fr.efrei.badtracker.models.Match.MatchEntry;

public class MatchPlayer {
    private long matchId;
    private long playerId;
    private boolean firstTeam;

    public MatchPlayer(long matchId, long playerId, boolean firstTeam) {
        this.matchId = matchId;
        this.playerId = playerId;
        this.firstTeam = firstTeam;
    }

    public static class MatchPlayerEntry implements BaseColumns {
        public static final String TABLE_NAME = "match_players";
        public static final String COLUMN_MATCH = "matchId";
        public static final String COLUMN_PLAYER = "playerId";
        public static final String COLUMN_FIRST_TEAM = "firstTeam";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_MATCH + " INTEGER NOT NULL REFERENCES " +
                        MatchEntry.TABLE_NAME + "(" + MatchEntry._ID + ") " +
                        "ON DELETE CASCADE," +
                        COLUMN_PLAYER + " INTEGER NOT NULL REFERENCES " +
                        PlayerEntry.TABLE_NAME + "(" + PlayerEntry._ID + ") " +
                        "ON DELETE CASCADE," +
                        COLUMN_FIRST_TEAM + " INTEGER NOT NULL," +
                        "PRIMARY KEY (" + COLUMN_MATCH + "," + COLUMN_PLAYER + ")" +
                        ");";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public long getMatchId() {
        return matchId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public boolean isFirstTeam() {
        return firstTeam;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public void setFirstTeam(boolean firstTeam) {
        this.firstTeam = firstTeam;
    }
}
