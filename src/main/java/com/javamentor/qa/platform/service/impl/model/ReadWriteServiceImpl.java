package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.ReadOnlyDaoImpl;
import com.javamentor.qa.platform.dao.impl.model.ReadWriteDaoImpl;

import java.util.Collection;

public abstract class ReadWriteServiceImpl<E, K> extends ReadOnlyServiceImpl<E, K> {

    private ReadWriteDaoImpl<E, K> readWriteDao;

    public ReadWriteServiceImpl(ReadOnlyDaoImpl readOnlyDao, ReadWriteDaoImpl<E, K> readWriteDao) {
        super(readOnlyDao);
        this.readWriteDao = readWriteDao;
    }

    public void persist(E e) {
        readWriteDao.persist(e);
    }

    public void update(E e) {
        readWriteDao.update(e);
    }

    public void delete(E e) {
        readWriteDao.delete(e);
    }

    public void persistAll(E... entities) {
        readWriteDao.persistAll(entities);
    }

    public void persistAll(Collection<E> entities) {
        readWriteDao.persistAll(entities);
    }

    public void deleteAll(Collection<E> entities) {
        readWriteDao.deleteAll(entities);
    }

    public void updateAll(Iterable<? extends E> entities) {
        readWriteDao.updateAll(entities);
    }
}
