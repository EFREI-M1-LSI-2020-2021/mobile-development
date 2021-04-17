package fr.efrei.badtracker.database.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fr.efrei.badtracker.database.DbHelper;
import fr.efrei.badtracker.models.Player;
import fr.efrei.badtracker.models.Player.PlayerEntry;
import fr.efrei.badtracker.models.Sex;

public class PlayerDao implements IDao<Player> {

    private DbHelper dbHelper;

    public PlayerDao(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public long add(Player entity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PlayerEntry.COLUMN_NAME, entity.getName());
        values.put(PlayerEntry.COLUMN_FIRSTNAME, entity.getFirstName());
        values.put(PlayerEntry.COLUMN_SEX, entity.getSex().toString());
        values.put(PlayerEntry.COLUMN_NATIONALITY, entity.getNationality());

        return db.insert(PlayerEntry.TABLE_NAME, null, values);
    }

    @Override
    public int delete(Player entity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = PlayerEntry._ID + " = ?";
        String[] selectionArgs = { "" + entity.getId() };

        return db.delete(PlayerEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public Player getById(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                PlayerEntry._ID,
                PlayerEntry.COLUMN_NAME,
                PlayerEntry.COLUMN_FIRSTNAME,
                PlayerEntry.COLUMN_SEX,
                PlayerEntry.COLUMN_NATIONALITY
        };

        String selection = PlayerEntry._ID + " = ?";
        String[] selectionArgs = { "" + id };

        Cursor cursor = db.query(
                PlayerEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        return getFromCursor(cursor);
    }

    @Override
    public Player getFromCursor(Cursor cursor) {
        if(!cursor.moveToNext()) {
            return null;
        }

        long id = cursor.getLong(cursor.getColumnIndexOrThrow(PlayerEntry._ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(PlayerEntry.COLUMN_NAME));
        String firstName = cursor.getString(cursor.getColumnIndexOrThrow(PlayerEntry.COLUMN_FIRSTNAME));
        String sexString = cursor.getString(cursor.getColumnIndexOrThrow(PlayerEntry.COLUMN_SEX));
        Sex sex = Enum.valueOf(Sex.class, sexString);
        String nationality = cursor.getString(cursor.getColumnIndexOrThrow(PlayerEntry.COLUMN_NATIONALITY));

        return new Player(id, name, firstName, sex, nationality);
    }
}
