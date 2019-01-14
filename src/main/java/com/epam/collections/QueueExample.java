package com.epam.collections;

import java.util.LinkedList;
import java.util.Queue;

public final class QueueExample {

  public static void main(String... __) {
    Queue<String> stringQueue = new LinkedList<>();
    stringQueue.offer("Oklahoma");
    stringQueue.offer("Indiana");
    stringQueue.offer("Georgia");
    stringQueue.offer("Texas");
    while (!stringQueue.isEmpty())
      System.out.print(stringQueue.remove() + " "); // Oklahoma Indiana Georgia Texas
  }
}
