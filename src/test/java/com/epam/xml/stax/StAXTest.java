package com.epam.xml.stax;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileWriter;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
class StAXTest {

  @Test
  @SneakyThrows
  @DisplayName("\"Name\" method works correctly")
  void testName() {

    @Cleanup XMLStreamWriter writer = XMLOutputFactory.newInstance()
      .createXMLStreamWriter(
        new FileWriter("./src/test/resources/output2.xml"));

    writer.writeStartDocument();
    writer.writeStartElement("document");
    writer.writeStartElement("data");
    writer.writeAttribute("name", "value");
    writer.writeCharacters("content");
    writer.writeEndElement();
    writer.writeEndElement();
    writer.writeEndDocument();
  }
}
