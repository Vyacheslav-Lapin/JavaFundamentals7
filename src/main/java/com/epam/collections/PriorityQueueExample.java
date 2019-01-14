package com.epam.collections;

import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.PriorityQueue;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public final class PriorityQueueExample {
  public static void main(String... __) {
    var stringPriorityQueue = new PriorityQueue<String>();
    stringPriorityQueue.offer("Oklahoma");
    stringPriorityQueue.offer("Indiana");
    stringPriorityQueue.offer("Georgia");
    stringPriorityQueue.offer("Texas");
    System.out.println("Priority queue using Comparable:");
    while (stringPriorityQueue.size() > 0)
      System.out.print(stringPriorityQueue.remove() + " ");

    var stringPriorityQueue1 = new PriorityQueue<String>(4,
      Collections.reverseOrder());
    stringPriorityQueue1.offer("Oklahoma");
    stringPriorityQueue1.offer("Indiana");
    stringPriorityQueue1.offer("Georgia");
    stringPriorityQueue1.offer("Texas");
    System.out.println("\nPriority queue using Comparator:");
    while (stringPriorityQueue1.size() > 0)
      System.out.print(stringPriorityQueue1.remove() + " ");
  }
}
