package com.epam.io;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class TestUtilsTest {

  @ParameterizedTest
  @ValueSource(ints = {1, 2, 3})
  @DisplayName("\"fromSystemOutPrint\" method works correctly")
  void testFromSystemOut(int x) {
    String s = "Мама мыла раму!\n" + x;
    assertThat(
      TestUtils.fromSystemOutPrint(
        () -> System.out.print(s)))
      .isEqualTo(s);
  }
}
