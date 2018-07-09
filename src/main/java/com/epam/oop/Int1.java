package com.epam.oop;

@FunctionalInterface
public interface Int1 {
    int m1();
    default int m2() {
        return m1() + 2;
    }
}
