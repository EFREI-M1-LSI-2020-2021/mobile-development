package fr.efrei.tennistracker.models;

import java.util.List;

public class Match {
    private MatchLocation location;
    private Player player1;
    private Player player2;
    private List<Set> sets;

    public Match(MatchLocation location, Player player1, Player player2, List<Set> sets) {
        this.location = location;
        this.player1 = player1;
        this.player2 = player2;
        this.sets = sets;
    }

    public MatchLocation getLocation() {
        return location;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public List<Set> getSets() {
        return sets;
    }
}
