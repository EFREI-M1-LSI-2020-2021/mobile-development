package fr.efrei.badtracker.database.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.efrei.badtracker.database.DbHelper;
import fr.efrei.badtracker.database.daos.interfaces.ISetPlayerDao;
import fr.efrei.badtracker.models.SetPlayer;
import fr.efrei.badtracker.models.SetPlayer.SetPlayerEntry;

public class SetPlayerDao extends EntityDao<SetPlayer> implements ISetPlayerDao {
    public SetPlayerDao(DbHelper dbHelper) {
        super(dbHelper);
    }

    @Override
    protected SetPlayer getFromCursor(Cursor cursor) {
        long setId = cursor.getLong(cursor.getColumnIndexOrThrow(SetPlayerEntry.COLUMN_SET));
        long playerId = cursor.getLong(cursor.getColumnIndexOrThrow(SetPlayerEntry.COLUMN_PLAYER));
        int winner = cursor.getInt(cursor.getColumnIndexOrThrow(SetPlayerEntry.COLUMN_WINNER));

        return new SetPlayer(setId, playerId, winner > 0);
    }

    @Override
    public long add(SetPlayer setPlayer) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SetPlayerEntry.COLUMN_SET, setPlayer.getSetId());
        values.put(SetPlayerEntry.COLUMN_PLAYER, setPlayer.getPlayerId());
        values.put(SetPlayerEntry.COLUMN_WINNER, setPlayer.isWinner());

        return db.insert(SetPlayerEntry.TABLE_NAME, null, values);
    }

    @Override
    public List<SetPlayer> getSetPlayers(long setId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = SetPlayerEntry.COLUMN_SET + " = ?";
        String[] selectionArgs = {"" + setId};

        String[] projection = {
                SetPlayerEntry.COLUMN_SET,
                SetPlayerEntry.COLUMN_PLAYER,
                SetPlayerEntry.COLUMN_WINNER
        };

        Cursor cursor = db.query(
                SetPlayerEntry.TABLE_NAME,
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

        List<SetPlayer> players = new ArrayList<>();

        while(cursor.moveToNext()) {
            players.add(getFromCursor(cursor));
        }

        return players;
    }

    @Override
    public int delete(SetPlayer setPlayer) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = SetPlayerEntry.COLUMN_SET + " = ? AND " +
                SetPlayerEntry.COLUMN_PLAYER + " = ?";
        String[] selectionArgs = {
                "" + setPlayer.getSetId(),
                "" + setPlayer.getPlayerId()
        };

        return db.delete(SetPlayerEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public SetPlayer getById(long id) {
        return null;
    }
}
