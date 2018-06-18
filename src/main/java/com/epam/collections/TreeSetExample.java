package com.epam.collections;

import lombok.experimental.FieldDefaults;

import java.util.LinkedHashMap;
import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class TreeSetExample {

    public static void main(String... args) {
        Map<String, Integer> linkedHashMap = new LinkedHashMap<>
                (16, 0.75f);
        linkedHashMap.put("Smith", 30);
        linkedHashMap.put("Anderson", 31);
        linkedHashMap.put("Lewis", 29);
        linkedHashMap.put("Cook", 29);
        System.out.println("\nThe age for " + "Lewis is "
                + linkedHashMap.get("Lewis").intValue());
        System.out.println(linkedHashMap);
    }
}
