package fr.efrei.badtracker.models;

import android.provider.BaseColumns;

import fr.efrei.badtracker.models.Player.PlayerEntry;
import fr.efrei.badtracker.models.Set.SetEntry;

public class SetPlayer {
    private long setId;
    private long playerId;
    private boolean winner;

    public SetPlayer(long setId, long playerId, boolean winner) {
        this.setId = setId;
        this.playerId = playerId;
        this.winner = winner;
    }

    public static class SetPlayerEntry implements BaseColumns {
        public static final String TABLE_NAME = "set_players";
        public static final String COLUMN_SET = "set";
        public static final String COLUMN_PLAYER = "player";
        public static final String COLUMN_WINNER = "winner";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_SET + " INTEGER NOT NULL REFERENCES " +
                        SetEntry.TABLE_NAME + "(" + SetEntry._ID + ") " +
                        "ON DELETE CASCADE," +
                        COLUMN_PLAYER + " INTEGER NOT NULL REFERENCES " +
                        PlayerEntry.TABLE_NAME + "(" + PlayerEntry._ID + ") " +
                        "ON DELETE CASCADE," +
                        COLUMN_WINNER + " INTEGER NOT NULL," +
                        "PRIMARY KEY (" + COLUMN_SET + "," + COLUMN_PLAYER + ")" +
                        ");";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public long getSetId() {
        return setId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setSetId(long setId) {
        this.setId = setId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }
}
