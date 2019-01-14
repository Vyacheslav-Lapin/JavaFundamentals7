package com.epam.oop.enums;

import java.util.concurrent.ThreadLocalRandom;

public enum Faculty {
  MMF, FPMI, GEO;

  public static void main(String... __) {
    Faculty current = ThreadLocalRandom.current().nextBoolean()
      ? Faculty.GEO
      : Faculty.MMF;

    switch (current) {
      case GEO:
        System.out.print(current);
        break;
      case MMF:
        System.out.print(current);
        break;
      //case LAW : System.out.print(current);//error
      default:
        System.out.print("вне case: " + current);
    }
  }
}
