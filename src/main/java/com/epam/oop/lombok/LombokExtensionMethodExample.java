package com.epam.oop.lombok;

import lombok.SneakyThrows;
import lombok.experimental.ExtensionMethod;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

// Lombok
@ExtensionMethod({Arrays.class, Extensions.class})
class LombokExtensionMethodExample {
  String useExtensionMethods() {
    String iAmNull = null;

    // Extensions.or(iAmNull, Extensions.toTitleCase("hELlO, WORlD!"))
    return iAmNull.or("hELlO, WORlD!".toTitleCase());
  }

  @SneakyThrows
  @SuppressWarnings("WeakerAccess")
  public final @NotNull int[] getSortedArray() {
    int[] intArray = {5, 3, 8, 2};

//    Arrays.sort(intArray);
    intArray.sort();

    return intArray;
  }


}
