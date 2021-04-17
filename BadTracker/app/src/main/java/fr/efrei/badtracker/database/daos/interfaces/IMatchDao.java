package fr.efrei.badtracker.database.daos.interfaces;

import fr.efrei.badtracker.models.Match;

public interface IMatchDao extends IDao<Match> {
    long add(Match match);
}
