package com.epam.jdbc.model;

import com.epam.jdbc.cp.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class Student implements Identifiable<Student> {

    long id;
    String name;
    int groupId;

    public Student(String name, int groupId) {
        this(0L, name, groupId);
    }
}
