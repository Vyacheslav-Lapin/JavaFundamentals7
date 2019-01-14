package com.epam.collections;

import lombok.experimental.UtilityClass;
import lombok.val;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class Java9BackPort {

  @NotNull
  @SafeVarargs
  public <T> Map<T, T> mapOf(@NotNull T... ts) {
    assert ts.length % 2 == 0;
    val result = new HashMap<T, T>(ts.length / 2);
    for (int i = 0; i < ts.length; )
      result.put(ts[i++], ts[i++]);
    return Collections.unmodifiableMap(result);
  }

  @NotNull
  public <K, V> Map<K, V> mapOf(K k1, V v1) {
    return Collections.singletonMap(k1, v1);
  }

  @NotNull
  public <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2) {
    val result = new HashMap<K, V>(2);
    result.put(k1, v1);
    result.put(k2, v2);
    //noinspection Java9CollectionFactory
    return Collections.unmodifiableMap(result);
  }

  @NotNull
  public <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3) {
    val result = new HashMap<K, V>(3);
    result.put(k1, v1);
    result.put(k2, v2);
    result.put(k3, v3);
    //noinspection Java9CollectionFactory
    return Collections.unmodifiableMap(result);
  }

  @NotNull
  public <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
    val result = new HashMap<K, V>(4);
    result.put(k1, v1);
    result.put(k2, v2);
    result.put(k3, v3);
    result.put(k4, v4);
    //noinspection Java9CollectionFactory
    return Collections.unmodifiableMap(result);
  }

  @NotNull
  public <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
    val result = new HashMap<K, V>(5);
    result.put(k1, v1);
    result.put(k2, v2);
    result.put(k3, v3);
    result.put(k4, v4);
    result.put(k5, v5);
    //noinspection Java9CollectionFactory
    return Collections.unmodifiableMap(result);
  }

  @NotNull
  public <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
    val result = new HashMap<K, V>(5);
    result.put(k1, v1);
    result.put(k2, v2);
    result.put(k3, v3);
    result.put(k4, v4);
    result.put(k5, v5);
    result.put(k6, v6);
    //noinspection Java9CollectionFactory
    return Collections.unmodifiableMap(result);
  }

  @NotNull
  public <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7) {
    val result = new HashMap<K, V>(5);
    result.put(k1, v1);
    result.put(k2, v2);
    result.put(k3, v3);
    result.put(k4, v4);
    result.put(k5, v5);
    result.put(k6, v6);
    result.put(k7, v7);
    //noinspection Java9CollectionFactory
    return Collections.unmodifiableMap(result);
  }

  @NotNull
  public <K, V> Map<K, V> mapOf(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6, K k7, V v7, K k8, V v8) {
    val result = new HashMap<K, V>(5);
    result.put(k1, v1);
    result.put(k2, v2);
    result.put(k3, v3);
    result.put(k4, v4);
    result.put(k5, v5);
    result.put(k6, v6);
    result.put(k7, v7);
    result.put(k8, v8);
    //noinspection Java9CollectionFactory
    return Collections.unmodifiableMap(result);
  }
}
