package com.epam.jdbc.dao;

import com.epam.fp.CheckedRunnable;
import com.epam.jdbc.cp.JdbcDao;
import com.epam.jdbc.model.Student;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@FunctionalInterface
public interface StudentDao extends JdbcDao<Student>, Supplier<Connection> {

    String INSERT_SQL = "insert into students (name, group_id) values (?, ?)";
    String GET_ALL_SQL = "select id, name, group_id from students";
    String UPDATE_SQL = "update students set name = ?, group_id = ? where id = ?;";
    String GET_SQL = "select name, group_id from students where id = ?";
    String DELETE_SQL = "delete from students where id = ?";
    String DELETE_ALL_SQL = "delete from students";
    String COUNT_SQL = "select count(id) from students";

    String ID_FIELD = "id";
    String NAME_FIELD = "name";
    String GROUP_ID_FIELD = "group_id";

    @Override
    @SneakyThrows
    default <U extends Student> U save(U student) {
        @Cleanup Connection connection = get();
        @Cleanup val ps = connection.prepareStatement(INSERT_SQL, RETURN_GENERATED_KEYS);
        ps.setString(1, student.getName());
        ps.setInt(2, student.getGroupId());
        ps.executeUpdate();
        @Cleanup ResultSet rs = ps.getGeneratedKeys();
        if (rs.next())
            //noinspection unchecked
            return (U) student.setId(rs.getInt(1));
        throw new RuntimeException("");
    }

    /**
     * @return Closeable Stream (!!!) of Students
     */
    @Override
    @SneakyThrows
    default <U extends Student> Stream<U> findAll() {
        val connection = get();
        val statement = connection.createStatement();
        val resultSet = statement.executeQuery(GET_ALL_SQL);
        Iterable<U> us = () -> new Iterator<>() {
            @Override
            @SneakyThrows
            public boolean hasNext() {
                return resultSet.next();
            }

            @Override
            @SneakyThrows
            public U next() {
                //noinspection unchecked
                return (U) new Student(resultSet.getLong(ID_FIELD),
                        resultSet.getString(NAME_FIELD),
                        resultSet.getInt(GROUP_ID_FIELD));
            }
        };
        return StreamSupport.stream(us.spliterator(), false)
                .onClose(CheckedRunnable.narrow(resultSet::close).unchecked())
                .onClose(CheckedRunnable.narrow(statement::close).unchecked())
                .onClose(CheckedRunnable.narrow(connection::close).unchecked());
    }

    @Override
    @SneakyThrows
    default <U extends Student> U update(U student) {
        @Cleanup val connection = get();
        @Cleanup val preparedStatement = connection.prepareStatement(UPDATE_SQL);
        preparedStatement.setString(1, student.getName());
        preparedStatement.setInt(2, student.getGroupId());
        preparedStatement.setLong(3, student.getId());
        preparedStatement.executeUpdate();
        return student;
    }

    @Override
    @SneakyThrows
    default <U extends Student> JdbcDao<Student> delete(U student) {
        @Cleanup val connection = get();
        @Cleanup val preparedStatement = connection.prepareStatement(DELETE_SQL);
        preparedStatement.setLong(1, student.getId());
        preparedStatement.executeLargeUpdate();
        return this;
    }

    @Override
    @SneakyThrows
    default <U extends Student> Optional<U> findById(long id) {
        @Cleanup val connection = get();
        @Cleanup val preparedStatement = connection.prepareStatement(GET_SQL);
        preparedStatement.setLong(1, id);
        @Cleanup val resultSet = preparedStatement.executeQuery();
        //noinspection unchecked
        return resultSet.next() ? Optional.of((U) new Student(id,
                resultSet.getString(NAME_FIELD),
                resultSet.getInt(GROUP_ID_FIELD))) : Optional.empty();
    }

    @Override
    @SneakyThrows
    default JdbcDao<Student> clear() {
        @Cleanup val connection = get();
        @Cleanup val preparedStatement = connection.prepareStatement(DELETE_ALL_SQL);
        preparedStatement.executeLargeUpdate();
        return this;
    }

    @Override
    @SneakyThrows
    default long count() {
        @Cleanup val connection = get();
        @Cleanup val statement = connection.createStatement();
        @Cleanup val resultSet = statement.executeQuery(COUNT_SQL);
        return resultSet.next() ? resultSet.getLong(1): 0L;
    }
}
