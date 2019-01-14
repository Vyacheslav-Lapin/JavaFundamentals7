package com.epam.threads;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PriorityDemo {

  @Test
  @DisplayName("Run method works correctly with priority")
  void testRun() {
    Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
    val hi1 = new ProgressThread();
    val hi2 = new ProgressThread();
    val hi3 = new ProgressThread();
    val hi4 = new ProgressThread();
    val lo1 = new ProgressThread();
    val lo2 = new ProgressThread();
    val lo3 = new ProgressThread();
    val lo4 = new ProgressThread();

    hi1.setPriority(Thread.MAX_PRIORITY);
    hi2.setPriority(Thread.MAX_PRIORITY);
    hi3.setPriority(Thread.MAX_PRIORITY);
    hi4.setPriority(Thread.MAX_PRIORITY);
    lo1.setPriority(Thread.MIN_PRIORITY);
    lo2.setPriority(Thread.MIN_PRIORITY);
    lo3.setPriority(Thread.MIN_PRIORITY);
    lo4.setPriority(Thread.MIN_PRIORITY);

    lo1.start();
    hi1.start();
    lo2.start();
    hi2.start();
    lo3.start();
    hi3.start();
    lo4.start();
    hi4.start();

    try {
      Thread.sleep(100);
    } catch (InterruptedException e) {
      System.out.println("Main thread interrupted.");
    }

    lo1.stopClick();
    lo2.stopClick();
    lo3.stopClick();
    lo4.stopClick();
    hi1.stopClick();
    hi2.stopClick();
    hi3.stopClick();
    hi4.stopClick();

    try {
      hi1.join();
      hi2.join();
      hi3.join();
      hi4.join();
      lo1.join();
      lo2.join();
      lo3.join();
      lo4.join();
    } catch (InterruptedException e) {
      System.out.println("InterruptedException caught");
    }
    System.out.println("Low-priority thread: " + lo1.getClick());
    System.out.println("Low-priority thread: " + lo2.getClick());
    System.out.println("Low-priority thread: " + lo3.getClick());
    System.out.println("Low-priority thread: " + lo4.getClick());
    System.out.println("High-priority thread: " + hi1.getClick());
    System.out.println("High-priority thread: " + hi2.getClick());
    System.out.println("High-priority thread: " + hi3.getClick());
    System.out.println("High-priority thread: " + hi4.getClick());
  }
}
