package dao;

import com.epam.jdbc.CrudJdbcDao;
import com.epam.jdbc.JdbcHelper;
import model.Person;

import java.util.Optional;

public interface PersonDao extends CrudJdbcDao<Person>, JdbcHelper {

  Optional<Person> findByLoginAndPasswordHash(String login, String hash);
}
