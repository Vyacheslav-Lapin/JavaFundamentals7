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
        @Cleanup val pipedInputStream = new PipedInputStream();
        @Cleanup val pipedOutputStream = new PipedOutputStream(pipedInputStream);

        for (int i = 0; i < 20;)
            pipedOutputStream.write(i++);

        for (int toRead[] = new int[pipedInputStream.available()],
             i = 0; i < toRead.length; i++) {
            toRead[i] = pipedInputStream.read();
            System.out.print(toRead[i] + " ");
        }
    }
}
