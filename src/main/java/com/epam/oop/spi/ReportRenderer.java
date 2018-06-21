package com.epam.oop.spi;

import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
public class ReportRenderer {
    public static ReportRenderer getInstance() {
        return ServiceLoader.load(ReportRenderer.class)
                .findFirst()
                .orElseGet(ReportRenderer::new);
    }

    public void generateReport() {
        for (String composition: findMusic())
            System.out.println(composition);
    }

    public List<String> findMusic() {
        return MusicFinder.from("music1", "music3", "music2").getMusic()
                .sorted()
                .collect(Collectors.toList());
    }
}
