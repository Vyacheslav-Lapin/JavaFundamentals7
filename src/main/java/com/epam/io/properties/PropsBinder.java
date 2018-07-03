package com.epam.io.properties;

import io.vavr.API;
import io.vavr.Function0;
import io.vavr.Function1;
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

import static io.vavr.API.*;
import static io.vavr.Predicates.is;
import static io.vavr.Predicates.isIn;

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
    private static <T> T from(Function1<String, String> getProperty, Class<T> tClass) {
        return from(getProperty, getMaxArgsCountConstructor(tClass));
    }

    @NotNull
    @SneakyThrows
    private static <T> T from(Function1<String, String> getProperty,
                              @NotNull Constructor<T> constructor) {
        return constructor.newInstance(
                Arrays.stream(constructor.getParameters())
                        .map(parameter -> resolveParameter(getProperty,
                                parameter.getType(),
                                parameter.getName()))
                        .toArray());
    }

    @SneakyThrows
    private static Function1<String, String> parseProperties(String propertiesFileName) {
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

    private static <T> T resolveParameter(Function1<String, String> getProperty,
                                          Class<T> parameterType,
                                          String name) {

        Function0<String> p = () -> getProperty.apply(name);

        //noinspection unchecked
        return (T) Match(parameterType).of(
                Case(API.<Class<T>>$(is(String.class)), p),
                Case(API.<Class<T>>$(isIn(int.class, Integer.class)), p.andThen(Integer::valueOf)),
                Case(API.<Class<T>>$(isIn(double.class, Double.class)), p.andThen(Double::valueOf)),
                Case(API.<Class<T>>$(isIn(long.class, Long.class)), p.andThen(Long::valueOf)),
                Case(API.<Class<T>>$(isIn(boolean.class, Boolean.class)), p.andThen(Boolean::valueOf)),
                Case(API.<Class<T>>$(isIn(float.class, Float.class)), p.andThen(Float::valueOf)),
                Case(API.<Class<T>>$(isIn(char.class, Character.class)), p.andThen(s -> s.charAt(0)).andThen(Character::valueOf)),
                Case(API.<Class<T>>$(isIn(byte.class, Byte.class)), p.andThen(Byte::valueOf)),
                Case(API.<Class<T>>$(isIn(short.class, Short.class)), p.andThen(Short::valueOf)),
                Case($(), () -> resolveObjectParameter(getProperty, parameterType, name))
        );
    }

    @NotNull
    private static <T> T resolveObjectParameter(Function<String, String> getProperty,
                                                Class<T> type,
                                                String prefix) {
        if (type.isInterface() || Modifier.isAbstract(type.getModifiers()))
            throw new PropsBinderException("Type must not be an interface or abstract class!");
        return from(s -> getProperty.apply(String.format("%s.%s", prefix, s)), type);
    }
}
