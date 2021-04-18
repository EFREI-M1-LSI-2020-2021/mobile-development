package fr.efrei.badtracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import fr.efrei.badtracker.database.daos.MatchLocationDao;
import fr.efrei.badtracker.database.daos.MatchPlayerDao;
import fr.efrei.badtracker.database.daos.PlayerDao;
import fr.efrei.badtracker.database.daos.SetDao;
import fr.efrei.badtracker.database.daos.SetPlayerDao;
import fr.efrei.badtracker.database.daos.interfaces.IDao;
import fr.efrei.badtracker.database.daos.interfaces.IMatchDao;
import fr.efrei.badtracker.database.daos.interfaces.IMatchLocationDao;
import fr.efrei.badtracker.database.daos.interfaces.IMatchPlayerDao;
import fr.efrei.badtracker.database.daos.interfaces.IPlayerDao;
import fr.efrei.badtracker.database.daos.interfaces.ISetDao;
import fr.efrei.badtracker.database.daos.interfaces.ISetPlayerDao;
import fr.efrei.badtracker.models.Match.MatchEntry;
import fr.efrei.badtracker.models.MatchLocation.MatchLocationEntry;
import fr.efrei.badtracker.models.Player.PlayerEntry;
import fr.efrei.badtracker.models.Set.SetEntry;
import fr.efrei.badtracker.models.SetPlayer.SetPlayerEntry;
import fr.efrei.badtracker.models.MatchPlayer.MatchPlayerEntry;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Database.db";

    private static DbHelper dbHelper;

    private final Map<Class<? extends IDao>, IDao> daos = new HashMap<>();

    private DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        daos.put(IPlayerDao.class, new PlayerDao(this));
        daos.put(IMatchLocationDao.class, new MatchLocationDao(this));
        daos.put(IMatchDao.class, new SetDao(this));
        daos.put(ISetDao.class, new SetDao(this));
        daos.put(IMatchPlayerDao.class, new MatchPlayerDao(this));
        daos.put(ISetPlayerDao.class, new SetPlayerDao(this));
    }

    public DbHelper getInstance(Context context) {
        if(dbHelper == null) {
            dbHelper = new DbHelper(context);
        }
        return dbHelper;
    }

    public <T extends IDao> T getDao(Class<T> key) {
        return (T) daos.get(key);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PlayerEntry.SQL_CREATE_ENTRIES);
        db.execSQL(MatchLocationEntry.SQL_CREATE_ENTRIES);
        db.execSQL(MatchEntry.SQL_CREATE_ENTRIES);
        db.execSQL(MatchPlayerEntry.SQL_CREATE_ENTRIES);
        db.execSQL(SetEntry.SQL_CREATE_ENTRIES);
        db.execSQL(SetPlayerEntry.SQL_CREATE_ENTRIES);
        db.execSQL(MatchPlayerEntry.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(PlayerEntry.SQL_DELETE_ENTRIES);
        db.execSQL(MatchLocationEntry.SQL_DELETE_ENTRIES);
        db.execSQL(MatchEntry.SQL_DELETE_ENTRIES);
        db.execSQL(MatchPlayerEntry.SQL_DELETE_ENTRIES);
        db.execSQL(SetEntry.SQL_DELETE_ENTRIES);
        db.execSQL(SetPlayerEntry.SQL_DELETE_ENTRIES);
        db.execSQL(MatchPlayerEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
