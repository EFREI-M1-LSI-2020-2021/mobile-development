package fr.efrei.badtracker.database.daos.interfaces;

import fr.efrei.badtracker.models.Player;

public interface IPlayerDao extends IDao<Player> {
    long add(Player player);
}
