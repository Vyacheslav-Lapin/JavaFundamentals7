package com.epam.jdbc;

import com.epam.fp.CheckedConsumer;
import com.epam.fp.CheckedFunction1;
import com.epam.fp.StreamUtils;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public interface CrudJdbcDao<T extends Identifiable<T>> {

  @NotNull T insert(@NotNull T entity);

  @NotNull T update(@NotNull T entity);

  /**
   * @apiNote please, use the wrapper methods {@link #mapAll(CheckedFunction1)} or
   * {@link #withAll(CheckedConsumer)}, instead of this one, because it`s return
   * result MUST be closed, but you may forget about it!
   */
  @NotNull Stream<T> all();

  @NotNull Optional<T> get(@NotNull Long id);

  @NotNull CrudJdbcDao<T> deleteById(@NotNull Long id);

  default @NotNull CrudJdbcDao<T> delete(@NotNull T entity) {
    return deleteById(entity.getId());
  }

  @NotNull CrudJdbcDao<T> clear();

  long count();

  default @NotNull Optional<T> get(int id) {
    return get((long) id);
  }

  default Stream<T> allById(@NotNull LongStream ids) {
    return ids
      .filter(this::existsById)
      .mapToObj(this::get)
      .map(Optional::get);
  }

  default CrudJdbcDao<T> deleteById(int id) {
    deleteById((long) id);
    return this;
  }

  default T save(@NotNull T entity) {
    return entity.getId() == null ? insert(entity) : update(entity);
  }

  /**
   * Lazy
   */
  default Stream<T> saveAll(@NotNull Stream<T> entities) {
    return entities.map(this::save);
  }

  @SneakyThrows
  default @NotNull Collection<T> getAll() {
    @Cleanup Stream<T> all = all();
    return all.collect(Collectors.toList());
  }

  default <R> @NotNull R mapAll(@NotNull CheckedFunction1<Stream<T>, R> mapper) {
    return StreamUtils.mapStream(all(), mapper);
  }

  default @NotNull CrudJdbcDao<T> withAll(@NotNull CheckedConsumer<Stream<T>> mapper) {
    StreamUtils.withStream(all(), mapper);
    return this;
  }

  // Dirty
  default boolean existsById(long id) {
    return get(id).isPresent();
  }

  // Dirty
  default boolean existsById(int id) {
    return get(id).isPresent();
  }
}
