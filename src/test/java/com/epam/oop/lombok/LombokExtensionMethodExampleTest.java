package com.epam.oop.lombok;

import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LombokExtensionMethodExampleTest {

  @Test
  @SneakyThrows
  @DisplayName("\"ExtensionMethod\" annotation works correctly")
  void testExtensionMethod() {
    // given
    val lombokExtensionMethodExample = new LombokExtensionMethodExample();

    // when
    assertThat(lombokExtensionMethodExample.useExtensionMethods())
      // then
      .isEqualTo("Hello, world!");
  }

  @Test
  @SneakyThrows
  @DisplayName("\"getSortedArray\" method works correctly")
  void testGetSortedArray() {
    // given
    val lombokExtensionMethodExample = new LombokExtensionMethodExample();

    // when
    assertThat(lombokExtensionMethodExample.getSortedArray())
      // then
      .containsSequence(2,3,5,8);

  }
}
