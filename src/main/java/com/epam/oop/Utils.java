package com.epam.oop;

import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import static lombok.AccessLevel.PRIVATE;

@UtilityClass
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class Utils {

    int CONST1 = 5;

    int sm1() {
        return CONST1;
    }

    public void main(String... args) {
        int i = Utils.sm1();
        System.out.println(i);
    }

}
