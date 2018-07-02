package com.epam.io.properties;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;
import java.util.function.Function;

public interface PropsBinder {

    @NotNull
    static <T> T from(Class<T> tClass) {
        return from(tClass.getSimpleName(), tClass);
    }

    @NotNull
    static <T> T from(String propertiesFileName, Class<T> tClass) {
        return from(
                parseProperties(propertiesFileName),
                getMaxArgsCountConstructor(tClass));
    }

    static <T> T from(String propertiesFileName,
                      @NotNull Constructor<T> constructor) {
        return from(parseProperties(propertiesFileName), constructor);
    }

    @SneakyThrows
    private static <T> T from(Function<String, String> getProperty, Class<T> tClass) {
        return from(getProperty, getMaxArgsCountConstructor(tClass));
    }

    @NotNull
    @SneakyThrows
    private static <T> T from(Function<String, String> getProperty,
                              @NotNull Constructor<T> constructor) {
        return constructor.newInstance(
                Arrays.stream(constructor.getParameters())
                        .map(parameter -> resolveParameter(getProperty,
                                parameter.getType(),
                                parameter.getName()))
                        .toArray());
    }

    @SneakyThrows
    private static Function<String, String> parseProperties(String propertiesFileName) {
        val properties = new Properties();
        @Cleanup val inputStream = PropsBinder.class.getResourceAsStream(
                String.format("/%s.properties", propertiesFileName));
        properties.load(inputStream);
        return properties::getProperty;
    }

    @NotNull
    private static <T> Constructor<T> getMaxArgsCountConstructor(Class<T> tClass) {
        //noinspection unchecked
        return (Constructor<T>) Arrays.stream(tClass.getConstructors())
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .orElseThrow(() -> new PropsBinderException("Нету ни одного конструктора!"));
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
    private static <T> T resolveObjectParameter(Function<String, String> getProperty,
                                                Class<T> type,
                                                String prefix) {
        //noinspection AssertionCanBeIf
        if (type.isInterface() || Modifier.isAbstract(type.getModifiers()))
            throw new PropsBinderException("Type must not be an interface or abstract class!");
        return from(s -> getProperty.apply(String.format("%s.%s", prefix, s)), type);
    }
}
