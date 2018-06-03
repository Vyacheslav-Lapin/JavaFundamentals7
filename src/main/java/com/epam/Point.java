package com.epam;

import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.Wither;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;

@Slf4j
@Builder // @AllArgsConstructor(access = PRIVATE)
//@Data//@Getter @Setter @ToString @EqualsAndHashCode @RequiredArgsConstructor
//@Value //@Getter @ToString @EqualsAndHashCode @AllArgsConstructor @FieldDefaults(level = PRIVATE, makeFinal = true)
//@NoArgsConstructor
@MyAnnotation("Мама мыла раму!")
public class Point {
    int x;

    //    @NonFinal
//    @Builder.Default
//    int y = 80;
    @Wither
    final int y;

    @Wither
    int z;

    @Singular
    List<Long> numbers;


    static int getFive() {
        main(null);
        return 5;
    }

    @SneakyThrows
    public static void main(String... args) {
        MyAnnotation myAnnotation = Point.class.getDeclaredAnnotation(MyAnnotation.class);
        System.out.println(myAnnotation.value());

        log.info("Мама мыла раму!");

        {
            @Cleanup Connection connection = null;
            connection.beginRequest();
        }
//
//        //...
//        connection.close();

        val point = Point.builder()
                .x(100)
                .z(58)

//                .numbers(Arrays.asList(1L,2L,3L,4L,5L))
                .number(1L)
                .number(2L)
                .number(3L)
                .number(4L)
                .number(5L)

                .y(200)
                .build();

//        Point point59 = point.withZ(59).withY(201);

//        System.out.println(point59.getZ());
//        Constructor<Point> constructor = Point.class.getConstructor(int.class, int.class);
//        Point point1 = constructor.newInstance(1, 5);
//        for (var parameter : constructor.getParameters()) {
//            System.out.println(parameter.getName());
//        }

    }
}
