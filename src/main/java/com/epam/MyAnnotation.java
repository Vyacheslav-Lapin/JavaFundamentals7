package com.epam;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
public @interface MyAnnotation {
    String value();
    int intParam() default 100500;
}
