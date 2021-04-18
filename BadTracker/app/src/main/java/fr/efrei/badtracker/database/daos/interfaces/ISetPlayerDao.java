package fr.efrei.badtracker.database.daos.interfaces;

import java.util.List;

import fr.efrei.badtracker.models.SetPlayer;

public interface ISetPlayerDao extends IDao<SetPlayer> {
    long add(SetPlayer setPlayer);
    List<SetPlayer> getSetPlayers(long setId);
}
