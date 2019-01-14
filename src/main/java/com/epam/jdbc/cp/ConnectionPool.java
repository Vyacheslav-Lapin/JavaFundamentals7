package com.epam.jdbc.cp;

import com.epam.fp.CheckedFunction3;
import com.epam.io.demo.properties.PropsBinder;
import io.vavr.CheckedFunction1;
import io.vavr.Function0;
import io.vavr.Function2;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Optional;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class ConnectionPool implements Supplier<Connection>, Closeable {

  final BlockingQueue<PooledConnection> freeConnections;
  volatile boolean isOpened = true;

  @SuppressWarnings("unused")
  public ConnectionPool(String url, String user, String password, int poolSize, String sqlFolder) {
    this(
      CheckedFunction3
        .<String, String, String, Connection>of(DriverManager::getConnection)
        .supply(url, user, password),
      poolSize,
      sqlFolder);
  }

  private ConnectionPool(@NotNull Function0<Connection> connectionCreator, int poolSize, String sqlFolder) {

    Supplier<PooledConnection> pooledConnectionCreator = connectionCreator
      .andThen(Function2.of(PooledConnection::new)
        .apply(this::closer));

    freeConnections = Stream.generate(pooledConnectionCreator)
      .limit(poolSize)
      .collect(Collectors.toCollection(() -> new ArrayBlockingQueue<>(poolSize)));

    Function<String, Optional<String>> getFileAsString =
      Function2.narrow(ConnectionPool::getSqlFileAsString)
        .apply(sqlFolder);

    execute(IntStream.iterate(1, operand -> operand + 1)
      .mapToObj(String::valueOf)
      .map(getFileAsString)
      .takeWhile(Optional::isPresent)
      .map(Optional::get)
      .collect(Collectors.joining()));
  }

  @NotNull
  public static ConnectionPool fromProps() {
    return fromProps("jdbc");
  }

  @NotNull
  public static ConnectionPool fromProps(String jdbc) {
    return PropsBinder.from(jdbc, ConnectionPool.class);
  }

  @SneakyThrows
  private static Optional<String> getSqlFileAsString(String initScriptsPath, String name) {
    val path = String.format("/%s/%s.sql", initScriptsPath, name);

    return Optional.ofNullable(ConnectionPool.class.getResource(path))
      .map(URL::getFile)
      .map(Paths::get)
      .map(CheckedFunction1.<Path, String>narrow(Files::readString)
        .recover(throwable -> null));
  }

  @SneakyThrows
  public void execute(String sql) {
    @Cleanup val connection = get();
    @Cleanup val statement = connection.createStatement();
    statement.executeUpdate(sql);
  }

  @SneakyThrows
  private void closer(PooledConnection pooledConnection) {
    if (isOpened) {
      if (!pooledConnection.getAutoCommit())
        pooledConnection.setAutoCommit(true);
      if (pooledConnection.isReadOnly())
        pooledConnection.setReadOnly(false);
      freeConnections.put(pooledConnection);
    } else
      pooledConnection.reallyClose();
  }

  @Override
  @SneakyThrows
  public Connection get() {
    if (isOpened)
      return freeConnections.take();
    else
      throw new IOException("Connection Pool is already closed!");
  }

  @Override
  public void close() {
    isOpened = false;
    freeConnections.forEach(PooledConnection::close);
  }
}
