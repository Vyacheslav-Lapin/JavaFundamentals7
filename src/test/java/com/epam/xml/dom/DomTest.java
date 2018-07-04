package com.epam.xml.dom;

import com.epam.io.InputStreamUtils;
import com.epam.xml.Food;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@FieldDefaults(level = PRIVATE)
public class DomTest {

    @Test
    @SneakyThrows
    @DisplayName("\"Read\" works correctly")
    void testName() {

        Element root = InputStreamUtils.mapFileInputStream("menu.xml",
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


    @Test
    @SneakyThrows
    @DisplayName("\"Write\" works correctly")
    void testWrite() {
        Document document = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();

        Element breakfastMenu = document.createElement("breakfast-menu");
        Element food = document.createElement("food");
        food.setAttribute("id", "234");

        Element name = document.createElement("name");
        name.setTextContent("Waffles");

        food.appendChild(name);
        breakfastMenu.appendChild(food);
        document.appendChild(breakfastMenu);

        TransformerFactory.newInstance()
                .newTransformer()
                .transform(
                        new DOMSource(document),
                        new StreamResult(new FileWriter("src/test/resources/dommenu.xml")));
    }
}
