package com.epam.oop.annotations;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@MyParentAnnotation("qwerty")
public @interface MyAnnotation {
  String value();
}
