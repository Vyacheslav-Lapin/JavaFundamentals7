package com.epam.oop;

import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;

import static lombok.AccessLevel.PRIVATE;

@UtilityClass
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class Utils {

  @SuppressWarnings("WeakerAccess")
  int CONST1 = 5;

  @Contract(pure = true)
  private int sm1() {
    return CONST1;
  }

  public void main(String... args) {
    int i = Utils.sm1();
    System.out.println(i);
  }

}
