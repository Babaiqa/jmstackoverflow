package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.ReadOnlyDaoImpl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class ReadOnlyServiceImpl<E, K> {

    private ReadOnlyDaoImpl<E, K> readOnlyDao;

    public ReadOnlyServiceImpl(ReadOnlyDaoImpl readOnlyDao) {
        this.readOnlyDao = readOnlyDao;
    }

    public List<E> getAll() {
        return readOnlyDao.getAll();
    }

    public boolean existsById(K id) {
        return readOnlyDao.existsById(id);
    }

    public Optional<E> getById(K id) {
        return readOnlyDao.getById(id);
    }

    public List<E> getAllByIds(Iterable<K> ids) {
        return readOnlyDao.getAllByIds(ids);
    }

    public boolean existsByAllIds(Collection<K> ids) {
        return existsByAllIds(ids);
    }
}
