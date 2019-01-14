package com.epam.fp;

import lombok.SneakyThrows;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static com.epam.fp.Exceptional.sneakyThrow;

@FunctionalInterface
public interface CheckedConsumer<T> extends io.vavr.CheckedConsumer<T> {

  @Contract(value = "_ -> param1", pure = true)
  static <T> CheckedConsumer<T> narrow(CheckedConsumer<T> consumer) {
    return consumer;
  }

  @SneakyThrows
  @Contract("_, _ -> param1")
  static <T> T doWith(T obj, @NotNull CheckedConsumer<T> consumer) {
    consumer.accept(obj);
    return obj;
  }

  @SneakyThrows
  static <T extends AutoCloseable> void doWithAndCleanup(T obj,
                                                         @NotNull CheckedConsumer<T> cns) {
    try (obj) {
      cns.accept(obj);
    }
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
      } catch (Throwable t) {
        sneakyThrow(t);
      }
    };
  }
}
