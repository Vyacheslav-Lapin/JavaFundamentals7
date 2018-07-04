package com.epam.xml.dom;

import com.epam.xml.Food;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.epam.io.InputStreamUtils.mapFileInputStream;
import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@FieldDefaults(level = PRIVATE)
public class DomTest {

    @Test
    @SneakyThrows
    @DisplayName("\"Name\" method works correctly")
    void testName() {

        Element root = mapFileInputStream("menu.xml",
                DocumentBuilderFactory.newInstance().newDocumentBuilder()::parse)
                .getDocumentElement();

        List<Food> menu = new ArrayList<>();

        NodeList foodNodes = root.getElementsByTagName("food");
        for (int i = 0; i < foodNodes.getLength(); i++) {
            Element foodElement = (Element) foodNodes.item(i);
            menu.add(new Food()
                    .setId(Integer.parseInt(foodElement.getAttribute("id")))
                    .setName(getSingleChild(foodElement, "name").getTextContent().trim())
                    .setPrice(getSingleChild(foodElement, "price").getTextContent().trim())
                    .setDescription(getSingleChild(foodElement, "description").getTextContent().trim())
                    .setCalories(Integer.parseInt(getSingleChild(foodElement, "calories").getTextContent().trim())));
        }
        assertThat(menu.size(), is(2));
    }

    private static Element getSingleChild(Element element, String childName){
        return (Element) element.getElementsByTagName(childName).item(0);
    }

}
