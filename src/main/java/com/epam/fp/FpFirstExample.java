package com.epam.fp;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;

import java.util.Properties;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

public interface FpFirstExample {

    @SneakyThrows
    static String getLoginAndPwd(String fileName) {
        val properties = new Properties();
        @Cleanup val inputStream = FpFirstExample.class.getResourceAsStream(
                format("/%s", fileName));
        properties.load(inputStream);

        return ofNullable(properties.getProperty("login"))
                .flatMap(login -> ofNullable(properties.getProperty("password"))
                        .map(pwd -> String.format("%s=%s", login, pwd))
                ).orElseThrow(() ->
                        new RuntimeException("There in no login and/or password!"));
    }
}
