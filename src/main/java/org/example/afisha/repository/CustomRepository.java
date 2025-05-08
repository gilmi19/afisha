package org.example.afisha.repository;

import java.util.List;
import java.util.Optional;

public interface CustomRepository<T> {
    void save(T t);

    Optional<T> findById(Long id);

    List<T> findAll();
}
