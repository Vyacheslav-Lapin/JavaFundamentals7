package com.epam.jdbc.demo.demo2;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class H2StudentDaoTest {

  @Test
  @SneakyThrows
  @DisplayName("\"GetAll\" method works correctly")
  void testGetAll() {
    // given
    val studentDao = new InMemoryH2StudentDao();
    val initialCount = studentDao.count();

    // when
    val student1 = studentDao.save(new Student("Вася Пупкин", 123_456));
    val student2 = studentDao.save(new Student("Федя Васичкин", 234_567));
    val student3 = studentDao.save(new Student("Иван Иванов", 345_678));

    // then
    Long student1Id = student1.getId();
    assertThat(student2.getId()).isEqualTo(student1Id + 1);
    assertThat(student3.getId()).isEqualTo(student1Id + 2);

    assertThat(studentDao.count()).isEqualTo(initialCount + 3);

    assertThat(studentDao.all())
      .containsSequence(student1, student2, student3);

    assertThat(studentDao.get(1))
      .isPresent()
      .contains(student1);

    assertThat(studentDao.deleteById(2).get(2)).isEmpty();

    studentDao.clear();
//    assertThat(studentDao.all()).hasSize(0);
  }

}
