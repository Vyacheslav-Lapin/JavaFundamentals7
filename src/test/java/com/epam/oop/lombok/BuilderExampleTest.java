package com.epam.oop.lombok;// import games.BuilderExample

import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsCollectionContaining;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.MatcherAssert.assertThat;

@SuppressWarnings("WeakerAccess")
@FieldDefaults(level = PRIVATE, makeFinal = true)
class BuilderExampleTest {

  // given
  static String NAME = "Вася";
  static String OCCUPATION1 = "учёба";
  static String OCCUPATION2 = "разнос почты";
  static int AGE = 16;

  @Test
  @SneakyThrows
  @DisplayName("\"Builder\" method works correctly")
  void testBuilder() {
    // when
    val builderExample1 = BuilderExample.builder()
      .name(NAME)
      .age(AGE)
      .occupation(OCCUPATION1)
      .occupation(OCCUPATION2)
      .build();

    val builderExample2 = BuilderExample.builder()
      .created(LocalDate.MIN)
      .name(NAME)
      .age(AGE)
      .occupation(OCCUPATION1)
      .occupation(OCCUPATION2)
      .build();

    // then
    assertThat(builderExample1.getCreated(), Is.is(LocalDate.now()));
    assertThat(builderExample2.getCreated(), Is.is(LocalDate.MIN));

    assertThat(builderExample1.getName(), Is.is(NAME));
    assertThat(builderExample2.getName(), Is.is(NAME));

    assertThat(builderExample1.getAge(), Is.is(AGE));
    assertThat(builderExample2.getAge(), Is.is(AGE));

    assertThat(builderExample1.getOccupations(), IsCollectionContaining.hasItems(OCCUPATION1, OCCUPATION2));
    assertThat(builderExample2.getOccupations(), IsCollectionContaining.hasItems(OCCUPATION1, OCCUPATION2));

  }

}
