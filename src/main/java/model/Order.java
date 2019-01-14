package model;

import lombok.experimental.FieldDefaults;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
public final class Order {
  long id;
  Person person;
  List<GunInstance> instances;
}
