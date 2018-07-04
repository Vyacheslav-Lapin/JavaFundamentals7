package com.epam.xml.stax;

import com.epam.xml.Food;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.epam.xml.stax.MenuTagName.FOOD;
import static com.epam.xml.stax.MenuTagName.getElementTagName;
import static javax.xml.stream.XMLStreamConstants.*;
import static lombok.AccessLevel.PRIVATE;

@Log4j2
@FieldDefaults(level = PRIVATE)
public class StAXMenuParser {

    public static List<Food> extractMenu(XMLStreamReader reader) throws XMLStreamException {
        List<Food> menu = new ArrayList<>();
        Food food = null;
        MenuTagName elementName = null;
        String text;
        while (reader.hasNext())
            // определение типа "прочтённого" элемента (тега)
            switch (reader.next()) {
                case START_ELEMENT:
                    if ((elementName = getElementTagName(reader.getLocalName())) == FOOD)
                        food = new Food()
                                .setId(Integer.parseInt(
                                        reader.getAttributeValue(null,"id")));

                    break;
                case CHARACTERS:
                    if (!(text = reader.getText().trim()).isEmpty())
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
                    if ((elementName = getElementTagName(reader.getLocalName())) == FOOD)
                        menu.add(food);
            }

        return menu;
    }

}
