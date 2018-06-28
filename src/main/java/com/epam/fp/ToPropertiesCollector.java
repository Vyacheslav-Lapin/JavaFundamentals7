package com.epam.fp;

import lombok.experimental.FieldDefaults;

import java.util.EnumSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public class ToPropertiesCollector implements Collector<Map.Entry<String, String>, Properties, Properties> {

    @Override
    public Supplier<Properties> supplier() {
        return Properties::new;
    }

    @Override
    public BiConsumer<Properties, Map.Entry<String, String>> accumulator() {
        return (properties, entry) -> properties.put(entry.getKey(), entry.getValue());
    }

    @Override
    public BinaryOperator<Properties> combiner() {
        return (left, right) -> {
            left.putAll(right);
            return left;
        };
    }

    @Override
    public Function<Properties, Properties> finisher() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(
                Characteristics.IDENTITY_FINISH,
                Characteristics.UNORDERED);
    }
}
