package com.javamentor.qa.platform.dao.impl.model;


import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class ReadOnlyDaoImpl<E, K> {

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
        return null;
    }

    public boolean existsByAllIds(Collection<K> ids) {
        return false;
    }
}
