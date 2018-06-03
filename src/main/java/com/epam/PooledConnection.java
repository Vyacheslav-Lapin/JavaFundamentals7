package com.epam;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class PooledConnection implements Connection {

    @Delegate(excludes = {A.class, AutoCloseable.class})
    Connection connection;

    private interface A{
        PreparedStatement prepareStatement(String s);
    }

    @Override
    public void close() throws SQLException {
//        connectionPool.returnOf(this);
//        connection.close();
    }

    public PreparedStatement prepareStatement(String s) {
        return null;
    }
}
