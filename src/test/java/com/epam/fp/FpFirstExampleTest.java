package com.epam.fp;

import com.epam.fp.demo.FpFirstExample;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static lombok.AccessLevel.PRIVATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@SuppressWarnings("WeakerAccess")
@FieldDefaults(level = PRIVATE, makeFinal = true)
class FpFirstExampleTest {

  static String FILE_NAME = "credentials.properties";
  static String WRONG_FILE_NAME = "wrong-cred.properties";

  @Test
  @DisplayName("GetLoginAndPwd method works correctly")
  void testGetLoginAndPwd() {
    assertThat(FpFirstExample.getLoginAndPwd(FILE_NAME))
      .isEqualTo("vl=qwerty");

    RuntimeException runtimeException =
      assertThrows(RuntimeException.class, () ->
        FpFirstExample.getLoginAndPwd(WRONG_FILE_NAME));

    assertThat(runtimeException.getMessage())
      .isEqualTo("There in no login and/or password!");
  }
}
