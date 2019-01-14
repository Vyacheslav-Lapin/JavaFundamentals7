package com.epam.jdbc;

import com.epam.fp.CheckedConsumer2;
import com.epam.fp.CheckedFunction1;
import com.epam.fp.Consumer2;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public interface JdbcHelper extends Supplier<Connection> {

  @SneakyThrows
  default void executeUpdate(String sql) {
    @Cleanup val connection = get();
    @Cleanup val statement = connection.createStatement();
    statement.executeUpdate(sql);
  }

  @SneakyThrows
  default void executeUpdate(String sql, Object... params) {
    @Cleanup val connection = get();
    @Cleanup val preparedStatement = connection.prepareStatement(sql);
    fill(preparedStatement, params)
      .executeUpdate();
  }

  @SneakyThrows
  default Long executeUpdateAndReturnGeneratedKey(String sql, Object... params) {
    @Cleanup val connection = get();
    @Cleanup val preparedStatement = connection.prepareStatement(sql, RETURN_GENERATED_KEYS);

    int affectedRows = fill(preparedStatement, params).executeUpdate();
    if (affectedRows == 0)
      throw new SQLException("Inserting entity failed, no rows affected.");

    @Cleanup ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
    if (generatedKeys.next())
      return generatedKeys.getLong(1);
    else
      throw new SQLException("Inserting entity failed, no ID_FIELD obtained.");
  }

  @SneakyThrows
  default <T> Stream<T> executeQuery(String sql, CheckedFunction1<ResultSet, T> rowMapper) {
    return JdbcUtils.toStream(get().createStatement().executeQuery(sql), rowMapper);
  }

  @SneakyThrows
  default <T> Optional<T> executeQueryForSingleResult(String sql,
                                                      CheckedFunction1<ResultSet, T> rowMapper) {
    @Cleanup val connection = get();
    @Cleanup val statement = connection.createStatement();
    @Cleanup val resultSet = statement.executeQuery(sql);
    return resultSet.next() ? Optional.of(rowMapper.apply(resultSet)) : Optional.empty();
  }

  @SneakyThrows
  default <T> Optional<T> executeQueryForSingleResult(String sql,
                                                      CheckedFunction1<ResultSet, T> rowMapper,
                                                      Object... params) {
    @Cleanup val connection = get();
    @Cleanup val preparedStatement = connection.prepareStatement(sql);
    @Cleanup val resultSet = fill(preparedStatement, params).executeQuery();
    return resultSet.next() ? Optional.of(rowMapper.apply(resultSet)) : Optional.empty();
  }

  private PreparedStatement fill(PreparedStatement preparedStatement, Object... params) {
    Consumer2<Integer, Object> setParam = CheckedConsumer2.<Integer, Object>of(
      preparedStatement::setObject
    ).unchecked();

    IntStream.range(0, params.length)
      .forEach(value -> setParam.accept(value + 1, params[value]));

    return preparedStatement;
  }
}
