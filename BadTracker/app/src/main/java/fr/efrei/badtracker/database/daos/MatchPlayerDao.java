package fr.efrei.badtracker.database.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.efrei.badtracker.database.DbHelper;
import fr.efrei.badtracker.database.daos.interfaces.IMatchPlayerDao;
import fr.efrei.badtracker.models.MatchPlayer;
import fr.efrei.badtracker.models.MatchPlayer.MatchPlayerEntry;

public class MatchPlayerDao extends EntityDao<MatchPlayer> implements IMatchPlayerDao {
    public MatchPlayerDao(DbHelper dbHelper) {
        super(dbHelper);
    }

    @Override
    protected MatchPlayer getFromCursor(Cursor cursor) {
        long matchId = cursor.getLong(cursor.getColumnIndexOrThrow(MatchPlayerEntry.COLUMN_MATCH));
        long playerId = cursor.getLong(cursor.getColumnIndexOrThrow(MatchPlayerEntry.COLUMN_PLAYER));
        int winner = cursor.getInt(cursor.getColumnIndexOrThrow(MatchPlayerEntry.COLUMN_WINNER));

        return new MatchPlayer(matchId, playerId, winner > 0);
    }

    @Override
    public long add(MatchPlayer matchPlayer) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MatchPlayerEntry.COLUMN_MATCH, matchPlayer.getMatchId());
        values.put(MatchPlayerEntry.COLUMN_PLAYER, matchPlayer.getPlayerId());
        values.put(MatchPlayerEntry.COLUMN_WINNER, matchPlayer.isWinner());

        return db.insert(MatchPlayerEntry.TABLE_NAME, null, values);
    }

    @Override
    public List<MatchPlayer> getMatchPlayers(long matchId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = MatchPlayerEntry.COLUMN_MATCH + " = ?";
        String[] selectionArgs = {"" + matchId};

        String[] projection = {
                MatchPlayerEntry.COLUMN_MATCH,
                MatchPlayerEntry.COLUMN_PLAYER,
                MatchPlayerEntry.COLUMN_WINNER
        };

        Cursor cursor = db.query(
                MatchPlayerEntry.TABLE_NAME,
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

        List<MatchPlayer> players = new ArrayList<>();

        while(cursor.moveToNext()) {
            players.add(getFromCursor(cursor));
        }

        return players;
    }

    @Override
    public int delete(MatchPlayer matchPlayer) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String selection = MatchPlayerEntry.COLUMN_MATCH + " = ? AND " +
                MatchPlayerEntry.COLUMN_PLAYER + " = ?";
        String[] selectionArgs = {
                "" + matchPlayer.getMatchId(),
                "" + matchPlayer.getPlayerId()
        };

        return db.delete(MatchPlayerEntry.TABLE_NAME, selection, selectionArgs);
    }

    @Override
    public MatchPlayer getById(long id) {
        return null;
    }
}
