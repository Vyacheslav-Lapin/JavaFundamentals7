package com.epam.fp;

import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static lombok.AccessLevel.PRIVATE;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@FieldDefaults(level = PRIVATE, makeFinal = true)
class FpSecondExampleTest {

    static String TEXT = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
            "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut " +
            "enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi " +
            "ut aliquip ex ea commodo consequat. Duis aute irure dolor in " +
            "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla " +
            "pariatur. Excepteur sint occaecat cupidatat non proident, sunt in " +
            "culpa qui officia deserunt mollit anim id est laborum.";

    @Test
    @DisplayName("findLongWords method works correctly")
    void testFindLongWords() {
        String s = FpSecondExample.findLongWords(TEXT, 6);
        assertNotNull(s);
        System.out.println(s);
    }
}