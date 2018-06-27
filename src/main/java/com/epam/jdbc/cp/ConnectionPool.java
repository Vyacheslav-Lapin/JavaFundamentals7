package com.epam.jdbc.cp;

import com.epam.io.PropsBinder;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.val;

import java.sql.Connection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class ConnectionPool implements Supplier<Connection> {

    final BlockingQueue<PooledConnection> freeConnections;

    public ConnectionPool() {
        val jdbcProperties = PropsBinder.from("jdbc", JdbcProperties.class);

        int poolSize = jdbcProperties.getPoolSize();

        freeConnections = IntStream.iterate(0, operand -> operand + 1)
                .limit(poolSize)
                .mapToObj(__ -> jdbcProperties.get())
                .map(PooledConnection::new)
                .collect(Collectors.toCollection(() -> new ArrayBlockingQueue<>(poolSize)));
    }

    @Override
    @SneakyThrows
    public Connection get() {
//        return DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
        return freeConnections.take();
    }
}
