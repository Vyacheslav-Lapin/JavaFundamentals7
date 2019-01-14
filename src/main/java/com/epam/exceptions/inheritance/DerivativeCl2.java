package com.epam.exceptions.inheritance;

import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public final class DerivativeCl2 extends BaseCl {
  // ошибок компиляции нет
  public DerivativeCl2() throws Exception {
    super();
  }

//    public static void methodA() // compile error
//            throws Exception {
//    }
}
