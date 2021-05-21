package fr.efrei.badtracker.api.dtos;

import android.location.Location;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import fr.efrei.badtracker.models.Match;
import fr.efrei.badtracker.models.MatchLocation;
import fr.efrei.badtracker.models.Player;
import fr.efrei.badtracker.models.Set;


public class CreateMatchDto {
    private String name;
    private MatchLocation location;
    private Date date;
    private List<Player> team1;
    private List<Player> team2;
    private List<Set> sets;

    public CreateMatchDto(Match match) {
        this.name = match.getName();
        this.location = match.getLocation();
        this.date = match.getDate();
        this.team1 = match.getTeam1();
        this.team2 = match.getTeam2();
        this.sets = match.getSets();
    }
}
