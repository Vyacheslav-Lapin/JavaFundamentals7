package com.epam.collections;

import lombok.experimental.FieldDefaults;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE, makeFinal = true)
class UntypedMapTest {
  @Test
  @DisplayName("\"GetUntyped\" method works correctly")
  void testGetUntyped() {
    // given
    val map = Map.of("One", 1L,
      "Two", 2,
      "Three", "Three",
      "Four", List.of(Map.of("K", "V")));

    // when
    val ex = new UntypedMap<String>(map);

    // then
    Long one = ex.getUntyped("One");
    Integer two = ex.getUntyped("Two");
    String three = ex.getUntyped("Three");
    List<Map<String, String>> four = ex.getUntyped("Four");
  }
}
