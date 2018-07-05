package com.epam.xml.validation;

import com.epam.io.InputStreamUtils;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import static org.junit.jupiter.api.Assertions.fail;

public class ValidationTest {

    @Test
    @SneakyThrows
    @DisplayName("\"Name\" method works correctly")
    void testName() {
        InputStreamUtils.withFileInputStream("notes.xml", inputStream -> {
            try {
                SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema")
                        .newSchema(ValidationTest.class
                                .getResource("/notes.xsd"))
                        .newValidator()
                        .validate(new StreamSource(inputStream));
            } catch (SAXException ex) {
                fail(ex.getMessage());
            }
        });
    }
}
