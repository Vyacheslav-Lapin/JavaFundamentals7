package com.epam.io;

import com.epam.fp.CheckedConsumer;
import io.vavr.CheckedFunction1;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

public interface InputStreamUtils {

    @SneakyThrows
    static <T> T mapFileInputStream(String fileName,
                                    @NotNull CheckedFunction1<InputStream, T> fisMapper) {
        @Cleanup val inputStream = InputStreamUtils.class
                .getResourceAsStream("/" + fileName);
        return fisMapper.apply(inputStream);
    }

    @SneakyThrows
    static void withFileInputStream(String fileName,
                                    @NotNull CheckedConsumer<InputStream> fisConsumer) {
        @Cleanup val inputStream = InputStreamUtils.class
                .getResourceAsStream("/" + fileName);
        fisConsumer.accept(inputStream);
    }
}
