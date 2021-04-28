package fr.efrei.badtracker.api.dtos;

import java.util.ArrayList;
import java.util.List;

import fr.efrei.badtracker.models.Match;
import fr.efrei.badtracker.models.Player;
import fr.efrei.badtracker.models.Set;

public class MatchDto {
    private String name;
    private LocationDto location;
    private List<PlayerDto> team1;
    private List<PlayerDto> team2;
    private List<SetDto> sets;

    public MatchDto(String name, LocationDto location, List<PlayerDto> team1, List<PlayerDto> team2, List<SetDto> sets) {
        this.name = name;
        this.location = location;
        this.team1 = team1;
        this.team2 = team2;
        this.sets = sets;
    }

    public Match toMatch() {
        List<Player> team1 = new ArrayList<>();
        List<Player> team2 = new ArrayList<>();
        List<Set> sets = new ArrayList<>();

        for(PlayerDto playerDto : this.team1) {
            team1.add(playerDto.toPlayer());
        }

        for(PlayerDto playerDto : this.team2) {
            team2.add(playerDto.toPlayer());
        }

        for(SetDto setDto : this.sets) {
            sets.add(setDto.toSet());
        }

        return new Match(name, location.toMatchLocation(), team1, team2, sets);
    }
}
