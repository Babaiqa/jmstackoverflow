package com.javamentor.qa.platform.dao.impl.model;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;


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

    public void deleteById(K id) {
        Class<E> clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        E e = entityManager.find(clazz, id);
        entityManager.remove(e);
    }

    public void persistAll(E... entities) {

    }

    public void persistAll(Collection<E> entities) {

    }

    public void deleteAll(Collection<E> entities) {

    }

    public void updateAll(Iterable<? extends E> entities) {

    }
}
