package com.epam.collections;

import lombok.experimental.FieldDefaults;

import java.util.LinkedHashMap;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class TreeSetExample {

    public static void main(String... args) {
        Map<String, Integer> personAges = new LinkedHashMap<>(
                16,
                0.75f);

        personAges.put("Smith", 30);
        personAges.put("Anderson", 31);
        personAges.put("Lewis", 29);
        personAges.put("Cook", 29);

        System.out.println("The age for Lewis is " + personAges.get("Lewis"));
        System.out.println(personAges);
    }
}
