package model;

import com.epam.jdbc.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import static lombok.AccessLevel.PRIVATE;

@Data
@FieldNameConstants
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class Gun implements Identifiable<Gun> {
  Long id;
  String name;
  double caliber;
}
