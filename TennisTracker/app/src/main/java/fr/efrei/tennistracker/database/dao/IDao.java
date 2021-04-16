package fr.efrei.tennistracker.database.dao;

public interface IDao<T> {
    long add(T entity);
    int delete(T entity);
}
