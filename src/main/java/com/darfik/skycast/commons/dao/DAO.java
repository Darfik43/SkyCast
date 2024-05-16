package com.darfik.skycast.commons.dao;

import java.util.Optional;

public interface DAO<T> {

    void save(T t);
    Optional<T> find(String usernameOrId);

    void update(T t);
}
