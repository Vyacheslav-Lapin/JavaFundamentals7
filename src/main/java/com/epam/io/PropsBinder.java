package com.epam.io;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;

public interface PropsBinder {

    static <T> T from(Class<T> tClass) {
        return from(tClass.getSimpleName(), tClass);
    }

    @SneakyThrows
    static <T> T from(String fileName, Class<T> tClass) {
        val properties = new Properties();
        @Cleanup val inputStream = PropsBinder.class.getResourceAsStream(String.format("/%s.properties", fileName));
        properties.load(inputStream);

        //noinspection unchecked
        val constructor = (Constructor<T>) Arrays.stream(tClass.getConstructors())
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .orElseThrow(() -> new RuntimeException("Нету ни одного конструктора!"));

        Object[] paramValues = Arrays.stream(constructor.getParameters())
                .map(parameter -> resolveParameter(parameter,
                        properties.getProperty(parameter.getName())))
                .toArray();

        return constructor.newInstance(paramValues);
    }

    private static Object resolveParameter(Parameter parameter, String value) {
        Class<?> parameterType = parameter.getType();
        if (parameterType == String.class)
            return value;
        if (parameterType == int.class || parameterType == Integer.class)
            return Integer.parseInt(value);
        if (parameterType == double.class || parameterType == Double.class)
            return Double.parseDouble(value);
        if (parameterType == long.class || parameterType == Long.class)
            return Long.parseLong(value);
        // TODO: 27/06/2018 нада реализовать для прочих примитивов
        // TODO: 27/06/2018 нада реализовать для ссылочных типов
        return value;
    }
}
