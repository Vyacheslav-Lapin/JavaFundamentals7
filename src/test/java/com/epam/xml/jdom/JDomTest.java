package com.epam.xml.jdom;

import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class JDomTest {

    @Test
    @SneakyThrows
    @DisplayName("\"Name\" method works correctly")
    void testName() {
        Document document = new SAXBuilder()
                .build("src/test/resources/menu.xml");

        document.getRootElement().getChildren().stream()
                .map(Element::getChildren)
                .flatMap(Collection::stream)
                .map(Element::getText)
                .map(String::trim)
                .forEach(System.out::println);
    }
}