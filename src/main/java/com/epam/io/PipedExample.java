package com.epam.io;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.val;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class PipedExample {

    @SneakyThrows
    public static void main(String... args) {
        @Cleanup val in = new PipedInputStream();
        @Cleanup val out = new PipedOutputStream(in);

        for (int i = 0; i < 20;)
            out.write(i++);

        for (int toRead[] = new int[in.available()], i = 0; i < toRead.length; i++) {
            toRead[i] = in.read();
            System.out.print(toRead[i] + " ");
        }
    }
}
