package com.epam.jdbc.cp;

import lombok.Cleanup;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Stream;

public interface JdbcDao<T extends Identifiable<T>> {

    @NotNull
    <U extends T> U save(@NotNull U t);

    @NotNull
    default <U extends T> Optional<U> findById(long id) {
        //noinspection unchecked
        @Cleanup Stream<T> all = findAll();
        return (Optional<U>) all.filter(t -> t.getId() == id)
                .findAny();
    }

    @NotNull
    <U extends T> Stream<U> findAll();

    @NotNull
    <U extends T> U update(@NotNull U t);

    @NotNull
    <U extends T> JdbcDao<T> delete(@NotNull U u);

    @NotNull
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
        return findById(id).isPresent();
    }
}
