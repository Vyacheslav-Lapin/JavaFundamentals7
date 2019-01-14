package com.epam.oop.lombok;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@UtilityClass
class Extensions {

  @Contract(value = "null, _ -> param2; !null, _ -> param1", pure = true)
  public <T> T or(T obj, T ifNull) {
    return obj == null ? ifNull : obj;
  }

  public String toTitleCase(@NotNull String in) {
    return in.isEmpty() ? in :
      String.format("%s%s",
        Character.toTitleCase(in.charAt(0)),
        in.substring(1).toLowerCase());
  }
}
