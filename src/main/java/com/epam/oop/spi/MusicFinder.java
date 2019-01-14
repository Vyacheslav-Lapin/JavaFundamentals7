package com.epam.oop.spi;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

@FunctionalInterface
public interface MusicFinder {

  @NotNull
  @Contract(pure = true)
  static MusicFinder from(String... musics) {
    return () -> Stream.of(musics);
  }

  Stream<String> getMusic();
}
