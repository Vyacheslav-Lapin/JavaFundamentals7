package com.epam.fp.demo;

import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public final class StreamExample implements Predicate<Stream<Boolean>> {

  @Override
  public boolean test(Stream<Boolean> booleanStream) {
    List<Boolean> collect = booleanStream.collect(Collectors.toList());
    return collect.contains(Boolean.TRUE) && !collect.contains(Boolean.FALSE);
  }
}
