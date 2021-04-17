package fr.efrei.badtracker.database.daos.interfaces;

public interface IDao<T> {
    int delete(T entity);
    T getById(long id);
}
