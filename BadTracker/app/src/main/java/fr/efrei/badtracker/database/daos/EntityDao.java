package fr.efrei.badtracker.database.daos;

import android.database.Cursor;

import fr.efrei.badtracker.database.DbHelper;

public abstract class EntityDao<T> {

    protected DbHelper dbHelper;

    public EntityDao(DbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    protected abstract T getFromCursor(Cursor cursor);
}
