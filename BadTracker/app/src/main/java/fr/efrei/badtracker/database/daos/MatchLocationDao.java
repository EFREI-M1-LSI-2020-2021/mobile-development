package fr.efrei.badtracker.database.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fr.efrei.badtracker.database.DbHelper;
import fr.efrei.badtracker.database.daos.interfaces.IMatchLocationDao;
import fr.efrei.badtracker.models.MatchLocation;
import fr.efrei.badtracker.models.MatchLocation.MatchLocationEntry;

public class MatchLocationDao extends EntityDao<MatchLocation> implements IMatchLocationDao {
    public MatchLocationDao(DbHelper dbHelper) {
        super(dbHelper);
    }

    @Override
    protected MatchLocation getFromCursor(Cursor cursor) {

        long id = cursor.getLong(cursor.getColumnIndexOrThrow(MatchLocationEntry._ID));
        double latitude = cursor.getDouble(cursor.getColumnIndexOrThrow(MatchLocationEntry.COLUMN_LATITUDE));
        double longitude = cursor.getDouble(cursor.getColumnIndexOrThrow(MatchLocationEntry.COLUMN_LONGITUDE));
        String street = cursor.getString(cursor.getColumnIndexOrThrow(MatchLocationEntry.COLUMN_STREET));

        return new MatchLocation(id, latitude, longitude, street);
    }

    @Override
    public long add(MatchLocation matchLocation) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MatchLocationEntry.COLUMN_LATITUDE, matchLocation.getLatitude());
        values.put(MatchLocationEntry.COLUMN_LONGITUDE, matchLocation.getLongitude());
        values.put(MatchLocationEntry.COLUMN_STREET, matchLocation.getStreet());

        return db.insert(MatchLocationEntry.TABLE_NAME, null, values);
    }

    @Override
    public int delete(MatchLocation matchLocation) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = MatchLocationEntry._ID + " = ?";
        String[] selectionArgs = { "" + matchLocation.getId() };

        return db.delete(MatchLocationEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public MatchLocation getById(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                MatchLocationEntry._ID,
                MatchLocationEntry.COLUMN_LATITUDE,
                MatchLocationEntry.COLUMN_LONGITUDE,
                MatchLocationEntry.COLUMN_STREET
        };

        String selection = MatchLocationEntry._ID + " = ?";
        String[] selectionArgs = { "" + id };

        Cursor cursor = db.query(
                MatchLocationEntry.TABLE_NAME,
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

        if(!cursor.moveToNext()) {
            return null;
        }

        return getFromCursor(cursor);
    }
}
