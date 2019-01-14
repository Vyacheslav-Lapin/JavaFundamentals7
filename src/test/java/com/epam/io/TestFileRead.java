package com.epam.io;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStreamReader;
import java.util.Scanner;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
class TestFileRead {

  @Test
  @SneakyThrows
  @DisplayName("Name method works correctly")
  void testName() {
    val inputStream = TestFileRead.class.getResourceAsStream("/ints");
    val reader = new InputStreamReader(inputStream);
    @Cleanup val scan = new Scanner(reader);

    while (scan.hasNext()) {
      if (scan.hasNextInt())
        System.out.println(scan.nextInt() + ":int");
      else if (scan.hasNextDouble())
        System.out.println(scan.nextDouble() + ":double");
      else if (scan.hasNextBoolean())
        System.out.println(scan.nextBoolean() + ":boolean");
      else
        System.out.println(scan.next() + ":String");
    }
  }
}
