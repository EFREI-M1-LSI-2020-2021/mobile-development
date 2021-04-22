package fr.efrei.badtracker.api.dtos;

import java.util.List;

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
}
