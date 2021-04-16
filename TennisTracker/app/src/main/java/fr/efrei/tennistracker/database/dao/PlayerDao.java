package fr.efrei.tennistracker.database.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import fr.efrei.tennistracker.database.DbHelper;
import fr.efrei.tennistracker.models.Player;
import fr.efrei.tennistracker.models.Player.PlayerEntry;

public class PlayerDao implements IDao<Player> {

    private DbHelper dbHelper;

    public PlayerDao(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public long add(Player entity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PlayerEntry.COLUMN_NAME_NAME, entity.getName());
        values.put(PlayerEntry.COLUMN_NAME_FIRSTNAME, entity.getFirstName());
        values.put(PlayerEntry.COLUMN_NAME_SEX, entity.getSex().toString());
        values.put(PlayerEntry.COLUMN_NAME_NATIONALITY, entity.getNationality());

        return db.insert(PlayerEntry.TABLE_NAME, null, values);
    }

    @Override
    public int delete(Player entity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = PlayerEntry._ID + " = ?";
        String[] selectionArgs = { "" + entity.getId() };

        return db.delete(PlayerEntry.TABLE_NAME, selection, selectionArgs);
    }
}
