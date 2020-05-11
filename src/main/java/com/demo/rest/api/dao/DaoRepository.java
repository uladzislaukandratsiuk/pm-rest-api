package com.demo.rest.api.dao;

import java.util.List;
import java.util.Optional;

public interface DaoRepository<T, ID> {

    List<T> findAll();

    Optional<T> findById(ID id);

    void save(T entity);

    void deleteById(ID id);
}
