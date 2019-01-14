package com.epam.io.demo.properties;

import lombok.Value;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

class PropsBinderTest {

  @Test
  @DisplayName("From method works correctly")
  void testFrom() {
    val props = PropsBinder.from(Props.class);
    assertThat(props.getProp1()).isEqualTo(50);
    assertThat(props.getProp2()).isEqualTo("qwerty!");
    Props2 p3 = props.getP3();
    assertThat(p3.getP1()).isEqualTo(2);
    assertThat(p3.getP2()).isEqualTo("qwerty!!!");
  }
}

@Value
class Props2 {
  int p1;
  String p2;
}

@Value
class Props {
  int prop1;
  String prop2;
  Props2 p3;
}
