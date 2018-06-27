package com.epam.oop.spi;

import java.util.stream.Stream;

@FunctionalInterface
public interface MusicFinder {

    static MusicFinder from(String... musics) {
        return () -> Stream.of(musics);
    }

    Stream<String> getMusic();
}
