package com.epam.exceptions.inheritance;

import lombok.experimental.FieldDefaults;

import java.io.EOFException;
import java.io.IOException;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public final class DerivativeCl extends BaseCl {
  public DerivativeCl() throws IOException, ArithmeticException {
    super();
  }

  @SuppressWarnings("RedundantThrows")
  public static void methodA() throws EOFException {
  }
}
