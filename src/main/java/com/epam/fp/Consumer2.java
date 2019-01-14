package com.epam.fp;

import java.util.function.Consumer;

@FunctionalInterface
public interface Consumer2<T, U> extends java.util.function.BiConsumer<T, U> {

  default Consumer<U> accept(T t) {
    return u -> accept(t, u);
  }
}
