package com.epam.oop.inner;

import lombok.experimental.FieldDefaults;

import java.util.Date;

import static lombok.AccessLevel.PRIVATE;

/**
 * Методы внутреннего класса имеют прямой доступ ко всем полям и
 * методам внешнего класса
 */
@FieldDefaults(level = PRIVATE)
public class Outer1 {
    /*private*/ String str;
    /*private*/ Date date;

    class Inner {
        public void method() {
            System.out.println(str);
            System.out.println(date.getTime());
        }
    }
}
