package com.epam.xml.fp;

import java.util.Arrays;
import java.util.function.Predicate;

public interface Predicates {

    static <T> Predicate<T> exact(T value) {
        return obj -> obj == value;
    }

    @SafeVarargs
    static <T> Predicate<T> exactAny(T... values) {
        return obj -> Arrays.stream(values)
                .anyMatch(value -> obj == value);
    }
}
