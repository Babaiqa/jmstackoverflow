package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReadOnlyDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class ReadOnlyServiceImpl<E, K> {

    @Autowired
    private ReadOnlyDaoImpl<E, K> readOnlyDao;

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
        return readOnlyDao.existsByAllIds(ids);
    }
}
