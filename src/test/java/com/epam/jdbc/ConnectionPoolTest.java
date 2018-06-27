package com.epam.jdbc;

import com.epam.jdbc.cp.ConnectionPool;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FieldDefaults(level = PRIVATE, makeFinal = true)
class ConnectionPoolTest {

    String GET_STUDENTS_SQL = "select id, name, group_id from students";

    String ID_FIELD = "id";
    String NAME_FIELD = "name";
    String GROUP_ID_FIELD = "group_id";

    @Test
    @SneakyThrows
    @DisplayName("ConnectionPool works correctly")
    void testGet() {
        @Cleanup val connection = new ConnectionPool().get();
        @Cleanup val statement = connection.createStatement();
        @Cleanup val resultSet = statement.executeQuery(GET_STUDENTS_SQL);
        assertTrue(resultSet.next());
        assertThat(resultSet.getInt(ID_FIELD), is(1));
        assertThat(resultSet.getString(NAME_FIELD), is("Вася Пупкин"));
        assertThat(resultSet.getInt(GROUP_ID_FIELD), is(123456));
    }
}