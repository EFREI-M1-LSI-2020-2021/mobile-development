package fr.efrei.tennistracker.models;

public class Game {
    private int scorePlayer1;
    private int scorePlayer2;
    private Player winner;

    public Game(int scorePlayer1, int scorePlayer2, Player winner) {
        this.scorePlayer1 = scorePlayer1;
        this.scorePlayer2 = scorePlayer2;
        this.winner = winner;
    }

    public int getScorePlayer1() {
        return scorePlayer1;
    }

    public int getScorePlayer2() {
        return scorePlayer2;
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

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
