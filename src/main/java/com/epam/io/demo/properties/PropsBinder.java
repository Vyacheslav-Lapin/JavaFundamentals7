package com.epam.io.demo.properties;

import com.epam.io.InputStreamUtils;
import io.vavr.API;
import io.vavr.Function0;
import io.vavr.Function1;
import lombok.SneakyThrows;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Properties;
import java.util.function.Function;

import static com.epam.fp.Predicates.exact;
import static com.epam.fp.Predicates.exactAny;
import static io.vavr.API.*;

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

  @NotNull
  static <T> T from(String propertiesFileName,
                    @NotNull Constructor<T> constructor) {
    return from(parseProperties(propertiesFileName), constructor);
  }

  @NotNull
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

  @NotNull
  @SneakyThrows
  private static Function1<String, String> parseProperties(String propertiesFileName) {
    val properties = new Properties();
    InputStreamUtils.withFileInputStream(propertiesFileName + ".properties", properties::load);
    return properties::getProperty;
  }

  @NotNull
  private static <T> Constructor<T> getMaxArgsCountConstructor(@NotNull Class<T> tClass) {
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
      Case(API.<Class<T>>$(exact(String.class)), p),
      Case(API.<Class<T>>$(exactAny(int.class, Integer.class)), p.andThen(Integer::valueOf)),
      Case(API.<Class<T>>$(exactAny(double.class, Double.class)), p.andThen(Double::valueOf)),
      Case(API.<Class<T>>$(exactAny(long.class, Long.class)), p.andThen(Long::valueOf)),
      Case(API.<Class<T>>$(exactAny(boolean.class, Boolean.class)), p.andThen(Boolean::valueOf)),
      Case(API.<Class<T>>$(exactAny(float.class, Float.class)), p.andThen(Float::valueOf)),
      Case(API.<Class<T>>$(exactAny(char.class, Character.class)), p.andThen(s -> s.charAt(0)).andThen(Character::valueOf)),
      Case(API.<Class<T>>$(exactAny(byte.class, Byte.class)), p.andThen(Byte::valueOf)),
      Case(API.<Class<T>>$(exactAny(short.class, Short.class)), p.andThen(Short::valueOf)),
      Case($(), () -> resolveObjectParameter(getProperty, parameterType, name))
    );
  }

  @NotNull
  private static <T> T resolveObjectParameter(Function<String, String> getProperty,
                                              @NotNull Class<T> type,
                                              String prefix) {
    if (type.isInterface() || Modifier.isAbstract(type.getModifiers()))
      throw new PropsBinderException("Type must not be an interface or abstract class!");
    return from(s -> getProperty.apply(String.format("%s.%s", prefix, s)), type);
  }
}
