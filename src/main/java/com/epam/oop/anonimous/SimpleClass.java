package com.epam.oop.anonimous;

import lombok.experimental.FieldDefaults;

import java.util.Date;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class SimpleClass {
    public void print() {
        Date d = new Date() {
            {
                System.out.println("This is Print() in Date class");
            }
            @Override
            public String toString() {
                return "new version toString method";
            }
        };
        System.out.println("This is Print() out of " + d);
    }

}
