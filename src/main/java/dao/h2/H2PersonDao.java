package dao.h2;

import com.epam.fp.CheckedFunction1;
import com.epam.fp.CheckedFunction2;
import com.epam.jdbc.CrudJdbcDao;
import com.epam.jdbc.JdbcHelper;
import dao.PersonDao;
import model.Person;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.stream.Stream;

@FunctionalInterface
public interface H2PersonDao extends PersonDao, JdbcHelper {

  //language=H2
  String CREATE_SQL = "create table Person (id identity, first_name varchar(255) not null, last_name varchar(255), permission boolean default false, dob date, email varchar(255) not null, password varchar(255) not null, address varchar(255), telephone varchar(20))";//language=H2
  String INSERT_SQL = "insert into Person (first_name, last_name, permission, dob, email, password, address, telephone) values (?, ?, ?, ?, ?, ?, ?, ?)";//language=H2
  String UPDATE_SQL = "update Person set first_name = ?, last_name = ?, permission = ?, dob = ?, email = ?, password = ?, address = ?, telephone = ? where id = ?";//language=H2
  String SELECT_SQL = "select first_name, last_name, permission, dob, email, password, address, telephone from Person where id = ?";//language=H2
  String SELECT_ALL_SQL = "select id, first_name, last_name, permission, dob, email, password, address, telephone from Person";//language=H2
  String DELETE_SQL = "delete from Person where id = ?";//language=H2
  String DELETE_ALL_SQL = "-- noinspection SqlWithoutWhere\ndelete from Person";//language=H2
  String FIND_BY_LOGIN_AND_PASSWORD_HASH_SQL = "select id, first_name, last_name, permission, dob, address, telephone from Person where email = ? and password = ?";//language=H2
  String COUNT_SQL = "select count(id) from Person";

  String ID_FIELD = "id";
  String FIRST_NAME_FIELD = "first_name";
  String LAST_NAME_FIELD = "last_name";
  String PERMISSION_FIELD = "permission";
  String DATE_OF_BIRTH_FIELD = "dob";
  String EMAIL_FIELD = "email";
  String PASSWORD_FIELD = "password";
  String ADDRESS_FIELD = "address";
  String TELEPHONE_FIELD = "telephone";

  CheckedFunction2<ResultSet, Long, Person> ROW_MAPPER = CheckedFunction2.of(
    (resultSet, id) -> new Person(id,
      resultSet.getString(FIRST_NAME_FIELD),
      resultSet.getString(LAST_NAME_FIELD),
      resultSet.getBoolean(PERMISSION_FIELD),
      resultSet.getDate(DATE_OF_BIRTH_FIELD).toLocalDate(),
      resultSet.getString(EMAIL_FIELD),
      resultSet.getString(PASSWORD_FIELD),
      resultSet.getString(ADDRESS_FIELD),
      resultSet.getString(TELEPHONE_FIELD)));

  CheckedFunction1<ResultSet, Person> ID_ROW_MAPPER = CheckedFunction1.of(resultSet ->
    ROW_MAPPER.apply(resultSet, resultSet.getLong(ID_FIELD)));

  CheckedFunction2<PreparedStatement, Person, PreparedStatement> TO_ROW_MAPPER =
    CheckedFunction2.of((preparedStatement, person) -> {
      preparedStatement.setString(1, person.getFirstName());
      preparedStatement.setString(2, person.getLastName());
      preparedStatement.setBoolean(3, person.isPermission());
      preparedStatement.setDate(4, Date.valueOf(person.getDob()));
      preparedStatement.setString(5, person.getEmail());
      preparedStatement.setString(6, person.getPassword());
      preparedStatement.setString(7, person.getAddress());
      preparedStatement.setString(8, person.getTelephone());
      return preparedStatement;
    });

  @Override
  default @NotNull Stream<Person> all() {
    return executeQuery(SELECT_ALL_SQL, ID_ROW_MAPPER);
  }

  @Override
  default @NotNull Person update(@NotNull Person person) {
    executeUpdate(UPDATE_SQL,
      person.getFirstName(),
      person.getLastName(),
      person.isPermission(),
      Date.valueOf(person.getDob()),
      person.getEmail(),
      person.getPassword(),
      person.getAddress(),
      person.getTelephone());
    return person;
  }

  @Override
  default @NotNull Person insert(@NotNull Person person) {
    return person.setId(
      executeUpdateAndReturnGeneratedKey(INSERT_SQL,
        person.getFirstName(),
        person.getLastName(),
        person.isPermission(),
        Date.valueOf(person.getDob()),
        person.getEmail(),
        person.getPassword(),
        person.getAddress(),
        person.getTelephone()));
  }

  @Override
  default @NotNull Optional<Person> get(@NotNull Long id) {
    return executeQueryForSingleResult(SELECT_SQL, ROW_MAPPER.apply2(id), id);
  }

  @Override
  default @NotNull CrudJdbcDao<Person> deleteById(@NotNull Long id) {
    executeUpdate(DELETE_SQL, id);
    return this;
  }

  @Override
  default @NotNull Optional<Person> findByLoginAndPasswordHash(String login, String hash) {
    return executeQueryForSingleResult(FIND_BY_LOGIN_AND_PASSWORD_HASH_SQL, resultSet -> new Person(
      resultSet.getLong(ID_FIELD),
      resultSet.getString(FIRST_NAME_FIELD),
      resultSet.getString(LAST_NAME_FIELD),
      resultSet.getBoolean(PERMISSION_FIELD),
      resultSet.getDate(DATE_OF_BIRTH_FIELD).toLocalDate(),
      login,
      hash,
      resultSet.getString(ADDRESS_FIELD),
      resultSet.getString(TELEPHONE_FIELD)),
      login, hash);
  }

  @Override
  default @NotNull CrudJdbcDao<Person> clear() {
    executeUpdate(DELETE_ALL_SQL);
    return this;
  }

  @Override
  default long count() {
    //noinspection OptionalGetWithoutIsPresent
    return executeQueryForSingleResult(COUNT_SQL, resultSet -> resultSet.getLong(1)).get();
  }
}
