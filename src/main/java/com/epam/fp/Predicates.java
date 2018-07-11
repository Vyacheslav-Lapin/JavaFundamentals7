package com.epam.fp;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.function.Predicate;

public interface Predicates {

    @NotNull
    @Contract(pure = true)
    static <T> Predicate<T> exact(T value) {
        return obj -> obj == value;
    }

    @NotNull
    @SafeVarargs
    @Contract(pure = true)
    static <T> Predicate<T> exactAny(T... values) {
        return obj -> Arrays.stream(values)
                .anyMatch(value -> obj == value);
    }
}
