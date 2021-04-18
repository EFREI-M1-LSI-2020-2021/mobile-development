package fr.efrei.badtracker.database.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.efrei.badtracker.database.DbHelper;
import fr.efrei.badtracker.database.daos.interfaces.IMatchDao;
import fr.efrei.badtracker.database.daos.interfaces.IMatchLocationDao;
import fr.efrei.badtracker.database.daos.interfaces.IMatchPlayerDao;
import fr.efrei.badtracker.database.daos.interfaces.IPlayerDao;
import fr.efrei.badtracker.database.daos.interfaces.ISetDao;
import fr.efrei.badtracker.models.Match;
import fr.efrei.badtracker.models.Match.MatchEntry;
import fr.efrei.badtracker.models.MatchLocation;
import fr.efrei.badtracker.models.MatchPlayer;
import fr.efrei.badtracker.models.Player;
import fr.efrei.badtracker.models.Set;

public class MatchDao extends EntityDao<Match> implements IMatchDao {

    private IMatchLocationDao matchLocationDao;
    private IPlayerDao playerDao;
    private ISetDao setDao;
    private IMatchPlayerDao matchPlayerDao;

    private static final String[] projectionAll = {
            MatchEntry._ID,
            MatchEntry.COLUMN_NAME,
            MatchEntry.COLUMN_LOCATION,
    };

    public MatchDao(DbHelper dbHelper) {
        super(dbHelper);
        matchLocationDao = dbHelper.getDao(IMatchLocationDao.class);
        playerDao = dbHelper.getDao(IPlayerDao.class);
        setDao = dbHelper.getDao(ISetDao.class);
        matchPlayerDao = dbHelper.getDao(IMatchPlayerDao.class);
    }

    @Override
    protected Match getFromCursor(Cursor cursor) {

        long id = cursor.getLong(cursor.getColumnIndexOrThrow(MatchEntry._ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(MatchEntry.COLUMN_NAME));
        long locationId = cursor.getLong(cursor.getColumnIndexOrThrow(MatchEntry.COLUMN_LOCATION));

        MatchLocation location = matchLocationDao.getById(locationId);

        List<MatchPlayer> matchPlayers = matchPlayerDao.getMatchPlayers(id);
        List<Player> winners = new ArrayList<>();
        List<Player> losers = new ArrayList<>();

        for(MatchPlayer matchPlayer : matchPlayers) {
            Player player = playerDao.getById(matchPlayer.getPlayerId());
            if(matchPlayer.isWinner()) {
                winners.add(player);
            }
            else {
                losers.add(player);
            }
        }

        List<Set> sets = setDao.getMatchSets(id);

        return new Match(id, name, location, winners, losers, sets);
    }

    @Override
    public long add(Match match) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long matchLocationId = matchLocationDao.add(match.getLocation());

        ContentValues values = new ContentValues();
        values.put(MatchEntry.COLUMN_NAME, match.getName());
        values.put(MatchEntry.COLUMN_LOCATION, matchLocationId);

        long matchId = db.insert(MatchEntry.TABLE_NAME, null, values);
        match.setId(matchId);

        for(Player player : match.getWinners()) {
            long playerId = playerDao.add(player);
            matchPlayerDao.add(new MatchPlayer(matchId, playerId, true));
            player.setId(playerId);
        }

        for(Player player : match.getLosers()) {
            long playerId = playerDao.add(player);
            matchPlayerDao.add(new MatchPlayer(matchId, playerId, false));
            player.setId(playerId);
        }

        for(Set set : match.getSets()) {
            long setId = setDao.add(matchId, set);
            set.setId(setId);
        }

        return matchId;
    }

    @Override
    public List<Match> getAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(
                MatchEntry.TABLE_NAME,
                projectionAll,
                null,
                null,
                null,
                null,
                null
        );

        if(cursor == null) {
            return null;
        }

        List<Match> matches = new ArrayList<>();

        while(cursor.moveToNext()) {
            matches.add(getFromCursor(cursor));
        }

        return matches;
    }

    @Override
    public int delete(Match match) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = MatchEntry._ID + " = ?";
        String[] selectionArgs = { "" + match.getId() };

        return db.delete(MatchEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public Match getById(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = MatchEntry._ID + " = ?";
        String[] selectionArgs = { "" + id };

        Cursor cursor = db.query(
                MatchEntry.TABLE_NAME,
                projectionAll,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor == null) {
            return null;
        }

        if(!cursor.moveToNext()) {
            return null;
        }

        return getFromCursor(cursor);
    }
}
