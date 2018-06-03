package com.epam;

import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class Class1 implements Int1, Int2 {

    @Override
    public int m1() {
        return 20;
    }

    @Override
    public int m2() {
        return Int1.super.m2();
    }
}
