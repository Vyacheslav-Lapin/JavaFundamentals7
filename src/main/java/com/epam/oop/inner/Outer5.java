package com.epam.oop.inner;

import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class Outer5 {

  Inner inner;

  private Outer5() {
    inner = new Inner();
  }

  public static void main(String... args) {
//        getStudents(new String[]{"", "jhgsdf"});
    Outer5 outer5 = new Outer5();
    outer5.callMethodInInner();
    for (Integer integer : outer5.getIterator()) {
      System.out.println(integer);
    }

  }

  private void callMethodInInner() {
    System.out.println(Inner.prfsi_polr);
    System.out.println(Inner.pubfsi_pole);
    System.out.println(inner.x);
  }

  private Iterable<Integer> getIterator() {
    return inner;
  }

  public class Inner implements Iterable<Integer> {
    final static int pubfsi_pole = 22;
    private final static int prfsi_polr = 33;
    private int x = 58;

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
}
