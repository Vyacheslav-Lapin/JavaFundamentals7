package com.epam.fp;

import java.util.Arrays;
import java.util.stream.Collectors;

public interface FpSecondExample {

    static String findLongWords(String text, int count) {
        return Arrays.stream(text.split(" "))
                .map(s -> s.endsWith(",") || s.endsWith(".") ?
                        s.substring(0, s.length() - 1): s)
                .filter(s -> s.length() > count)
                .collect(Collectors.joining(", "));
    }
}
