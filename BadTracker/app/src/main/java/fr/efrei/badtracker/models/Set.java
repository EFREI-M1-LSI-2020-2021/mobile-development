package fr.efrei.badtracker.models;

import android.provider.BaseColumns;

public class Set {
    private long id;
    private int scoreWinner;
    private int scoreLoser;
    private Player winner;
    private Player loser;

    public Set(long id, int scoreWinner, int scoreLoser, Player winner, Player loser) {
        this(scoreWinner, scoreLoser, winner, loser);
        this.id = id;
    }

    public Set(int scoreWinner, int scoreLoser, Player winner, Player loser) {
        this.scoreWinner = scoreWinner;
        this.scoreLoser = scoreLoser;
        this.winner = winner;
        this.loser = loser;
    }

    public static class SetEntry implements BaseColumns {
        public static final String TABLE_NAME = "sets";
        public static final String COLUMN_SCORE_WINNER = "scoreWinner";
        public static final String COLUMN_SCORE_LOSER = "scoreLoser";
        public static final String COLUMN_WINNER = "winner";
        public static final String COLUMN_LOSER = "street";
        public static final String COLUMN_MATCH = "match";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_SCORE_WINNER + " INTEGER," +
                        COLUMN_SCORE_LOSER + " INTEGER," +
                        COLUMN_WINNER + " INTEGER," +
                        COLUMN_LOSER + " INTEGER)" +
                        COLUMN_MATCH + " INTEGER)";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public long getId() {
        return id;
    }

    public int getScoreWinner() {
        return scoreWinner;
    }

    public int getScoreLoser() {
        return scoreLoser;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getLoser() {
        return loser;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setScoreWinner(int scoreWinner) {
        this.scoreWinner = scoreWinner;
    }

    public void setScoreLoser(int scoreLoser) {
        this.scoreLoser = scoreLoser;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public void setLoser(Player loser) {
        this.loser = loser;
    }
}
