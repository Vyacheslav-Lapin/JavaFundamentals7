package com.epam.oop.anonimous;

import lombok.experimental.FieldDefaults;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class AnonymInspector {
    public void printSecond() {
        SimpleClass myCl = new SimpleClass() {

            @Override
            public void print() {
                System.out.println("!!!!!!!");
                newMethod();
            }

            public void newMethod() {
                System.out.println("New method");
            }
        };
        SimpleClass myCl2 = new SimpleClass();
        myCl.print();
//        myCl.newMethod(); // Error
        myCl2.print();
    }
}
