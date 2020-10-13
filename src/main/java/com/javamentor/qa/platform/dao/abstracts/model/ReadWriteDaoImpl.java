package com.javamentor.qa.platform.dao.abstracts.model;


import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

@Repository
public abstract class ReadWriteDaoImpl<E, K> extends ReadOnlyDaoImpl<E, K> {

    @PersistenceContext
    private EntityManager entityManager;

    public void persist(E e) {
        entityManager.persist(e);
    }

    public void update(E e) {
        entityManager.merge(e);
    }

    public void delete(E e) {
        entityManager.remove(e);
    }

    public abstract void persistAll(E... entities);

    public abstract void persistAll(Collection<E> entities);

    public abstract void deleteAll(Collection<E> entities);

    public abstract void updateAll(Iterable<? extends E> entities);
}
