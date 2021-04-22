package fr.efrei.badtracker.api.dtos;

public class SetDto {
    private final int scoreTeam1;
    private final int scoreTeam2;

    public SetDto(int scoreTeam1, int scoreTeam2) {
        this.scoreTeam1 = scoreTeam1;
        this.scoreTeam2 = scoreTeam2;
    }
}
