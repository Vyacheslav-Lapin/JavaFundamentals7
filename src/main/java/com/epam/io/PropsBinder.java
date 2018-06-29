package com.epam.io;

import com.epam.fp.StreamUtils;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.Properties;

public interface PropsBinder {

    static <T> T from(Class<T> tClass) {
        return from(tClass.getSimpleName(), tClass);
    }

    @SneakyThrows
    static <T> T from(String fileName, Class<T> tClass) {
        val properties = new Properties();
        @Cleanup val inputStream = PropsBinder.class.getResourceAsStream(
                String.format("/%s.properties", fileName));
        properties.load(inputStream);
        return from(properties, tClass);
    }

    @SneakyThrows
    static <T> T from(Properties properties, Class<T> tClass) {
        //noinspection unchecked
        val constructor = (Constructor<T>) Arrays.stream(tClass.getConstructors())
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .orElseThrow(() -> new RuntimeException("Нету ни одного конструктора!"));

        return from(properties, constructor);
    }

    @NotNull
    @SneakyThrows
    static <T> T from(Properties properties, Constructor<T> constructor) {
        Object[] paramValues = Arrays.stream(constructor.getParameters())
                .map(parameter -> resolveParameter(parameter, properties))
                .toArray();

        return constructor.newInstance(paramValues);
    }

    private static Object resolveParameter(Parameter parameter, Properties properties) {
        Class<?> parameterType = parameter.getType();
        String name = parameter.getName();
        String value = properties.getProperty(name);
        if (parameterType == String.class)
            return value;
        if (parameterType == int.class || parameterType == Integer.class)
            return Integer.parseInt(value);
        if (parameterType == double.class || parameterType == Double.class)
            return Double.parseDouble(value);
        if (parameterType == long.class || parameterType == Long.class)
            return Long.parseLong(value);
        if (parameterType == boolean.class || parameterType == Boolean.class)
            return Boolean.parseBoolean(value);
        if (parameterType == float.class || parameterType == Float.class)
            return Float.parseFloat(value);
        if (parameterType == char.class || parameterType == Character.class)
            return value.charAt(0);
        if (parameterType == byte.class || parameterType == Byte.class)
            return Byte.parseByte(value);
        if (parameterType == short.class || parameterType == Short.class)
            return Short.parseShort(value);

        return resolveObjectParameter(properties, parameterType, name);
    }

    @NotNull
    static Object resolveObjectParameter(Properties properties, Class<?> parameterType, String name) {
        Properties subProps = properties.entrySet().stream()
                .map(entry -> Map.entry(
                        entry.getKey().toString(),
                        entry.getValue().toString()))
                .filter(entry -> entry.getKey().startsWith(name + '.'))
                .map(entry -> Map.entry(
                        entry.getKey().substring(name.length() + 1),
                        entry.getValue()))
                .collect(StreamUtils.toProperties());

        return from(subProps, parameterType);
    }
}
