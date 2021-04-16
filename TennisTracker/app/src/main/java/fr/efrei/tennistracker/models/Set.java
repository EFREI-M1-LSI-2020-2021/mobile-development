package fr.efrei.tennistracker.models;

import java.util.List;

public class Set {
    private int scorePlayer1;
    private int scorePlayer2;
    List<Game> games;
    private Player winner;

    public Set(int scorePlayer1, int scorePlayer2, List<Game> games, Player winner) {
        this.scorePlayer1 = scorePlayer1;
        this.scorePlayer2 = scorePlayer2;
        this.games = games;
        this.winner = winner;
    }

    public int getScorePlayer1() {
        return scorePlayer1;
    }

    public int getScorePlayer2() {
        return scorePlayer2;
    }

    public List<Game> getGames() {
        return games;
    }

    public Player getWinner() {
        return winner;
    }

    public void setScorePlayer1(int scorePlayer1) {
        this.scorePlayer1 = scorePlayer1;
    }

    public void setScorePlayer2(int scorePlayer2) {
        this.scorePlayer2 = scorePlayer2;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
