package com.epam.oop.lombok;

import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Builder
@FieldDefaults(level = PRIVATE)
class BuilderExample {

  @Default
  LocalDate created = LocalDate.now();

  String name;
  int age;

  @Singular
  Set<String> occupations;
}
