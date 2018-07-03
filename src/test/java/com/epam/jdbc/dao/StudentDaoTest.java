package com.epam.jdbc.dao;

import com.epam.fp.CheckedRunnable;
import com.epam.jdbc.cp.ConnectionPool;
import com.epam.jdbc.model.Student;
import lombok.Cleanup;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.*;

import java.util.stream.Stream;

import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.assertEquals;

@FieldDefaults(level = PRIVATE, makeFinal = true)
class StudentDaoTest {

    static ConnectionPool CONNECTION_POOL = ConnectionPool.fromProps("jdbc");
    static StudentDao STUDENT_DAO = CONNECTION_POOL::get;
    static long INITIAL_COUNT = STUDENT_DAO.count();

    @SuppressWarnings("SpellCheckingInspection")
    Student vasyaPupkin = STUDENT_DAO.save(new Student("Vasya Pupkin", 1));

    @AfterAll
    static void tearDown() {
        CONNECTION_POOL.close();
    }

    @Test
    @DisplayName("Save method works correctly")
    void testSave() {
        assertThat(vasyaPupkin.getId(), not(0L));
        STUDENT_DAO.delete(vasyaPupkin);
    }

    @Test
    @DisplayName("FindAll method works correctly")
    void testFindAll() {
        assertThat(STUDENT_DAO.mapAll(Stream::count), is(INITIAL_COUNT + 1));
        STUDENT_DAO.delete(vasyaPupkin);
    }

    @Test
    @DisplayName("Update method works correctly")
    void testUpdate() {
        Student student1 = STUDENT_DAO.findById(vasyaPupkin.getId()).orElseThrow(() -> {
            CheckedRunnable.narrow(Assertions::fail).unchecked();
            return null;
        });
        STUDENT_DAO.update(student1.setGroupId(100500));
        Student student2 = STUDENT_DAO.findById(vasyaPupkin.getId()).orElseThrow(() -> {
            CheckedRunnable.narrow(Assertions::fail).unchecked();
            return null;
        });
        assertEquals(student2, student1);
    }

    @Test
    @DisplayName("Delete method works correctly")
    void testDelete() {
        Student student = STUDENT_DAO.findById(vasyaPupkin.getId())
                .orElseThrow(() -> {
                    CheckedRunnable.narrow(Assertions::fail).unchecked();
                    return null;
                });

        assertThat(STUDENT_DAO.delete(student).count(), is(0L));
    }

    @Test
    @DisplayName("Clear method works correctly")
    void testClear() {
        assertThat(STUDENT_DAO.clear().count(), is(0L));
    }

    @AfterEach
    void tearDown2() {
        STUDENT_DAO.delete(vasyaPupkin);
    }
}