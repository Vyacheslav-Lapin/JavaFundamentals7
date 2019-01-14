package com.epam.xml.stax;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;

import static com.epam.xml.stax.StAXMenuParser.extractMenu;
import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Log4j2
@FieldDefaults(level = PRIVATE, makeFinal = true)
class StAXMenuParserTest {

  @Test
  @SneakyThrows
  @DisplayName("\"Process\" method works correctly")
  void testProcess() {
    @Cleanup InputStream input = StAXMenuParserTest.class
      .getResourceAsStream("/menu.xml");

    XMLStreamReader reader = XMLInputFactory.newInstance()
      .createXMLStreamReader(input);

    assertThat(extractMenu(reader).size(), is(2));
  }
}
