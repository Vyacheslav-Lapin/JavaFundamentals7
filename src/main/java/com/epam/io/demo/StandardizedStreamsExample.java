package com.epam.io.demo;

import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public final class StandardizedStreamsExample {

  @SneakyThrows
  public static void main(String... __) {

    // Output
    var printStream = System.out;
    printStream.write(104); // ASCII 'h'
    printStream.flush();
    printStream.write('\n');

    // Input
    var inputStream = System.in;

    var size = 5;
    var bytes1 = new byte[size];
    if (inputStream.read(bytes1) == size) {
      System.out.write(bytes1);
      System.out.write('\n');
      System.out.flush();
    }

    var bytes2 = new byte[inputStream.available()];
    for (int i = 0; i < bytes2.length; )
      bytes2[i++] = (byte) inputStream.read();

    for (byte b : bytes2)
      System.out.print(b);
  }
}
