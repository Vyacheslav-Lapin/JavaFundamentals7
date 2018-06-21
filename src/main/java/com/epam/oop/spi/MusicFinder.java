package com.epam.oop.spi;

import java.util.stream.Stream;

@FunctionalInterface
public interface MusicFinder {

    static MusicFinder from(String... misics) {
        return () -> Stream.of(misics);
    }

    Stream<String> getMusic();
}
