package com.epam.fp;// import com.epam.fp.StreamExample

import com.epam.fp.demo.StreamExample;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static lombok.AccessLevel.PRIVATE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@FieldDefaults(level = PRIVATE, makeFinal = true)
class StreamExampleTest {
  @Test
  @SneakyThrows
  @DisplayName("\"Test1\" method works correctly")
  void testTest1() {
    assertTrue(new StreamExample().test(Stream.of(true, true, true)));
    assertFalse(new StreamExample().test(Stream.of(true, false, true)));
    assertFalse(new StreamExample().test(Stream.of()));
  }

}
