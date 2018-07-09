package com.epam.oop.inner;

import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static lombok.AccessLevel.PRIVATE;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = JavaConfig.class)
//@AllArgsConstructor(onConstructor = @__(@Autowired))
@FieldDefaults(level = PRIVATE, makeFinal = true)
class Outer3Test {

    /**
     * Доступ к таким полям можно получить извне класса, используя
     * конструкцию: {@code
     * имя_внешнего_класса
     *      .имя_внутреннего класса
     *          .имя_статической_переменной}
     */
    @Test
    @DisplayName("\"Name\" method works correctly")
    void testName() {
        Outer3 outer = new Outer3();
        System.out.println(Outer3.Inner.pubfsi_pole);
    }
}