package com.javamentor.qa.platform.dao.impl.model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class ReadOnlyDaoImpl<E, K> {

    @PersistenceContext
    private EntityManager entityManager;

    public List<E> getAll() {
        return null;
    }

    public boolean existsById(K id) {
        return false;
    }

    public Optional<E> getById(K id) {
        return Optional.empty();
    }

    public List<E> getAllByIds(Iterable<K> ids) {
        Class genericClass = getCurrentGenericClass();
        return entityManager.createQuery("SELECT e FROM genericClass e WHERE e.id IN :ids", genericClass).setParameter("ids", ids).getResultList();
    }

    public boolean existsByAllIds(Collection<K> ids) {
        return false;
    }

    private Class getCurrentGenericClass() {
        return (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }
}
