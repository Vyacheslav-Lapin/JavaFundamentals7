package com.epam.xml.dom;

import com.epam.io.InputStreamUtils;
import com.epam.xml.Food;
import io.vavr.CheckedFunction1;
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
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.epam.io.TestUtils.toTestResourceFilePath;
import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("WeakerAccess")
@FieldDefaults(level = PRIVATE, makeFinal = true)
class DomTest {

  static File FILE = new File(toTestResourceFilePath("document.xml"));

  private static Element getFirstChildElement(Element element, String childName) {
    return (Element) element.getElementsByTagName(childName).item(0);
  }

  @Test
  @SneakyThrows
  @DisplayName("\"Read\" works correctly")
  void testName() {
    // given
    CheckedFunction1<InputStream, Document> fisMapper =
      DocumentBuilderFactory
        .newInstance()
        .newDocumentBuilder()::parse;

    Element root = InputStreamUtils.mapFileInputStream(
      "menu.xml",
      fisMapper)
      .getDocumentElement();

    // when
    List<Food> menu = new ArrayList<>();
    NodeList foodNodes = root.getElementsByTagName("food");
    for (int i = 0; i < foodNodes.getLength(); i++) {
      Element foodElement = (Element) foodNodes.item(i);
      int id = Integer.parseInt(foodElement.getAttribute("id"));
      String name = getFirstChildElement(foodElement, "name").getTextContent().trim();
      String price = getFirstChildElement(foodElement, "price").getTextContent().trim();
      String description = getFirstChildElement(foodElement, "description").getTextContent().trim();
      int calories = Integer.parseInt(getFirstChildElement(foodElement, "calories").getTextContent().trim());
      menu.add(new Food(id, name, price, description, calories));
    }

    // then
    assertThat(menu.size(), is(2));

  }

  @Test
  @SneakyThrows
  @DisplayName("\"Write\" works correctly")
  void testWrite() {
    // given
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

    // when
    TransformerFactory.newInstance()
      .newTransformer()
      .transform(
        new DOMSource(document),
        new StreamResult(FILE));

    // then
    assertTrue(FILE.exists());

    assertTrue(FILE.delete());

  }
}
