package com.epam;

public interface Int1 {
  static int m3() {
    return 100_500;
  }

  static void main(String... args) {
    System.out.println(123);
  }

  int m1();

  default int m2() {
    return 1;
  }

  class Int1Impl implements Int1 {
    @Override
    public int m1() {
      return m3();
    }
  }
}
