package com.epam.xml.sax;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;

import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@FieldDefaults(level = PRIVATE, makeFinal = true)
class MenuSaxHandlerTest {

  @Test
  @SneakyThrows
  @DisplayName("\"GetFoodList\" method works correctly")
  void testGetFoodList() {
    XMLReader reader = SAXParserFactory.newInstance()
      .newSAXParser()
      .getXMLReader();

    MenuSaxHandler handler = new MenuSaxHandler();
    reader.setContentHandler(handler);

    @Cleanup InputStream resource = MenuSaxHandlerTest.class
      .getResourceAsStream("/menu.xml");

    reader.parse(new InputSource(resource));
    assertThat(handler.getFoodList().size(), is(2));
  }
}
