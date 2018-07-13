package com.epam.web.controllers;

import lombok.Data;
import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class SimplePerson {
    String name;
    String surname;
//    LocalDate date;
}
