package com.javamentor.qa.platform.dao.impl.model;

import com.javamentor.qa.platform.dao.util.SingleResultUtil;

import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
        Class<E> clazz = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        String hql = "FROM " + clazz.getName() + " WHERE id = :id";
        TypedQuery<E> query= (TypedQuery<E>) entityManager.createQuery(hql).setParameter("id", id);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    public Optional<E> getByEmail(String email) {
        Class<E> eClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        String hql = "FROM " + eClass.getName() + " WHERE email = :email";
        TypedQuery<E> query = (TypedQuery<E>) entityManager.createQuery(hql).setParameter("email", email);
        return SingleResultUtil.getSingleResultOrNull(query);
    }

    public List<E> getAllByIds(Iterable<K> ids) {
        return null;
    }

    public boolean existsByAllIds(Collection<K> ids) {
        return false;
    }
}
