package com.epam.xml.fp;

import java.util.function.Consumer;

import static com.epam.xml.fp.Exceptional.sneakyThrow;


@FunctionalInterface
public interface CheckedConsumer<T> extends io.vavr.CheckedConsumer<T> {

    static <T> CheckedConsumer<T> narrow(CheckedConsumer<T> consumer) {
        return consumer;
    }

    /**
     * Returns an unchecked consumer that will <em>sneaky throw</em> if an exceptions occurs when applying the function.
     *
     * @return a new Consumer<T> that throws a {@code Throwable}.
     */
    default Consumer<T> unchecked() {
        return value -> {
            try {
                accept(value);
            } catch(Throwable t) {
                sneakyThrow(t);
            }
        };
    }
}