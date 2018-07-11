package com.epam.io;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public interface TestUtils {

    String TEST_RESOURCES_PATH = "./src/test/resources/";

    @NotNull
    @SneakyThrows
    static String fromSystemOut(@NotNull Runnable task) {

        val out = new ByteArrayOutputStream();
        @Cleanup val printStream = new PrintStream(out);
        PrintStream realOut = System.out;
        System.setOut(printStream);
        task.run();
        System.setOut(realOut);

        return new String(out.toByteArray()).intern();
    }

    @NotNull
    @Contract(pure = true)
    static String toTestResourceFilePath(String fileName) {
        return TEST_RESOURCES_PATH + fileName;
    }
}
