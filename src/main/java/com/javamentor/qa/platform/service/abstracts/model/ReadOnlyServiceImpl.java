package com.javamentor.qa.platform.service.abstracts.model;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class ReadOnlyServiceImpl<E, K> {

    public abstract List<E> getAll();

    public abstract boolean existsById(K id);

    public abstract Optional<E> getById(K id);

    public abstract List<E> getAllByIds(Iterable<K> ids);

    public abstract boolean existsByAllIds(Collection<K> ids);
}
