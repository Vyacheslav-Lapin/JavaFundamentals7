package com.epam.io;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;
import java.util.function.Function;

public interface PropsBinder {

    static <T> T from(Class<T> tClass) {
        return from(tClass.getSimpleName(), tClass);
    }

    @SneakyThrows
    static <T> T from(String propertiesFileName, Class<T> tClass) {
        val properties = new Properties();
        @Cleanup val inputStream = PropsBinder.class.getResourceAsStream(
                String.format("/%s.properties", propertiesFileName));
        properties.load(inputStream);
        return from(properties::getProperty, tClass);
    }

    @SneakyThrows
    static <T> T from(Function<String, String> getProperty, Class<T> tClass) {
        //noinspection unchecked
        val constructor = (Constructor<T>) Arrays.stream(tClass.getConstructors())
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .orElseThrow(() -> new RuntimeException("Нету ни одного конструктора!"));

        return from(getProperty, constructor);
    }

    @NotNull
    @SneakyThrows
    static <T> T from(Function<String, String> getProperty, Constructor<T> constructor) {
        Object[] paramValues = Arrays.stream(constructor.getParameters())
                .map(parameter -> resolveParameter(parameter, getProperty))
                .toArray();

        return constructor.newInstance(paramValues);
    }

    private static Object resolveParameter(Parameter parameter,
                                           Function<String, String> getProperty) {
        return resolveParameter(getProperty, parameter.getType(), parameter.getName());
    }

    private static <T> T resolveParameter(Function<String, String> getProperty,
                                  Class<T> parameterType,
                                  String name) {

        if (parameterType == String.class)
            //noinspection unchecked
            return (T) getProperty.apply(name);
        if (parameterType == int.class || parameterType == Integer.class)
            //noinspection unchecked
            return (T) Integer.valueOf(getProperty.apply(name));
        if (parameterType == double.class || parameterType == Double.class)
            //noinspection unchecked
            return (T) Double.valueOf(getProperty.apply(name));
        if (parameterType == long.class || parameterType == Long.class)
            //noinspection unchecked
            return (T) Long.valueOf(getProperty.apply(name));
        if (parameterType == boolean.class || parameterType == Boolean.class)
            //noinspection unchecked
            return (T) Boolean.valueOf(getProperty.apply(name));
        if (parameterType == float.class || parameterType == Float.class)
            //noinspection unchecked
            return (T) Float.valueOf(getProperty.apply(name));
        if (parameterType == char.class || parameterType == Character.class)
            //noinspection unchecked
            return (T) Character.valueOf(getProperty.apply(name).charAt(0));
        if (parameterType == byte.class || parameterType == Byte.class)
            //noinspection unchecked
            return (T) Byte.valueOf(getProperty.apply(name));
        if (parameterType == short.class || parameterType == Short.class)
            //noinspection unchecked
            return (T) Short.valueOf(getProperty.apply(name));

        return resolveObjectParameter(getProperty, parameterType, name);
    }

    @NotNull
    static <T> T resolveObjectParameter(Function<String, String> getProperty,
                                         Class<T> type,
                                         String prefix) {
        //noinspection AssertionCanBeIf
        if (type.isInterface() || Modifier.isAbstract(type.getModifiers()))
            throw new PropsBinderException("Type must not be an interface or abstract class!");
        return from(s -> getProperty.apply(String.format("%s.%s", prefix, s)), type);
    }
}
