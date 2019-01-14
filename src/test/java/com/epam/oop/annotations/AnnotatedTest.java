package com.epam.oop.annotations;

import lombok.SneakyThrows;
import lombok.experimental.ExtensionMethod;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtensionMethod(AnnotationUtils.class)
class AnnotatedTest { //for com.epam.oop.annotations.Annotated

  @Test
  @SneakyThrows
  @DisplayName("Annotation extracts correctly")
  void testAnnotation() {
    // given
    Class<Annotated> annotatedClass = Annotated.class;
    Method getStringMethod = annotatedClass.getMethod("getString", String.class);

    // when
    MyAnnotation myAnnotationBeforeClass = annotatedClass.getAnnotation(MyAnnotation.class);
    MyAnnotation myAnnotationBeforeMethod = getStringMethod.getAnnotation(MyAnnotation.class);
    MyAnnotation myAnnotationBeforeMethodParameter = getStringMethod.getParameters()[0].getAnnotation(MyAnnotation.class);

    // then
    assertThat(myAnnotationBeforeClass.value()).isEqualTo("for class");
    assertThat(myAnnotationBeforeMethod.value()).isEqualTo("for method");
    assertThat(myAnnotationBeforeMethodParameter.value()).isEqualTo("for parameter");
  }

  @Test
  @DisplayName("AnnotationHelper works correctly")
  void testName() {
    // given
    Class<Annotated> annotatedClass = Annotated.class;

    // when
    Optional<MyParentAnnotation> optionalMyParentAnnotation =
      Optional.ofNullable(
//        getDeepAnnotation(annotatedClass, MyParentAnnotation.class));
        annotatedClass.getDeepAnnotation(MyParentAnnotation.class));

    // then
    assertThat(optionalMyParentAnnotation)
      .isPresent()
      .containsInstanceOf(MyParentAnnotation.class)
      .map(MyParentAnnotation::value)
      .contains("qwerty");
  }
}
