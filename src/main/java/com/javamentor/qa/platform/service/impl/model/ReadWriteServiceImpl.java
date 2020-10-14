package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.ReadWriteDaoImpl;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;

public abstract class ReadWriteServiceImpl<E, K> extends ReadOnlyServiceImpl<E, K> {

    private ReadWriteDaoImpl<E, K> readWriteDao;

    public ReadWriteServiceImpl(ReadWriteDaoImpl<E, K> readWriteDao) {
        super(readWriteDao);
        this.readWriteDao = readWriteDao;
    }

    @Transactional
    public void persist(E e) {
        readWriteDao.persist(e);
    }

    @Transactional
    public void update(E e) {
        readWriteDao.update(e);
    }

    @Transactional
    public void delete(E e) {
        readWriteDao.delete(e);
    }

    @Transactional
    public void deleteById(K id) {
        readWriteDao.deleteById(id);
    }

    @Transactional
    public void persistAll(E... entities) {
        readWriteDao.persistAll(entities);
    }

    @Transactional
    public void persistAll(Collection<E> entities) {
        readWriteDao.persistAll(entities);
    }

    @Transactional
    public void deleteAll(Collection<E> entities) {
        readWriteDao.deleteAll(entities);
    }

    @Transactional
    public void updateAll(Iterable<? extends E> entities) {
        readWriteDao.updateAll(entities);
    }
}
