package com.epam.fp;

import lombok.SneakyThrows;

public interface CheckedFunction1<T, R> extends io.vavr.CheckedFunction1<T, R> {

    static <T, R> CheckedFunction1<T, R> of(CheckedFunction1<T, R> checkedFunction1) {
        return checkedFunction1;
    }

    @SneakyThrows
    static <T extends AutoCloseable, R> R mapAndCleanup(T obj,
                                                     CheckedFunction1<T, R> function1) {
        try (obj) {
            return function1.apply(obj);
        }
    }
}
