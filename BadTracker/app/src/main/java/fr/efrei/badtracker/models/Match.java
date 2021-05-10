package fr.efrei.badtracker.models;

import android.provider.BaseColumns;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Match {
    private long id;
    private String name;
    private MatchLocation location;
    private String image;
    private Timestamp date;
    private List<Player> team1;
    private List<Player> team2;
    private List<Set> sets;

    public Match() {}

    public Match(long id, String name, MatchLocation location, String image, Timestamp date,
                 List<Player> team1, List<Player> team2, List<Set> sets) {
        this(name, location, image, date, team1, team2, sets);
        this.id = id;
    }

    public Match(String name, MatchLocation location, String image, Timestamp date,
                 List<Player> team1, List<Player> team2, List<Set> sets) {
        this.name = name;
        this.location = location;
        this.image = image;
        this.date = date;
        this.team1 = team1;
        this.team2 = team2;
        this.sets = sets;
    }

    public static class MatchEntry implements BaseColumns {
        public static final String TABLE_NAME = "matches";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LOCATION = "locationId";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_DATE = "date";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME + " TEXT," +
                        COLUMN_LOCATION + " INTEGER" +
                        COLUMN_IMAGE + " TEXT)" +
                        COLUMN_DATE + " UNSIGNED BIG INT);";

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

    public String getImage() {
        return image;
    }

    public Date getDate() {
        return date;
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

    public void setImage(String image) {
        this.image = image;
    }

    public void setDate(Timestamp date) {
        this.date = date;
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

    public String getTeamName(List<Player> players) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            stringBuilder.append(player.getName()).append(" ").append(player.getFirstName());
            if(i + 1 < players.size()) {
                stringBuilder.append(" - ");
            }
        }

        return stringBuilder.toString();
    }

    public boolean isteam1Winner(Match match) {
        int team1Sets = 0;
        for(Set set : match.getSets()) {
            if(set.getScoreTeam1() > set.getScoreTeam2()) {
                team1Sets++;
            }
        }

        return team1Sets == 2;
    }
}
