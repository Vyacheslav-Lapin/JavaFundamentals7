package com.epam.jdbc.demo.demo2;

import com.epam.jdbc.CrudJdbcDao;
import io.vavr.CheckedFunction0;
import lombok.SneakyThrows;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;

import java.sql.DriverManager;

import static lombok.AccessLevel.PRIVATE;

@SuppressWarnings("WeakerAccess")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public final class InMemoryH2StudentDao implements CrudJdbcDao<Student> {

  static String DB_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";

  @Delegate
  H2StudentDao h2StudentDao = CheckedFunction0.of(() -> DriverManager.getConnection(DB_URL)).unchecked()::apply;

  @SneakyThrows
  public InMemoryH2StudentDao() {
    h2StudentDao.executeUpdate(H2StudentDao.CREATE_TABLE_SQL);
  }
}
