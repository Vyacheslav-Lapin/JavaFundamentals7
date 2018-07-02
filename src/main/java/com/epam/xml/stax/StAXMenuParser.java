package com.epam.xml.stax;

import com.epam.xml.Food;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.epam.xml.stax.MenuTagName.FOOD;
import static javax.xml.stream.XMLStreamConstants.CHARACTERS;
import static javax.xml.stream.XMLStreamConstants.END_ELEMENT;
import static javax.xml.stream.XMLStreamConstants.START_ELEMENT;
import static lombok.AccessLevel.PRIVATE;

@Log4j2
@FieldDefaults(level = PRIVATE)
public class StAXMenuParser {

    public static List<Food> extractMenu(XMLStreamReader reader) throws XMLStreamException {
        List<Food> menu = new ArrayList<>();
        Food food = null;
        MenuTagName elementName = null;
        while (reader.hasNext()) {
            // определение типа "прочтённого" элемента (тега)
            int type = reader.next();
            switch (type) {
                case START_ELEMENT:
                    if ((elementName = MenuTagName.getElementTagName(reader.getLocalName()))
                            == FOOD)
                        food = new Food()
                                .setId(Integer.parseInt(
                                        reader.getAttributeValue(
                                                null,
                                                "id")));

                    break;
                case CHARACTERS:
                    String text = reader.getText().trim();
                    if (text.isEmpty())
                        break;
                    switch (elementName) {
                        case NAME:
                            food.setName(text);
                            break;
                        case PRICE:
                            food.setPrice(text);
                            break;
                        case DESCRIPTION:
                            food.setDescription(text);
                            break;
                        case CALORIES:
                            food.setCalories(Integer.parseInt(text));
                    }
                    break;

                case END_ELEMENT:
                    elementName = MenuTagName.getElementTagName(reader.getLocalName());
                    if (elementName == FOOD)
                        menu.add(food);
            }
        }
        return menu;
    }

}
