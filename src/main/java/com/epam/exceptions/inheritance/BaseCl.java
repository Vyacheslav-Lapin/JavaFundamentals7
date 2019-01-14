package com.epam.exceptions.inheritance;

import lombok.experimental.FieldDefaults;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class BaseCl {
  BaseCl() throws IOException, ArithmeticException {
    if (ThreadLocalRandom.current().nextBoolean()) {
      throw new IOException("bsg");
    } else {
      throw new ArithmeticException("kjshdg");
    }
  }

  @SuppressWarnings("RedundantThrows")
  public static void methodA() throws IOException {
  }
}
