package fr.efrei.badtracker.database.daos.interfaces;

import java.util.List;

import fr.efrei.badtracker.models.Match;

public interface IMatchDao extends IDao<Match> {
    long add(Match match);
    List<Match> getAll();

    Match safeAdd(Match match);
}
