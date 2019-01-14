package com.epam.jdbc;

public interface Identifiable<T> {
  Long getId();

  T setId(Long id);
}
