package com.epam.jdbc.dao;

import com.epam.fp.CheckedRunnable;
import com.epam.jdbc.cp.ConnectionPool;
import com.epam.jdbc.model.Student;
import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.experimental.FieldDefaults;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.*;

import java.util.Optional;
import java.util.stream.Stream;

import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.*;

@FieldDefaults(level = PRIVATE, makeFinal = true)
class StudentDaoTest {

    static ConnectionPool connectionPool = ConnectionPool.fromProps("jdbc");
    static StudentDao studentDao = connectionPool::get;
    Student vasyaPupkin = studentDao.save(new Student("Vasya Pupkin", 1));

    @Test
    @DisplayName("Save method works correctly")
    void testSave() {
        assertThat(vasyaPupkin.getId(), not(0L));
        studentDao.delete(vasyaPupkin);
    }

    @Test
    @DisplayName("FindAll method works correctly")
    void testFindAll() {
        @Cleanup Stream<Student> all = studentDao.findAll();
        assertThat(all.count(), is(2L));
        studentDao.delete(vasyaPupkin);
    }

    @Test
    @DisplayName("Update method works correctly")
    void testUpdate() {
        Student student1 = studentDao.findById(vasyaPupkin.getId()).orElseThrow(() -> {
                CheckedRunnable.narrow(Assertions::fail).unchecked();
                return null;});
        studentDao.update(student1.setGroupId(100500));
        Student student2 = studentDao.findById(vasyaPupkin.getId()).orElseThrow(() -> {
            CheckedRunnable.narrow(Assertions::fail).unchecked();
            return null;});
        assertEquals(student2, student1);
    }

    @Test
    @DisplayName("Delete method works correctly")
    void testDelete() {
        Student student = studentDao.findById(vasyaPupkin.getId()).orElseThrow(() -> {
            CheckedRunnable.narrow(Assertions::fail).unchecked();
            return null;
        });
        assertThat(studentDao.delete(student).count(), is(0L));
    }

    @Test
    @DisplayName("Clear method works correctly")
    void testClear() {
        assertThat(studentDao.clear().count(), is(0L));
    }

    @AfterEach
    void tearDown2() {
        studentDao.delete(vasyaPupkin);
    }

    @AfterAll
    static void tearDown() {
        connectionPool.close();
    }
}