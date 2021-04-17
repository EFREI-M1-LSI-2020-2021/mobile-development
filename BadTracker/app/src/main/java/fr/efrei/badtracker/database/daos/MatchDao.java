package fr.efrei.badtracker.database.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import fr.efrei.badtracker.database.DbHelper;
import fr.efrei.badtracker.database.daos.interfaces.IMatchDao;
import fr.efrei.badtracker.database.daos.interfaces.IMatchLocationDao;
import fr.efrei.badtracker.database.daos.interfaces.IPlayerDao;
import fr.efrei.badtracker.database.daos.interfaces.ISetDao;
import fr.efrei.badtracker.models.Match;
import fr.efrei.badtracker.models.Match.MatchEntry;
import fr.efrei.badtracker.models.MatchLocation;
import fr.efrei.badtracker.models.Player;
import fr.efrei.badtracker.models.Set;

public class MatchDao extends EntityDao<Match> implements IMatchDao {

    private IMatchLocationDao matchLocationDao;
    private IPlayerDao playerDao;
    private ISetDao setDao;

    public MatchDao(DbHelper dbHelper) {
        super(dbHelper);
        matchLocationDao = dbHelper.getDao(IMatchLocationDao.class);
        playerDao = dbHelper.getDao(IPlayerDao.class);
        setDao = dbHelper.getDao(ISetDao.class);
    }

    @Override
    public long add(Match match) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        long matchLocationId = matchLocationDao.add(match.getLocation());
        long winnerId = playerDao.add(match.getWinner());
        long loserId = playerDao.add(match.getLoser());

        ContentValues values = new ContentValues();
        values.put(MatchEntry.COLUMN_LOCATION, matchLocationId);
        values.put(MatchEntry.COLUMN_WINNER, winnerId);
        values.put(MatchEntry.COLUMN_LOSER, loserId);

        long matchId = db.insert(MatchEntry.TABLE_NAME, null, values);

        for(Set set : match.getSets()) {
            long setId = setDao.add(matchId, set);
            set.setId(setId);
        }

        return matchId;
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

        String[] projection = {
                MatchEntry._ID,
                MatchEntry.COLUMN_LOCATION,
                MatchEntry.COLUMN_WINNER,
                MatchEntry.COLUMN_LOSER
        };

        String selection = MatchEntry._ID + " = ?";
        String[] selectionArgs = { "" + id };

        Cursor cursor = db.query(
                MatchEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if(cursor == null) {
            return null;
        }

        return getFromCursor(cursor);
    }

    @Override
    protected Match getFromCursor(Cursor cursor) {
        if(!cursor.moveToNext()) {
            return null;
        }

        long id = cursor.getLong(cursor.getColumnIndexOrThrow(MatchEntry._ID));
        long locationId = cursor.getLong(cursor.getColumnIndexOrThrow(MatchEntry._ID));
        long winnerId = cursor.getLong(cursor.getColumnIndexOrThrow(MatchEntry._ID));
        long loserId = cursor.getLong(cursor.getColumnIndexOrThrow(MatchEntry._ID));

        MatchLocation location = matchLocationDao.getById(locationId);
        Player winner = playerDao.getById(winnerId);
        Player loser = playerDao.getById(loserId);
        List<Set> sets = setDao.getMatchSets(id);

        return new Match(id, location, winner, loser, sets);
    }
}
