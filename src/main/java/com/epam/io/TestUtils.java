package com.epam.io;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.function.Consumer;
import java.util.function.Function;

public interface TestUtils {

    @SneakyThrows
    static String fromSystemOut(Runnable task) {

        PrintStream realOut = System.out;

        @Cleanup val out = new ByteArrayOutputStream();
        @Cleanup val printStream = new PrintStream(out);

        System.setOut(printStream);
        task.run();

        System.setOut(realOut);

        return new String(out.toByteArray()).intern();
    }
}
