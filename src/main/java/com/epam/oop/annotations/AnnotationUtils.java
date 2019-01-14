package com.epam.oop.annotations;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Executable;
import java.lang.reflect.Parameter;

@UtilityClass
@SuppressWarnings("WeakerAccess")
public class AnnotationUtils {

  <T extends Annotation> T getDeepAnnotation(@NotNull Class<?> targetClass,
                                             @NotNull Class<T> annotationClass) {
    return getDeepAnnotation(annotationClass, targetClass.getAnnotations());
  }

  <T extends Annotation> T getDeepAnnotation(@NotNull Executable target,
                                             @NotNull Class<T> annotationClass) {
    return getDeepAnnotation(annotationClass, target.getAnnotations());
  }

  <T extends Annotation> T getDeepAnnotation(@NotNull Parameter target,
                                             @NotNull Class<T> annotationClass) {
    return getDeepAnnotation(annotationClass, target.getAnnotations());
  }

  @Nullable
  @Contract(pure = true)
  private <T extends Annotation> T getDeepAnnotation(@NotNull Class<T> annotationClass,
                                                     @NotNull Annotation... annotations) {
    for (Annotation annotation : annotations)
      if (!annotation.annotationType().getPackageName().equals("java.lang.annotation"))
        if (annotation.annotationType().equals(annotationClass))
          //noinspection unchecked
          return (T) annotation;
        else {
          T metaAnotation = getDeepAnnotation(annotationClass, annotation.annotationType().getAnnotations());
          if (metaAnotation != null)
            return metaAnotation;
        }

    return null;
  }
}
