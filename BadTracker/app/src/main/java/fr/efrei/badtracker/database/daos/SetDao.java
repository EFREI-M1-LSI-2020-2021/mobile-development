package fr.efrei.badtracker.database.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.efrei.badtracker.database.DbHelper;
import fr.efrei.badtracker.database.daos.interfaces.IPlayerDao;
import fr.efrei.badtracker.database.daos.interfaces.ISetDao;
import fr.efrei.badtracker.models.Player;
import fr.efrei.badtracker.models.Set;
import fr.efrei.badtracker.models.Set.SetEntry;

public class SetDao extends EntityDao<Set> implements ISetDao {

    private IPlayerDao playerDao;

    private static final String[] projectionAll = {
            SetEntry._ID,
            SetEntry.COLUMN_SCORE_WINNER,
            SetEntry.COLUMN_SCORE_LOSER,
            SetEntry.COLUMN_WINNER,
            SetEntry.COLUMN_LOSER
    };

    public SetDao(DbHelper dbHelper) {
        super(dbHelper);
        playerDao = dbHelper.getDao(IPlayerDao.class);
    }

    @Override
    public long add(long matchId, Set set) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SetEntry.COLUMN_SCORE_WINNER, set.getScoreWinner());
        values.put(SetEntry.COLUMN_SCORE_WINNER, set.getScoreLoser());
        values.put(SetEntry.COLUMN_WINNER, set.getWinner().getId());
        values.put(SetEntry.COLUMN_WINNER, set.getLoser().getId());

        return db.insert(SetEntry.TABLE_NAME, null, values);
    }

    @Override
    public List<Set> getMatchSets(long matchId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = SetEntry.COLUMN_MATCH + " = ?";
        String[] selectionArgs = { "" + matchId };

        Cursor cursor = db.query(
                SetEntry.TABLE_NAME,
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

        List<Set> sets = new ArrayList<>();

        while(cursor.moveToNext()) {
            sets.add(getFromCursor(cursor));
        }

        return sets;
    }

    @Override
    public int delete(Set set) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = SetEntry._ID + " = ?";
        String[] selectionArgs = { "" + set.getId() };

        return db.delete(SetEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public Set getById(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selection = SetEntry._ID + " = ?";
        String[] selectionArgs = { "" + id };

        Cursor cursor = db.query(
                SetEntry.TABLE_NAME,
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

    @Override
    protected Set getFromCursor(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(SetEntry._ID));
        int scoreWinnner = cursor.getInt(cursor.getColumnIndexOrThrow(SetEntry.COLUMN_SCORE_WINNER));
        int scoreLoser = cursor.getInt(cursor.getColumnIndexOrThrow(SetEntry.COLUMN_SCORE_LOSER));
        long winnerId = cursor.getLong(cursor.getColumnIndexOrThrow(SetEntry.COLUMN_WINNER));
        long loserId = cursor.getLong(cursor.getColumnIndexOrThrow(SetEntry.COLUMN_LOSER));

        Player winner = playerDao.getById(winnerId);
        Player loser = playerDao.getById(loserId);

        return new Set(id, scoreWinnner, scoreLoser, winner, loser);
    }
}
