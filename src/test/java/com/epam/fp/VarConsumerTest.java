package com.epam.fp;// import com.epam.fp.VarConsumer

import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
class VarConsumerTest {
  @Test
  @SneakyThrows
  @DisplayName("\"Accept\" method works correctly")
  void testAccept() {
    // given
    VarConsumer<String> stringVarConsumer = System.out::println;

    // when

    // then
  }

  @Test
  @SneakyThrows
  @DisplayName("\"Apply\" method works correctly")
  void testApply() {
    // given

    // when

    // then
  }

}
