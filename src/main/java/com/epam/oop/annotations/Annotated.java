package com.epam.oop.annotations;

@MyAnnotation("for class")
final class Annotated {

  @MyAnnotation("for method")
  @SuppressWarnings("WeakerAccess")
  public final String getString(@MyAnnotation("for parameter") String s) {
    return s + " from method";
  }
}
