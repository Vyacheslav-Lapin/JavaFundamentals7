package com.epam.exceptions;

public interface ThrowsExample {

  static void throwOne() throws IllegalAccessException {
    System.out.println("Внутри throwOne.");

    throw new IllegalAccessException("demo");
  }

  static void main(String... __) {

    try {
      throwOne();
    } catch (IllegalAccessException e) {
      System.out.println("Выброс " + e);
    }
  }

}
