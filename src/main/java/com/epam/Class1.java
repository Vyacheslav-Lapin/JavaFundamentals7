package com.epam;

import lombok.experimental.FieldDefaults;

import java.util.function.Supplier;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class Class1 implements Int1, Int2 {

  public static void main(String... args) {
    Supplier<String> stringSupplier = () -> "Мама мыла" + Math.random() + "раму";

    System.out.println(stringSupplier.get());
    System.out.println(stringSupplier.get());
    System.out.println(stringSupplier.get());
    System.out.println(stringSupplier.get());
    System.out.println(stringSupplier.get());
  }

  @Override
  public int m1() {

    return 20;
  }

  @Override
  public int m2() {
    return Int1.super.m2();
  }
}
