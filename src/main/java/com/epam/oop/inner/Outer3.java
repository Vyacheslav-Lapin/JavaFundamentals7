package com.epam.oop.inner;

import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

/**
 * Внутренние классы не могут содержать static-полей,
 * кроме final static
 */
class Outer3 {
  //...

  @FieldDefaults(level = PRIVATE)
  class Inner {
    //        private static int static_pole; // ERROR
    final static int pubfsi_pole = 22;
    /*private*/ final static int prfsi_polr = 33;
    /*private*/ int i;
  }

  //...
}

/**
 * Доступ к таким полям можно получить извне класса, используя конструкцию:
 * {@code имя_внешнего_класса.имя_внутреннего класса.имя_статической_переменной}
 */
class Outer3Another {
  Outer3.Inner inner = new Outer3().new Inner();
}
