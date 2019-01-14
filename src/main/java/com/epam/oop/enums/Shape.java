package com.epam.oop.enums;

import org.jetbrains.annotations.Contract;

import static java.lang.System.out;

public enum Shape {
  RECTANGLE("red"),
  TRIANGLE("green"),
  CIRCLE("blue");

  private final String color;

  Shape(String color) {
    this.color = color;
  }

  public static void main(String... __) {
    Shape[] arr = Shape.values();
    for (Shape sh : arr)
      out.println(sh + " " + sh.getColor());
  }

  @Contract(pure = true)
  public String getColor() {
    return color;
  }
}
