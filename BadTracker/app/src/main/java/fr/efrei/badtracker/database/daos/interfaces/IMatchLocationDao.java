package fr.efrei.badtracker.database.daos.interfaces;

import fr.efrei.badtracker.models.MatchLocation;

public interface IMatchLocationDao extends IDao<MatchLocation> {
    long add(MatchLocation matchLocation);
}
