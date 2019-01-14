package dao;

import com.epam.fp.CheckedRunnable;
import com.epam.jdbc.cp.ConnectionPool;
import dao.h2.H2PersonDao;
import lombok.experimental.FieldDefaults;
import lombok.val;
import model.Person;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.time.Month;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@FieldDefaults(level = PRIVATE, makeFinal = true)
class PersonDaoTest {

  static ConnectionPool CONNECTION_POOL = ConnectionPool.fromProps("jdbc");
  static H2PersonDao PERSON_DAO = CONNECTION_POOL::get;
  static long INITIAL_COUNT = PERSON_DAO.count();

  @SuppressWarnings("SpellCheckingInspection")
  Person vasyaPupkin = PERSON_DAO.save(
    new Person(
      "Vasya",
      "Pupkin",
      true,
      LocalDate.of(1990, Month.AUGUST, 7),
      "Vasy@Pupk.in",
      "qweerty",
      "Gogolya street, h.18a, f.188",
      "+7(909)222-33-22"));

  @AfterAll
  static void tearDown() {
    CONNECTION_POOL.close();
  }

  @Test
  @DisplayName("Save method works correctly")
  void testSave() {
    assertThat(vasyaPupkin.getId()).isNotEqualTo(0L);
    PERSON_DAO.delete(vasyaPupkin);
  }

  @Test
  @DisplayName("FindAll method works correctly")
  void testFindAll() {
    assertThat(PERSON_DAO.count()).isEqualTo(INITIAL_COUNT + 1);
    PERSON_DAO.delete(vasyaPupkin);
  }

  @Test
  @DisplayName("Update method works correctly")
  void testUpdate() {
    Person person1 = PERSON_DAO.get(vasyaPupkin.getId()).orElseThrow(() -> {
      CheckedRunnable.of(Assertions::fail).unchecked();
      return null;
    });
    PERSON_DAO.update(person1.setLastName("Sidoroff"));
    Person person2 = PERSON_DAO.get(vasyaPupkin.getId()).orElseThrow(() -> {
      CheckedRunnable.of(Assertions::fail).unchecked();
      return null;
    });
    assertEquals(person2, person1);
  }

  @Test
  @DisplayName("Delete method works correctly")
  void testDelete() {
    val count = PERSON_DAO.count();
    Person person = PERSON_DAO.get(vasyaPupkin.getId())
      .orElseThrow(() -> {
        CheckedRunnable.of(Assertions::fail).unchecked();
        return null;
      });

    assertThat(PERSON_DAO.delete(person).count()).isEqualTo(count - 1);
  }

  @Test
  @Disabled
  @DisplayName("Clear method works correctly")
  void testClear() {
    assertThat(PERSON_DAO.clear().count()).isEqualTo(0L);
  }

  @AfterEach
  void tearDown2() {
    PERSON_DAO.delete(vasyaPupkin);
  }
}
