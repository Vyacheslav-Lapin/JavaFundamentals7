package com.epam.oop.spi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.epam.io.TestUtils.fromSystemOutPrint;
import static org.assertj.core.api.Assertions.assertThat;

class ReportRendererTest {
  @Test
  @DisplayName("GenerateReport method works correctly")
  void testGenerateReport() {
    assertThat(
      fromSystemOutPrint(
        ReportRenderer.getInstance()::generateReport))
      .isNotEmpty();

  }
}
