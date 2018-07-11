package com.epam.fp;

import lombok.SneakyThrows;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface CheckedFunction1<T, R> extends io.vavr.CheckedFunction1<T, R> {

    @Contract(value = "_ -> param1", pure = true)
    static <T, R> CheckedFunction1<T, R> of(CheckedFunction1<T, R> checkedFunction1) {
        return checkedFunction1;
    }

    @SneakyThrows
    static <T extends AutoCloseable, R> R mapAndCleanup(T obj,
                                                        @NotNull CheckedFunction1<T, R> function1) {
        try (obj) {
            return function1.apply(obj);
        }
    }
}
