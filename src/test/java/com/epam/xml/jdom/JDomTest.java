package com.epam.xml.jdom;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.LineSeparator;
import org.jdom2.output.XMLOutputter;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.util.Collection;

import static com.epam.io.InputStreamUtils.mapFileInputStream;
import static com.epam.io.TestUtils.toTestResourceFilePath;
import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.jdom2.Namespace.getNamespace;

@FieldDefaults(level = PRIVATE, makeFinal = true)
class JDomTest {

    static String FILE_NAME = "menu.xml";
    static String NEW_MENU_FILE_NAME = "newmenu.xml";
    static Namespace NAMESPACE = getNamespace("http://epam.com/spb/jf/7");

    @NotNull
    @SuppressWarnings("SpellCheckingInspection")
    private static XMLOutputter getXmlOutputter() {
        val xmlOutputter = new XMLOutputter();
        xmlOutputter.setFormat(Format.getPrettyFormat()
                .setLineSeparator(LineSeparator.SYSTEM));
        return xmlOutputter;
    }

    private static Element getFoodNameById(Document document, String id) {
        return document.getRootElement()
                .getChildren("food", NAMESPACE)
                .stream()
                .filter(food -> food.getAttributeValue("id").equals(id))
                .findAny()
                .orElseThrow(() -> new RuntimeException(
                        "There is no food element with id=1, needed for test"))
                .getChild("name", NAMESPACE);
    }

//    static <T> T doWithAndCleanup()

    @Test
    @SneakyThrows
    @DisplayName("\"Read\" method works correctly")
    void testRead() {
        new SAXBuilder().build(toTestResourceFilePath(FILE_NAME))
                .getRootElement().getChildren().stream()
                .map(Element::getChildren)
                .flatMap(Collection::stream)
                .map(Element::getText)
                .map(String::trim)
                .forEach(System.out::println);
    }

    @Test
    @Disabled // TODO: 05/07/2018 fix it!
    @SneakyThrows
    @DisplayName("\"Write\" works correctly")
    void testWrite() {

        // given
        String testResourceFilePath = toTestResourceFilePath(FILE_NAME);
        String text;

        //when
        {
            Document document = new SAXBuilder().build(testResourceFilePath);
            Element name = getFoodNameById(document, "1");
            text = name.getText();
            assertThat(text, is("Belgian Waffles"));
            name.setText("Meat");
            try (val fileOutputStream = new FileOutputStream(testResourceFilePath)) {
                getXmlOutputter().output(document, fileOutputStream);
            }
        }

//        Thread.sleep(10_000);

        //then
        String nameText = mapFileInputStream(FILE_NAME, inputStream ->
                getFoodNameById(new SAXBuilder().build(inputStream), "1")
                        .getText());

        assertThat(nameText, is("Meat"));

        // cleanup
        {
            Document document = new SAXBuilder().build(testResourceFilePath);
            Element name = getFoodNameById(document, "1");
            name.setText(text);
            try (val fileOutputStream = new FileOutputStream(FILE_NAME)) {
                getXmlOutputter().output(document, fileOutputStream);
            }
        }
    }

    @Test
    @SneakyThrows
    @DisplayName("\"Create\" works correctly")
    void testCreate() {

        @Cleanup val fileOutputStream = new FileOutputStream(NEW_MENU_FILE_NAME);
        getXmlOutputter().output(new Document(new Element("breakfast-menu")
                .addContent(new Element("food")
                        .setAttribute("id", "123")
                        .addContent(new Element("name")
                                .setText("Waffles")))), fileOutputStream);

    }
}