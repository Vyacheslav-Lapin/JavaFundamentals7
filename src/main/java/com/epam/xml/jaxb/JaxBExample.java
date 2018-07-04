package com.epam.xml.jaxb;

import com.epam.xml.Food;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileOutputStream;

import static lombok.AccessLevel.PRIVATE;

@UtilityClass
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class JaxBExample {

    JAXBContext context;

    static {
        try {
            context = JAXBContext.newInstance(Food.class);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public Food getFood(String fileName) {
        Unmarshaller jaxbUnmarshaller = context.createUnmarshaller();
        Food food = (Food) jaxbUnmarshaller.unmarshal(new File(fileName));
        System.out.println(food.getName());
        return food;
    }

    @SneakyThrows
    public void setFood(String fileName, Food food) {
        Marshaller m = context.createMarshaller();
        @Cleanup FileOutputStream os = new FileOutputStream(fileName);
        m.marshal(food, os);
        m.marshal(food, System.out);// на консоль
    }
}
