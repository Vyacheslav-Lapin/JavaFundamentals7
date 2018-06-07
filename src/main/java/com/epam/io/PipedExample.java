package com.epam.io;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class PipedExample {

    @SneakyThrows
    public static void main(String... args) {
        @Cleanup PipedInputStream pipeIn = new PipedInputStream();
        @Cleanup PipedOutputStream pipeOut = new PipedOutputStream(pipeIn);

        int countRead = 0;
        int[] toRead;

        for (int i = 0; i < 20; i++)
            pipeOut.write(i);

        int willRead = pipeIn.available();
        toRead = new int[willRead];

        for (int i = 0; i < willRead; i++) {
            toRead[i] = pipeIn.read();
            System.out.print(toRead[i] + " ");
        }
    }
}
