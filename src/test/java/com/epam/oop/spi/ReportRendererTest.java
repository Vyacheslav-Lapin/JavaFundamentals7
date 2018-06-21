package com.epam.oop.spi;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.epam.io.TestUtils.fromSystemOut;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.text.IsEmptyString.emptyString;
import static org.hamcrest.text.IsEmptyString.isEmptyString;
import static org.junit.jupiter.api.Assertions.*;

class ReportRendererTest {
    @Test
    @DisplayName("GenerateReport method works correctly")
    void testGenerateReport() {
        Runnable generateReport = ReportRenderer.getInstance()::generateReport;
        assertThat(fromSystemOut(generateReport), Is.is(not(emptyString())));
    }
}