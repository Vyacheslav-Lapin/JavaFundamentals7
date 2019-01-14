package com.epam.oop.inner;

import lombok.experimental.FieldDefaults;

import java.util.Date;

import static lombok.AccessLevel.PRIVATE;

/**
 * Доступ к элементам внутреннего класса возможен только из
 * внешнего класса через объект внутреннего класса
 */
@FieldDefaults(level = PRIVATE)
public class Outer2 {
  /*private*/ Inner inner = new Inner();
  /*private*/ String str;
  /*private*/ Date date;

  public void callMethodInInner() {
    inner.method();
  }

  class Inner {
    public void method() {
      //...
    }
  }
}
