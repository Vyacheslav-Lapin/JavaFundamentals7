package com.epam.fp;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;

import java.util.Properties;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

public interface FPFirstExample {

    @SneakyThrows
    static Tuple2<String, String> getLoginAndPwd(String fileName) {
        val properties = new Properties();
        @Cleanup val inputStream = FPFirstExample.class.getResourceAsStream(
                format("/%s", fileName));
        properties.load(inputStream);

        return ofNullable(properties.getProperty("login"))
                .flatMap(login -> ofNullable(properties.getProperty("password"))
                        .map(pwd -> Tuple.of(login, pwd))
                ).orElseThrow(() ->
                        new RuntimeException("There in no login and/or password!"));
    }
}
