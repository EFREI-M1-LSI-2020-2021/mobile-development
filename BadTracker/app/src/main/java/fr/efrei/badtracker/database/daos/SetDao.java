package fr.efrei.badtracker.database.daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import fr.efrei.badtracker.database.DbHelper;
import fr.efrei.badtracker.database.daos.interfaces.IPlayerDao;
import fr.efrei.badtracker.database.daos.interfaces.ISetDao;
import fr.efrei.badtracker.database.daos.interfaces.ISetPlayerDao;
import fr.efrei.badtracker.models.Player;
import fr.efrei.badtracker.models.Set;
import fr.efrei.badtracker.models.Set.SetEntry;
import fr.efrei.badtracker.models.SetPlayer;

public class SetDao extends EntityDao<Set> implements ISetDao {

    private final IPlayerDao playerDao;
    private final ISetPlayerDao setPlayerDao;

    private static final String[] projectionAll = {
            SetEntry._ID,
            SetEntry.COLUMN_SCORE_WINNER,
            SetEntry.COLUMN_SCORE_LOSER,
    };

    public SetDao(DbHelper dbHelper) {
        super(dbHelper);
        playerDao = dbHelper.getDao(IPlayerDao.class);
        setPlayerDao = dbHelper.getDao(ISetPlayerDao.class);
    }

    @Override
    protected Set getFromCursor(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(SetEntry._ID));
        int scoreWinner = cursor.getInt(cursor.getColumnIndexOrThrow(SetEntry.COLUMN_SCORE_WINNER));
        int scoreLoser = cursor.getInt(cursor.getColumnIndexOrThrow(SetEntry.COLUMN_SCORE_LOSER));

        List<SetPlayer> setPlayers = setPlayerDao.getSetPlayers(id);
        List<Player> winners = new ArrayList<>();
        List<Player> losers = new ArrayList<>();

        for(SetPlayer setPlayer : setPlayers) {
            Player player = playerDao.getById(setPlayer.getPlayerId());
            if(setPlayer.isWinner()) {
                winners.add(player);
            }
            else {
                losers.add(player);
            }
        }

        return new Set(id, scoreWinner, scoreLoser, winners, losers);
    }

    @Override
    public long add(long matchId, Set set) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SetEntry.COLUMN_SCORE_WINNER, set.getScoreWinner());
        values.put(SetEntry.COLUMN_SCORE_LOSER, set.getScoreLoser());
        values.put(SetEntry.COLUMN_MATCH, matchId);

        long setId = db.insert(SetEntry.TABLE_NAME, null, values);
        set.setId(setId);

        for(Player player : set.getWinners()) {
            setPlayerDao.add(new SetPlayer(setId, player.getId(), true));
        }

        for(Player player : set.getLosers()) {
            setPlayerDao.add(new SetPlayer(setId, player.getId(), false));
        }

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
