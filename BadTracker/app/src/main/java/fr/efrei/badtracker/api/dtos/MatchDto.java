package fr.efrei.badtracker.api.dtos;

public class MatchDto {
    private String name;
    private LocationDto location;
    private PlayerDto[] winners;
    private PlayerDto[] losers;
    private SetDto[] sets;

    public MatchDto(String name, LocationDto location, PlayerDto[] winners, PlayerDto[] losers,
                    SetDto[] sets) {
        this.name = name;
        this.location = location;
        this.winners = winners;
        this.losers = losers;
        this.sets = sets;
    }
}
