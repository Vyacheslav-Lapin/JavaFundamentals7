package dao.h2;

import com.epam.fp.CheckedFunction1;
import com.epam.fp.CheckedFunction2;
import com.epam.jdbc.CrudJdbcDao;
import com.epam.jdbc.JdbcHelper;
import dao.GunInstanceDao;
import model.GunInstance;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.util.Optional;
import java.util.stream.Stream;

@FunctionalInterface
public interface H2GunInstanceDao extends GunInstanceDao, JdbcHelper {

  //language=H2
  String CREATE_SQL = "create table if not exists GunInstance (id identity, modelId int not null, foreign key (modelId) references Gun (id))";//language=H2
  String INSERT_SQL = "insert into GunInstance (modelId) values (?)";//language=H2
  String UPDATE_SQL = "update GunInstance set modelId=? where id=?";//language=H2
  String SELECT_SQL = "select i.id, i.modelId, g.name, g.caliber from GunInstance i, Gun g where i.id=? AND i.modelId = g.id";//language=H2
  String SELECT_ALL_SQL = "select i.id, i.modelId, g.name, g.caliber from GunInstance i, Gun g where i.modelId = g.id";//language=H2
  String DELETE_SQL = "delete from GunInstance where id=?";//language=H2
  String DELETE_ALL_SQL = "-- noinspection SqlWithoutWhere\ndelete from GunInstance";//language=H2
  String COUNT_SQL = "select count(*) from GunInstance";

  String ID_FIELD = "id";
  String MODEL_ID = "modelId";

  CheckedFunction2<ResultSet, Long, GunInstance> ROW_MAPPER =
    CheckedFunction2.of(
      (resultSet, id) -> new GunInstance(id,
        H2GunDao.ROW_MAPPER.apply(resultSet, resultSet.getLong(MODEL_ID)))); // model.GunInstance.Fields.modelId

  CheckedFunction1<ResultSet, GunInstance> ID_ROW_MAPPER =
    CheckedFunction1.of(resultSet ->
      ROW_MAPPER.apply(resultSet, resultSet.getLong(ID_FIELD))); // model.GunInstance.Fields.modelId)

  @Override
  default @NotNull Stream<GunInstance> all() {
    return executeQuery(SELECT_ALL_SQL, ID_ROW_MAPPER);
  }

  @Override
  default @NotNull GunInstance update(@NotNull GunInstance gunInstance) {
    executeUpdate(UPDATE_SQL, gunInstance.getGun().getId(), gunInstance.getId());
    return gunInstance;
  }

  @Override
  default @NotNull GunInstance insert(@NotNull GunInstance gunInstance) {
    return gunInstance.setId(
      executeUpdateAndReturnGeneratedKey(
        INSERT_SQL,
        gunInstance.getGun().getId()));
  }

  @Override
  default @NotNull CrudJdbcDao<GunInstance> deleteById(@NotNull Long id) {
    executeUpdate(DELETE_SQL, id);
    return this;
  }

  @Override
  default @NotNull Optional<GunInstance> get(@NotNull Long id) {
    return executeQueryForSingleResult(SELECT_SQL, ROW_MAPPER.apply2(id), id);
  }

  @Override
  default @NotNull CrudJdbcDao<GunInstance> clear() {
    executeUpdate(DELETE_ALL_SQL);
    return this;
  }

  @Override
  default long count() {
    //noinspection OptionalGetWithoutIsPresent
    return executeQueryForSingleResult(COUNT_SQL, resultSet -> resultSet.getLong(1)).get();
  }
}
