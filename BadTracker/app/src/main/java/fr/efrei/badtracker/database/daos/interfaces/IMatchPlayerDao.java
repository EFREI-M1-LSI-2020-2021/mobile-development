package fr.efrei.badtracker.database.daos.interfaces;

import java.util.List;

import fr.efrei.badtracker.models.MatchPlayer;

public interface IMatchPlayerDao extends IDao<MatchPlayer> {
    long add(MatchPlayer matchPlayer);
    List<MatchPlayer> getMatchPlayers(long matchId);
}
