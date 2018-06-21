package com.epam.oop.spi;

import java.util.stream.Stream;

public interface MusicFinder {
    static Stream<String> getMusic() {
        return Stream.of("music1", "music3", "music2");
    }
}
