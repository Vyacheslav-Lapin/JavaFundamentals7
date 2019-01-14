package dao.h2;

import com.epam.fp.CheckedFunction1;
import com.epam.fp.CheckedFunction2;
import com.epam.jdbc.CrudJdbcDao;
import com.epam.jdbc.JdbcHelper;
import dao.GunDao;
import model.Gun;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.Optional;
import java.util.stream.Stream;

import static model.Gun.Fields.*;

@FunctionalInterface
public interface H2GunDao extends GunDao, JdbcHelper {

  //language=H2
  String CREATE_TABLE_SQL = "create table if not exists Gun (id identity, name varchar(255), caliber double not null)";//language=H2
  String INSERT_SQL = "insert into Gun (name, caliber) values (?, ?)";//language=H2
  String UPDATE_SQL = "update Gun set name=?, caliber=? where id=?";//language=H2
  String SELECT_SQL = "select id, name, caliber from Gun where id=?";//language=H2
  String SELECT_ALL_SQL = "select id, name, caliber from Gun";//language=H2
  String DELETE_SQL = "delete from Gun where id=?";//language=H2
  String DELETE_ALL_SQL = "-- noinspection SqlWithoutWhere\ndelete from Gun";//language=H2
  String COUNT_SQL = "select count(*) from Gun";

  CheckedFunction2<ResultSet, Long, Gun> ROW_MAPPER = CheckedFunction2.of(
    (resultSet, id) -> new Gun(id,
      resultSet.getString(name), //model.Gun.Fields.name - see static import
      resultSet.getDouble(caliber))); //model.Gun.Fields.name - see static import

  CheckedFunction1<ResultSet, Gun> ID_ROW_MAPPER = CheckedFunction1.of(resultSet ->
    ROW_MAPPER.apply(resultSet, resultSet.getLong(id))); //model.Gun.Fields.id - see static import

  @Override
  default @NotNull Optional<Gun> get(@NotNull Long id) {
    return executeQueryForSingleResult(SELECT_SQL, ROW_MAPPER.apply2(id), id);
  }

  @Override
  default @NotNull Stream<Gun> all() {
    return executeQuery(SELECT_ALL_SQL, ID_ROW_MAPPER);
  }

  @Override
  default @NotNull Gun update(@NotNull Gun gun) {
    executeUpdate(UPDATE_SQL, gun.getName(), gun.getCaliber(), gun.getId());
    return gun;
  }

  @Override
  default @NotNull Gun insert(@NotNull Gun gun) {
    return gun.setId(
      executeUpdateAndReturnGeneratedKey(
        INSERT_SQL,
        gun.getName(),
        gun.getCaliber()));
  }

  @Override
  default @NotNull CrudJdbcDao<Gun> deleteById(@NotNull Long id) {
    executeUpdate(DELETE_SQL, id);
    return this;
  }

  @Override
  default @NotNull CrudJdbcDao<Gun> clear() {
    executeUpdate(DELETE_ALL_SQL);
    return this;
  }

  @Override
  default long count() {
    //noinspection OptionalGetWithoutIsPresent
    return executeQueryForSingleResult(COUNT_SQL, resultSet -> resultSet.getLong(1)).get();
  }
}
