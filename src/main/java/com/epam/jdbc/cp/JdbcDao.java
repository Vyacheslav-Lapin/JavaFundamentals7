package com.epam.jdbc.cp;

import lombok.Cleanup;

import java.util.Optional;
import java.util.stream.Stream;

public interface JdbcDao<T extends Identifiable<T>> {

    <U extends T> U save(U t);

    default <U extends T> Optional<U> findById(long id) {
        //noinspection unchecked
        @Cleanup Stream<T> all = findAll();
        return (Optional<U>) all.filter(t -> t.getId() == id)
                .findAny();
    }

    <U extends T> Stream<U> findAll();

    <U extends T> U update(U t);

    <U extends T> JdbcDao<T> delete(U u);

    default JdbcDao<T> clear() {
        @Cleanup Stream<T> all = findAll();
        all.forEach(this::delete);
        //noinspection unchecked
        return this;
    }

    default long count() {
        return findAll().count();
    }

    default boolean existsById(long id) {
        return findAll().anyMatch(entity -> entity.getId() == id);
    }
}
