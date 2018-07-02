package com.epam.xml.sax;

import com.epam.xml.Food;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Log4j2
@FieldDefaults(level = PRIVATE)
public class MenuSaxHandler extends DefaultHandler {

    public static final String ID_ATTRIBUTE = "id";

    @Getter
    List<Food> foodList = new ArrayList<>();
    Food food;
    StringBuilder text;

    @Override
    public void startDocument() {
        log.info("Parsing started.");
    }

    @Override
    public void endDocument() {
        log.info("Parsing ended.");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        log.info("startElement -> uri: {}, localName: {}, qName: {}", uri, localName, qName);

        text = new StringBuilder();
        if (qName.equals("food"))
            food = new Food()
                    .setId(Integer.parseInt(attributes.getValue(ID_ATTRIBUTE)));
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        text.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        MenuTagName tagName =
                MenuTagName.valueOf(
                        qName.toUpperCase()
                                .replace("-", "_"));

        switch (tagName) {
            case NAME:
                food.setName(text.toString());
                break;
            case PRICE:
                food.setPrice(text.toString());
                break;
            case DESCRIPTION:
                food.setDescription(text.toString());
                break;
            case CALORIES:
                food.setCalories(Integer.parseInt(text.toString()));
                break;
            case FOOD:
                foodList.add(food);
                food = null;
        }
    }

    @Override
    public void warning(SAXParseException e) throws SAXException {
        log.warn("WARNING: line {}: {}", e.getLineNumber(), e.getMessage());
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        log.error("ERROR: line {}: {}", e.getLineNumber(), e.getMessage());
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        log.fatal("FATAL: line {}: {}", e.getLineNumber(), e.getMessage());
        throw (e);
    }
}
