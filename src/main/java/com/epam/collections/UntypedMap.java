package com.epam.collections;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.experimental.FieldDefaults;

import java.util.Map;

import static lombok.AccessLevel.PRIVATE;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
class UntypedMap<K> implements Map<K, Object> {

  @Delegate
  Map<K, Object> map;

  <V> V getUntyped(K key) {
    //noinspection unchecked
    return (V) get(key);
  }
}
