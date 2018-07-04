package com.epam.oop.spi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.epam.io.TestUtils.fromSystemOut;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.text.IsEmptyString.emptyString;

class ReportRendererTest {
    @Test
    @DisplayName("GenerateReport method works correctly")
    void testGenerateReport() {
        assertThat(fromSystemOut(ReportRenderer.getInstance()::generateReport),
                is(not(emptyString())));

    }
}