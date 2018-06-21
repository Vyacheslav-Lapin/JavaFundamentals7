package com.epam.fp;

import lombok.Cleanup;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.Properties;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

public interface FPFirstExample {

    @SneakyThrows
    static String getLoginAndPwd(String fileName) {
        Properties properties = new Properties();
        @Cleanup InputStream resource = FPFirstExample.class.getResourceAsStream(
                format("/%TEXT", fileName));
        properties.load(resource);

        return ofNullable(properties.getProperty("login"))
                .flatMap(login -> ofNullable(properties.getProperty("password"))
                        .map(pwd -> format("%TEXT=%TEXT", login, pwd))
                ).orElseThrow(() ->
                        new RuntimeException("There in no login and/or password!"));
    }
}
