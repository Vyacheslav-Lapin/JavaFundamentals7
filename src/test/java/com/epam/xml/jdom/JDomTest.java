package com.epam.xml.jdom;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.util.Collection;

import static lombok.AccessLevel.PRIVATE;
import static org.jdom2.Namespace.getNamespace;

@FieldDefaults(level = PRIVATE)
public class JDomTest {

    public static final String FILE_NAME = "src/test/resources/menu.xml";
    public static final String NEW_MENU_FILE_NAME = "src/test/resources/newmenu.xml";

    @Test
    @SneakyThrows
    @DisplayName("\"Name\" method works correctly")
    void testName() {
        Document document = new SAXBuilder()
                .build(FILE_NAME);

        document.getRootElement().getChildren().stream()
                .map(Element::getChildren)
                .flatMap(Collection::stream)
                .map(Element::getText)
                .map(String::trim)
                .forEach(System.out::println);
    }

    @Test
    @SneakyThrows
    @DisplayName("\"Write\" works correctly")
    void testWrite() {

        Document document = new SAXBuilder().build(FILE_NAME);

        for (Element food : document.getRootElement().getChildren())
            if (food.getAttributeValue("id").equals("1"))
                food.getChild("name", getNamespace("http://epam.com/spb/jf/7"))
                        .setText("Meat"); //Belgian Waffles

        @Cleanup val fileOutputStream = new FileOutputStream(FILE_NAME);
        new XMLOutputter().output(document, fileOutputStream);
    }

    @Test
    @SneakyThrows
    @DisplayName("\"Create\" works correctly")
    void testCreate() {
        Element root = new Element("breakfast-menu");

        Element food = new Element("food");
        food.setAttribute("id", "123");

        Element name = new Element("name");
        name.setText("Waffles");

        food.addContent(name);
        root.addContent(food);

        @Cleanup FileOutputStream out = new FileOutputStream(NEW_MENU_FILE_NAME);
        new XMLOutputter().output(new Document(root), out);

    }
}