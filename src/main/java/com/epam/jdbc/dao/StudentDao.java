package com.epam.jdbc.dao;

import com.epam.fp.CheckedRunnable;
import com.epam.fp.StreamUtils;
import com.epam.jdbc.cp.JdbcDao;
import com.epam.jdbc.model.Student;
import io.vavr.CheckedFunction1;
import io.vavr.CheckedFunction2;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

@FunctionalInterface
public interface StudentDao extends JdbcDao<Student>, Supplier<Connection> {

    String INSERT_SQL = "insert into students (name, group_id) values (?, ?)";
    String GET_ALL_SQL = "select id, name, group_id from students";
    String UPDATE_SQL = "update students set name = ?, group_id = ? where id = ?";
    String GET_SQL = "select name, group_id from students where id = ?";
    String DELETE_SQL = "delete from students where id = ?";
    String DELETE_ALL_SQL = "delete from students";
    String COUNT_SQL = "select count(id) from students";

    String ID_FIELD = "id";
    String NAME_FIELD = "name";
    String GROUP_ID_FIELD = "group_id";

    BiFunction<ResultSet, Long, Student> ROW_MAPPER = CheckedFunction2.<ResultSet, Long, Student>of(
            (resultSet, id) -> new Student(id,
                    resultSet.getString(NAME_FIELD),
                    resultSet.getInt(GROUP_ID_FIELD)))
            .unchecked();

    Function<ResultSet, Student> ID_ROW_MAPPER = CheckedFunction1.<ResultSet, Student>of(resultSet ->
            ROW_MAPPER.apply(resultSet, resultSet.getLong(ID_FIELD)))
            .unchecked();

    @NotNull
    @Override
    @SneakyThrows
    default Student save(@NotNull Student student) {
        @Cleanup Connection connection = get();
        @Cleanup val ps = connection.prepareStatement(INSERT_SQL, RETURN_GENERATED_KEYS);
        ps.setString(1, student.getName());
        ps.setInt(2, student.getGroupId());
        ps.executeUpdate();
        @Cleanup ResultSet rs = ps.getGeneratedKeys();
        if (!rs.next())
            throw new RuntimeException("Не был сгенерирован ключ!");

        //noinspection unchecked
        return student.setId(rs.getInt(1));
    }

    /**
     * @return Closeable Stream (!!!) of Students
     */
    @NotNull
    @Override
    @SneakyThrows
    default Stream<Student> findAll() {
        val connection = get();
        val statement = connection.createStatement();
        val resultSet = statement.executeQuery(GET_ALL_SQL);
        //noinspection unchecked
        return StreamUtils.toStream(resultSet, ID_ROW_MAPPER)
                .onClose(CheckedRunnable.of(resultSet::close).unchecked())
                .onClose(CheckedRunnable.of(statement::close).unchecked())
                .onClose(CheckedRunnable.of(connection::close).unchecked());
    }

    @NotNull
    @Override
    @SneakyThrows
    default Student update(@NotNull Student student) {
        @Cleanup val connection = get();
        @Cleanup val preparedStatement = connection.prepareStatement(UPDATE_SQL);
        preparedStatement.setString(1, student.getName());
        preparedStatement.setInt(2, student.getGroupId());
        preparedStatement.setLong(3, student.getId());
        preparedStatement.executeUpdate();
        return student;
    }

    @NotNull
    @Override
    @SneakyThrows
    default JdbcDao<Student> delete(@NotNull Student student) {
        @Cleanup val connection = get();
        @Cleanup val preparedStatement = connection.prepareStatement(DELETE_SQL);
        preparedStatement.setLong(1, student.getId());
        preparedStatement.executeLargeUpdate();
        return this;
    }

    @NotNull
    @Override
    @SneakyThrows
    default Optional<Student> findById(long id) {
        @Cleanup val connection = get();
        @Cleanup val preparedStatement = connection.prepareStatement(GET_SQL);
        preparedStatement.setLong(1, id);
        @Cleanup val resultSet = preparedStatement.executeQuery();
        //noinspection unchecked
        return Optional.ofNullable(resultSet.next() ?
                ROW_MAPPER.apply(resultSet, id) :
                null);
    }

    @NotNull
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
        return resultSet.next() ? resultSet.getLong(1) : 0L;
    }
}
