package fr.efrei.badtracker.database.dao;

import android.database.Cursor;

public interface IDao<T> {
    long add(T entity);
    int delete(T entity);
    T getById(long id);
    T getFromCursor(Cursor cursor);
}
