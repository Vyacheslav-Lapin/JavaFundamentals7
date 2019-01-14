package com.epam.xml.jaxb;

import com.epam.fp.CheckedFunction1;
import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;
import lombok.val;

import javax.xml.bind.JAXBContext;
import java.io.File;
import java.io.FileOutputStream;
import java.util.function.Function;

import static lombok.AccessLevel.PRIVATE;

@UtilityClass
@FieldDefaults(level = PRIVATE, makeFinal = true)
class JaxBExample {

  @SuppressWarnings("WeakerAccess")
  Function<Class<?>, JAXBContext> GET_CONTEXT =
    CheckedFunction1.<Class<?>, JAXBContext>of(JAXBContext::newInstance)
      .unchecked()
      .memoized();

  @SneakyThrows
  <T> T getBean(String fileName, Class<T> aClass) {

    val unmarshaller = GET_CONTEXT.apply(aClass).createUnmarshaller();
//        noinspection unchecked
    return (T) unmarshaller.unmarshal(new File(fileName));
  }

  @SneakyThrows
  <T> void setBean(String fileName, T t) {
    @Cleanup FileOutputStream os = new FileOutputStream(fileName);
    val marshaller = GET_CONTEXT.apply(t.getClass()).createMarshaller();
    marshaller.marshal(t, os);
  }
}
