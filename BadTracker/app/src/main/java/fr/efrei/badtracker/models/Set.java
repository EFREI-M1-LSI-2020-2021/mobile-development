package fr.efrei.badtracker.models;

import android.provider.BaseColumns;

import java.util.List;
import fr.efrei.badtracker.models.Match.MatchEntry;

public class Set {
    private long id;
    private int scoreWinner;
    private int scoreLoser;
    private List<Player> winners;
    private List<Player> losers;

    public Set(long id, int scoreWinner, int scoreLoser, List<Player> winners, List<Player> losers) {
        this(scoreWinner, scoreLoser, winners, losers);
        this.id = id;
    }

    public Set(int scoreWinner, int scoreLoser, List<Player> winners, List<Player> losers) {
        this.scoreWinner = scoreWinner;
        this.scoreLoser = scoreLoser;
        this.winners = winners;
        this.losers = losers;
    }

    public static class SetEntry implements BaseColumns {
        public static final String TABLE_NAME = "sets";
        public static final String COLUMN_SCORE_WINNER = "scoreWinner";
        public static final String COLUMN_SCORE_LOSER = "scoreLoser";
        public static final String COLUMN_MATCH = "matchId";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_SCORE_WINNER + " INTEGER NOT NULL," +
                        COLUMN_SCORE_LOSER + " INTEGER NOT NULL," +
                        COLUMN_MATCH + " INTEGER NOT NULL REFERENCES " +
                        MatchEntry.TABLE_NAME + "(" + MatchEntry._ID + ") " +
                        "ON DELETE CASCADE" +
                        ")";

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

    public List<Player> getWinners() {
        return winners;
    }

    public List<Player> getLosers() {
        return losers;
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

    public void setWinners(List<Player> winners) {
        this.winners = winners;
    }

    public void setLosers(List<Player> losers) {
        this.losers = losers;
    }
}
