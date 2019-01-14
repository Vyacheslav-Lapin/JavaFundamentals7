package com.epam;

import lombok.*;
import lombok.experimental.Wither;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
//@NoArgsConstructor
@Builder // @AllArgsConstructor(access = PRIVATE)
@Data // @Getter //@Setter @ToString @EqualsAndHashCode @RequiredArgsConstructor
//@Value //@Getter @ToString @EqualsAndHashCode @AllArgsConstructor @FieldDefaults(level = PRIVATE, makeFinal = true)
public class Point {
  //    @NonFinal
//    @Builder.Default
//    int y = 80;
  @Wither
  final int y;
  int x;
  @Wither
  int z;
  @Singular
  List<Long> numbers;

  @SneakyThrows
  public static void main(String... args) {

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

  public int getX() {
    return this.x;
  }
}
