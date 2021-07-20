package com.ustudy.requestservice.services.interfaces;

public interface EntityService<T, V> {
    T save(T entity);

    T update(T entity);

    void delete(T entity);

    void deleteById(V id);

    T getById(Long id) throws Exception;
}
