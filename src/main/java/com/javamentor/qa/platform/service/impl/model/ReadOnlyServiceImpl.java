package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.ReadOnlyDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public abstract class ReadOnlyServiceImpl<E, K> {

    private ReadOnlyDaoImpl<E, K> readOnlyDao;

    @Autowired
    public ReadOnlyServiceImpl(@Qualifier("readOnlyDaoImpl") ReadOnlyDaoImpl<E, K> readOnlyDao) {
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
        return readOnlyDao.existsByAllIds(ids);
    }
}
