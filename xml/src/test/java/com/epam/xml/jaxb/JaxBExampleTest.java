package com.epam.xml.jaxb;

import com.epam.xml.Food;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = JavaConfig.class)
//@AllArgsConstructor(onConstructor = @__(@Autowired))
// JaxBExample
@FieldDefaults(level = PRIVATE, makeFinal = true)
class JaxBExampleTest {

    public static final String FILE_NAME = "src/test/resources/food.xml";

    @Test
    @SneakyThrows
    @DisplayName("\"SetFood\" method works correctly")
    void testSetFood() {

        Food writeFood = new Food(123,
                "Waffles",
                "$12",
                "вкусные вафли",
                650);

        JaxBExample.setFood(FILE_NAME, writeFood);

        Food readFood = JaxBExample.getFood(FILE_NAME);

        assertThat(readFood, Is.is(writeFood));
    }
}