package com.epam.jdbc.demo.demo2;

import com.epam.fp.CheckedFunction1;
import com.epam.fp.CheckedFunction2;
import com.epam.jdbc.CrudJdbcDao;
import com.epam.jdbc.JdbcHelper;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.Optional;
import java.util.stream.Stream;

import static com.epam.jdbc.demo.demo2.Student.Fields.*;

@FunctionalInterface
public interface H2StudentDao extends CrudJdbcDao<Student>, JdbcHelper {

  //language=H2
  String CREATE_TABLE_SQL = "create table if not exists student (id identity, name varchar not null, groupId int)";//language=H2
  String INSERT_SQL = "insert into student (name, groupId) values (?, ?)";//language=H2
  String UPDATE_SQL = "update student set name=?, groupId=? where id=?";//language=H2
  String SELECT_ALL_SQL = "select id, name, groupId from student";//language=H2
  String SELECT_SQL = "select name, groupId from student where id=?";//language=H2
  String DELETE_ALL_SQL = "-- noinspection SqlWithoutWhere\ndelete from student";//language=H2
  String DELETE_SQL = "delete from student where id=?";//language=H2
  String COUNT_SQL = "select count(*) from student";

  CheckedFunction2<ResultSet, Long, Student> ROW_MAPPER = CheckedFunction2.of(
    (resultSet, id) -> new Student(id,
      resultSet.getString(name), // com.epam.jdbc.demo.demo2.Student.Fields.name
      resultSet.getInt(groupId))); // com.epam.jdbc.demo.demo2.Student.Fields.groupId

  CheckedFunction1<ResultSet, Student> ID_ROW_MAPPER = CheckedFunction1.of(resultSet ->
    ROW_MAPPER.apply(resultSet, resultSet.getLong(id))); // com.epam.jdbc.demo.demo2.Student.Fields.id

  @Override
  default @NotNull Student insert(@NotNull Student student) {
    return student.setId(
      executeUpdateAndReturnGeneratedKey(
        INSERT_SQL,
        student.getName(),
        student.getGroupId()));
  }

  @Override
  default @NotNull Student update(@NotNull Student student) {
    executeUpdate(UPDATE_SQL, student.getName(), student.getGroupId(), student.getId());
    return student;
  }

  @Override
  @SneakyThrows
  default @NotNull Stream<Student> all() {
    return executeQuery(SELECT_ALL_SQL, ID_ROW_MAPPER);
  }

  @Override
  default @NotNull Optional<Student> get(@NotNull Long id) {
    return executeQueryForSingleResult(SELECT_SQL, ROW_MAPPER.apply2(id), id);
  }

  @Override
  default @NotNull H2StudentDao deleteById(@NotNull Long id) {
    executeUpdate(DELETE_SQL, id);
    return this;
  }

  @Override
  default @NotNull CrudJdbcDao<Student> clear() {
    executeUpdate(DELETE_ALL_SQL);
    return this;
  }

  @Override
  @SneakyThrows
  default long count() {
    //noinspection OptionalGetWithoutIsPresent
    return executeQueryForSingleResult(COUNT_SQL, resultSet -> resultSet.getLong(1)).get();
  }
}
