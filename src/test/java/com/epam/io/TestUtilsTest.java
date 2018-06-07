package com.epam.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static com.epam.io.TestUtils.fromSystemOut;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class TestUtilsTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    @DisplayName("fromSystemOut method works correctly")
    void testFromSystemOut(int x) {
        String s = "Мама мыла раму!\n" + x;
        assertThat(fromSystemOut(() -> System.out.print(s)), is(s));
    }
}