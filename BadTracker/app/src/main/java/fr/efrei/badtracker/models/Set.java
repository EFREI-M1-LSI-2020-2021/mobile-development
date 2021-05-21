package fr.efrei.badtracker.models;

import android.provider.BaseColumns;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;
import fr.efrei.badtracker.models.Match.MatchEntry;

public class Set implements Serializable {

    private transient long id;
    private int scoreTeam1;
    private int scoreTeam2;

    public Set(long id, int scoreTeam1, int scoreTeam2) {
        this(scoreTeam1, scoreTeam2);
        this.id = id;
    }

    public Set(int scoreTeam1, int scoreTeam2) {
        this.scoreTeam1 = scoreTeam1;
        this.scoreTeam2 = scoreTeam2;
    }

    public static class SetEntry implements BaseColumns {
        public static final String TABLE_NAME = "sets";
        public static final String COLUMN_SCORE_TEAM1 = "scoreTeam1";
        public static final String COLUMN_SCORE_TEAM2 = "scoreTeam2";
        public static final String COLUMN_MATCH = "matchId";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_SCORE_TEAM1 + " INTEGER NOT NULL," +
                        COLUMN_SCORE_TEAM2 + " INTEGER NOT NULL," +
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

    public int getScoreTeam1() {
        return scoreTeam1;
    }

    public int getScoreTeam2() {
        return scoreTeam2;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setScoreTeam1(int scoreTeam1) {
        this.scoreTeam1 = scoreTeam1;
    }

    public void setScoreTeam2(int scoreTeam2) {
        this.scoreTeam2 = scoreTeam2;
    }
}
