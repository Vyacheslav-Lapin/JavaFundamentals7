package com.epam.threads;

import lombok.Getter;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class ProgressThread extends Thread {

  @Getter
  long click;
  volatile boolean running = true;

  @Override
  public void run() {
    while (running)
      click++;
  }

  void stopClick() {
    running = false;
  }
}
