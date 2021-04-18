package fr.efrei.badtracker.database.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import fr.efrei.badtracker.database.DbHelper;
import fr.efrei.badtracker.database.daos.interfaces.IPlayerDao;
import fr.efrei.badtracker.models.Player;
import fr.efrei.badtracker.models.Player.PlayerEntry;
import fr.efrei.badtracker.models.Sex;

public class PlayerDao extends EntityDao<Player> implements IPlayerDao {

    public PlayerDao(DbHelper dbHelper) {
        super(dbHelper);
    }

    @Override
    public long add(Player player) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PlayerEntry.COLUMN_NAME, player.getName());
        values.put(PlayerEntry.COLUMN_FIRST_NAME, player.getFirstName());
        values.put(PlayerEntry.COLUMN_SEX, player.getSex().toString());
        values.put(PlayerEntry.COLUMN_NATIONALITY, player.getNationality());

        return db.insert(PlayerEntry.TABLE_NAME, null, values);
    }

    @Override
    public int delete(Player player) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = BaseColumns._ID + " = ?";
        String[] selectionArgs = { "" + player.getId() };

        return db.delete(PlayerEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public Player getById(long id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                PlayerEntry._ID,
                PlayerEntry.COLUMN_NAME,
                PlayerEntry.COLUMN_FIRST_NAME,
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

        if(cursor == null) {
            return null;
        }

        if(!cursor.moveToNext()) {
            return null;
        }

        return getFromCursor(cursor);
    }

    @Override
    protected Player getFromCursor(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(PlayerEntry._ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(PlayerEntry.COLUMN_NAME));
        String firstName = cursor.getString(cursor.getColumnIndexOrThrow(PlayerEntry.COLUMN_FIRST_NAME));
        String sexString = cursor.getString(cursor.getColumnIndexOrThrow(PlayerEntry.COLUMN_SEX));
        Sex sex = Enum.valueOf(Sex.class, sexString);
        String nationality = cursor.getString(cursor.getColumnIndexOrThrow(PlayerEntry.COLUMN_NATIONALITY));

        return new Player(id, name, firstName, sex, nationality);
    }
}
