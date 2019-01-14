package com.epam.jdbc.demo.demo2;

import com.epam.jdbc.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import lombok.experimental.NonFinal;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldNameConstants
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(exclude = "id")
@FieldDefaults(level = PRIVATE, makeFinal = true)
class Student implements Identifiable<Student> {

  @NonFinal
  Long id;

  String name;
  int groupId;
}
