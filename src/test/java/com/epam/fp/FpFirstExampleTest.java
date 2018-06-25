package com.epam.fp;

import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@FieldDefaults(level = PRIVATE, makeFinal = true)
class FpFirstExampleTest {

    static String FILE_NAME = "credentials.properties";
    static String WRONG_FILE_NAME = "wrong-cred.properties";

    @Test
    @DisplayName("GetLoginAndPwd method works correctly")
    void testGetLoginAndPwd() {
        assertThat(FpFirstExample.getLoginAndPwd(FILE_NAME), is("vl=qwerty"));

        RuntimeException runtimeException =
                assertThrows(RuntimeException.class, () ->
                        FpFirstExample.getLoginAndPwd(WRONG_FILE_NAME));

        log.info(runtimeException.getMessage());
    }
}