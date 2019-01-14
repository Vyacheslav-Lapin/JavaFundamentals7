package com.epam.jdbc.demo.demo1;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import lombok.val;

import java.sql.DriverManager;

import static lombok.AccessLevel.PRIVATE;

@UtilityClass
@SuppressWarnings("WeakerAccess")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class SimpleConnectToDB {

  String DB_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

  //language=H2
  String CREATE_TABLE_SQL = "create table student (id identity, name varchar not null, group_id int)";//language=H2
  String INSERT_STUDENT_SQL = "insert into student (name, group_id) values ('Вася Пупкин', 123456)";//language=H2
  String GET_STUDENTS_SQL = "select id, name, group_id from student";

  @SneakyThrows
  public void main(String... __) {
    @Cleanup val connection = DriverManager.getConnection(DB_URL);
    @Cleanup val statement = connection.createStatement();
    statement.executeUpdate(CREATE_TABLE_SQL);
    statement.executeUpdate(INSERT_STUDENT_SQL);
    @Cleanup val resultSet = statement.executeQuery(GET_STUDENTS_SQL);
    while (resultSet.next())
      System.out.printf("%d %s %d%n",
        resultSet.getInt("id"),
        resultSet.getString("name"),
        resultSet.getInt("group_id"));
  }
}
