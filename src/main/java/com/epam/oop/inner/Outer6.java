package com.epam.oop.inner;

import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class Outer6 {

  public Iterable<Integer> getIntegers(int y) {

    int x = 52;

    class Cl1 implements Iterable<Integer> {

      @NotNull
      @Override
      public Iterator<Integer> iterator() {
        return new Iterator<>() {
          @Override
          public boolean hasNext() {
            return false;
          }

          @Override
          public Integer next() {
            return x;
          }
        };
      }
    }

//        y = 51;

    return new Cl1();
  }

}
