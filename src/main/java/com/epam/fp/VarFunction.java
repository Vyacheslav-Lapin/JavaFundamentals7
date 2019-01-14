package com.epam.fp;

import java.util.function.Function;

@FunctionalInterface
public interface VarFunction<T, R> extends Function<T[], R> {

  @Override
  @SuppressWarnings("unchecked")
  R apply(T... ts);
}
