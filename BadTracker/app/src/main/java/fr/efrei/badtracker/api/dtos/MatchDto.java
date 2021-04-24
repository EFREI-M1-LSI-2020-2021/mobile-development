package fr.efrei.badtracker.api.dtos;

import java.util.ArrayList;
import java.util.List;

import fr.efrei.badtracker.models.Match;
import fr.efrei.badtracker.models.Player;
import fr.efrei.badtracker.models.Set;

public class MatchDto {
    private String name;
    private LocationDto location;
    private List<PlayerDto> winners;
    private List<PlayerDto> losers;
    private List<SetDto> sets;

    public MatchDto(String name, LocationDto location, List<PlayerDto> winners, List<PlayerDto> losers, List<SetDto> sets) {
        this.name = name;
        this.location = location;
        this.winners = winners;
        this.losers = losers;
        this.sets = sets;
    }

    public Match toMatch() {
        List<Player> winners = new ArrayList<>();
        List<Player> losers = new ArrayList<>();
        List<Set> sets = new ArrayList<>();

        for(PlayerDto playerDto : this.winners) {
            winners.add(playerDto.toPlayer());
        }

        for(PlayerDto playerDto : this.losers) {
            losers.add(playerDto.toPlayer());
        }

        for(SetDto setDto : this.sets) {
            sets.add(setDto.toSet());
        }

        return new Match(name, location.toMatchLocation(), winners, losers, sets);
    }
}
