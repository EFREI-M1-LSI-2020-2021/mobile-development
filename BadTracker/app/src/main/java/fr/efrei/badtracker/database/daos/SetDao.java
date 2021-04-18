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

    private final IPlayerDao playerDao;

    private static final String[] projectionAll = {
            SetEntry._ID,
            SetEntry.COLUMN_SCORE_TEAM1,
            SetEntry.COLUMN_SCORE_TEAM2
    };

    public SetDao(DbHelper dbHelper) {
        super(dbHelper);
        playerDao = dbHelper.getDao(IPlayerDao.class);
    }

    @Override
    protected Set getFromCursor(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(SetEntry._ID));
        int scoreTeam1 = cursor.getInt(cursor.getColumnIndexOrThrow(SetEntry.COLUMN_SCORE_TEAM1));
        int scoreTeam2 = cursor.getInt(cursor.getColumnIndexOrThrow(SetEntry.COLUMN_SCORE_TEAM2));

        return new Set(id, scoreTeam1, scoreTeam2);
    }

    @Override
    public long add(long matchId, Set set) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SetEntry.COLUMN_SCORE_TEAM1, set.getScoreTeam1());
        values.put(SetEntry.COLUMN_SCORE_TEAM2, set.getScoreTeam2());
        values.put(SetEntry.COLUMN_MATCH, matchId);

        long setId = db.insert(SetEntry.TABLE_NAME, null, values);
        set.setId(setId);

        return setId;
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
        return null;
    }
}
