package fr.efrei.badtracker.database.daos.interfaces;

import java.util.List;

import fr.efrei.badtracker.models.Set;

public interface ISetDao extends IDao<Set> {
    long add(long matchId, Set set);
    List<Set> getMatchSets(long matchId);
}
