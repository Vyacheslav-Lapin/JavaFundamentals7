package com.epam.strings;

import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.nio.charset.Charset;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class Strings {

  @SneakyThrows
  public static void main(String... args) {
    byte[] data3 = {(byte) 0xE3, (byte) 0xEE};
    String str = "Мама мыла раму";
    byte[] strCP866 = str.getBytes(Charset.forName("cp866"));
    //byte[] strCP1251 = str.getBytes("cp1251");
    byte[] strCP1251 = str.getBytes();

    for (byte b : strCP866) System.out.print(b + " ");
    System.out.println();
    for (byte b : strCP1251) System.out.print(b + " ");

    System.out.println();
    System.out.println(new String(strCP866));
    System.out.println(new String(strCP866, Charset.forName("cp866")));
    System.out.println(new String(strCP1251));
  }

}
