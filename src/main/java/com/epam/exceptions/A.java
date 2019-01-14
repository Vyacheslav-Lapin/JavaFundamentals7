package com.epam.exceptions;

import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class A {

  int x;
  int y;

  private A(int x, int y) {

//        if (x == y)
//            throw new RuntimeException("kjbsdg");

    this.x = x;
    this.y = y;
  }

  public static A newA(int x, int y) {
    if (x == y)
      throw new RuntimeException("kjbsdg");

    return new A(x, y);
  }
}
