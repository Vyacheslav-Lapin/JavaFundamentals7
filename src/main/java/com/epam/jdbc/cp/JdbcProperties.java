package com.epam.jdbc.cp;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.function.Supplier;

import static lombok.AccessLevel.NONE;

@Value
@Getter(NONE)
public class JdbcProperties implements Supplier<Connection> {
    String url;
    String user;
    String password;

    @Getter
    int poolSize;

    @Getter
    String sqlFolder;

    @Override
    @SneakyThrows
    public Connection get() {
        return DriverManager.getConnection(url, user, password);
    }
}
