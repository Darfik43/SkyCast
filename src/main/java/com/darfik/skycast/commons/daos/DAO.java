package com.darfik.skycast.commons.daos;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    void save(T t);
    Optional<T> get(int id);

    List<T> getAll();

    void update(T t, String[] params);
}
