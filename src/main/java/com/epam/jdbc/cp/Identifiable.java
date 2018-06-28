package com.epam.jdbc.cp;

public interface Identifiable<T> {
    long getId();
    T setId(long id);
}
