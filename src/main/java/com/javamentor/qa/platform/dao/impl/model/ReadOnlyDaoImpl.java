package com.javamentor.qa.platform.dao.impl.model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class ReadOnlyDaoImpl<E, K> {

    @PersistenceContext
    private EntityManager entityManager;

    protected Class<E> genericClass;

    public ReadOnlyDaoImpl() {
        this.genericClass = (Class<E>) ((ParameterizedType) getClass()
                .getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

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
        if (ids != null && ids.iterator().hasNext()) {
            return entityManager.createQuery("from " + genericClass.getName() + " e WHERE e.id IN :ids")
                    .setParameter("ids", ids).getResultList();
        } else {
            return new ArrayList<>();
        }
    }

    public boolean existsByAllIds(Collection<K> ids) {
        return false;
    }
}
