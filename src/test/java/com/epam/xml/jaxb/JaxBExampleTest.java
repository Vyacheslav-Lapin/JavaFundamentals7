package com.epam.xml.jaxb;

import com.epam.xml.Food;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static com.epam.io.TestUtils.toTestResourceFilePath;
import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("WeakerAccess")
@FieldDefaults(level = PRIVATE, makeFinal = true)
class JaxBExampleTest {

  static String FILE_NAME = toTestResourceFilePath("food.xml");

  static Food writeFood = new Food(123,
    "Waffles",
    "$12",
    "вкусные вафли",
    650);

  @Test
  @SneakyThrows
  @DisplayName("\"SetFood\" method works correctly")
  void testSetFood() {
    var numbers = new int[]{1, 2, 3, 4, 5, 6, 7};
    int[] subset = Arrays.stream(numbers).filter(a -> a > 5).toArray();
    for (int i1 : subset) {
      System.out.println(i1);
    }
    JaxBExample.setBean(FILE_NAME, writeFood);
    assertThat(JaxBExample.getBean(FILE_NAME, Food.class), is(writeFood));
    assertTrue(new File(FILE_NAME).delete());
  }
}
