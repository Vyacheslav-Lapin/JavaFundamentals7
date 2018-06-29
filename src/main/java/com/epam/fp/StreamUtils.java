package com.epam.fp;

import checkers.units.quals.K;
import io.vavr.CheckedFunction1;
import io.vavr.Function1;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface StreamUtils {

    static <T> Stream<T> toStream(ResultSet resultSet, Function<ResultSet, T> rowMapper) {
        return toStream(new Iterator<>() {
            @Override
            @SneakyThrows
            public boolean hasNext() {
                return resultSet.next();
            }

            @Override
            @SneakyThrows
            public T next() {
                //noinspection unchecked
                return rowMapper.apply(resultSet);
            }
        });
    }

    static <T> Stream<T> toStream(Iterator<T> iterator) {
        return toStream(iterator, false);
    }

    static <T> Stream<T> toStream(Iterator<T> iterator, boolean isParallel) {
        Iterable<T> iterable = () -> iterator;
        return StreamSupport.stream(iterable.spliterator(), isParallel);
    }

    static <K, V> Collector<Map.Entry<K, V>, ?, Properties> toProperties() {
        //noinspection unchecked
        return new ToPropertiesCollector();
    }
}
