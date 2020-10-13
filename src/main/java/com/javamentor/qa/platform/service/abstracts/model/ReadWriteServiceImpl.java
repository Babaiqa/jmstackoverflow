package com.javamentor.qa.platform.service.abstracts.model;

import com.javamentor.qa.platform.dao.abstracts.model.ReadWriteDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public abstract class ReadWriteServiceImpl<E, K> extends ReadOnlyServiceImpl<E, K> {

    private ReadWriteDaoImpl<E, K> readWriteDao;

    @Autowired
    public void setReadWriteDao(ReadWriteDaoImpl<E, K> readWriteDao) {
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
