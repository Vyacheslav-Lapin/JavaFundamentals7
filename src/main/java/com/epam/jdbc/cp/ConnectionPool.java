package com.epam.jdbc.cp;

import com.epam.io.PropsBinder;
import io.vavr.CheckedFunction1;
import io.vavr.Function1;
import io.vavr.Function2;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.val;

import java.io.Closeable;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
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

    public ConnectionPool() {
        val jdbcProperties = PropsBinder.from("jdbc", JdbcProperties.class);

        int poolSize = jdbcProperties.getPoolSize();

        Function<Connection, PooledConnection> pooledConnectionCreator =
                Function2.of(PooledConnection::new)
                        .apply(this::closer);

        freeConnections = IntStream.iterate(0, operand -> operand + 1)
                .limit(poolSize)
                .mapToObj(__ -> jdbcProperties.get())
                .map(pooledConnectionCreator)
                .collect(Collectors.toCollection(() -> new ArrayBlockingQueue<>(poolSize)));

        Function1<String, Optional<String>> getFileAsString =
                Function2.narrow(ConnectionPool::getFileAsString)
                        .apply(jdbcProperties.getSqlFolder());

        execute(IntStream.iterate(1, operand -> operand + 1)
                .mapToObj(String::valueOf)
                .map(getFileAsString)
                .takeWhile(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.joining()));
    }

    @SneakyThrows
    private static Optional<String> getFileAsString(String initScriptsPath, String name) {
        val path = String.format("/%s/%s.sql", initScriptsPath, name);

        Function<Path, String> read =
                CheckedFunction1.<Path, String>narrow(filePath -> {
                    @Cleanup Stream<String> lines = Files.lines(filePath);
                    return lines.collect(Collectors.joining());
                }).unchecked();

        return Optional.ofNullable(ConnectionPool.class.getResource(path))
                .map(CheckedFunction1.narrow(URL::toURI).unchecked())
                .map(Paths::get)
                .map(read);
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
