package com.epam.fp;

import io.vavr.Tuple2;
import io.vavr.Tuple3;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Contract;

@FunctionalInterface
public interface CheckedConsumer3<T1, T2, T3> {

  @Contract(value = "_ -> param1", pure = true)
  static <T1, T2, T3> CheckedConsumer3<T1, T2, T3> of(CheckedConsumer3<T1, T2, T3> checkedConsumer3) {
    return checkedConsumer3;
  }

  void put(T1 t1, T2 t2, T3 t3) throws Exception;

  @SneakyThrows
  default void accept(T1 t1, T2 t2, T3 t3) {
    put(t1, t2, t3);
  }

  default CheckedConsumer<Tuple3<T1, T2, T3>> tupled() {
    return tuple3 -> this.put(tuple3._1, tuple3._2, tuple3._3);
  }

  default CheckedConsumer2<Tuple2<T1, T2>, T3> tupled12() {
    return (tuple12, t3) -> this.put(tuple12._1, tuple12._2, t3);
  }

  default CheckedConsumer2<T1, Tuple2<T2, T3>> tupled23() {
    return (t1, tuple23) -> this.put(t1, tuple23._1, tuple23._2);
  }

  default CheckedConsumer2<Tuple2<T1, T3>, T2> tupled13() {
    return (tuple13, t2) -> this.put(tuple13._1, t2, tuple13._2);
  }
}
