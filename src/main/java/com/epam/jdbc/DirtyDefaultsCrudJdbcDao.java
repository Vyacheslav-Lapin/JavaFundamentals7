package com.epam.jdbc;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.stream.Stream;

public interface DirtyDefaultsCrudJdbcDao<T extends Identifiable<T>> extends CrudJdbcDao<T> {

  @Override
  default long count() {
    return mapAll(Stream::count);
  }

  @NotNull
  @Override
  default CrudJdbcDao<T>clear() {
    withAll(all -> all.forEach(this::delete));
    return this;
  }

  @Override
  @NotNull
  default Optional<T> get(@NotNull Long id) {
    return mapAll(all -> all
      .filter(t -> t.getId().equals(id))
      .findAny());
  }

  default boolean existsById(long id) {
    return get(id).isPresent();
  }

  default boolean existsById(int id) {
    return get(id).isPresent();
  }
}
