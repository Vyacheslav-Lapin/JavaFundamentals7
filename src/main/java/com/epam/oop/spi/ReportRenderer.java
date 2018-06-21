package com.epam.oop.spi;

import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
public class ReportRenderer {
    public static ReportRenderer getInstance() {
        ServiceLoader<ReportRenderer> load = ServiceLoader.load(ReportRenderer.class);
        return load.findFirst().orElseGet(ReportRenderer::new);
    }

    public void generateReport() {
        for (String composition: findMusic())
            System.out.println(composition);
    }

    public List<String> findMusic() {
        List<String> music = MusicFinder.getMusic()
                .collect(Collectors.toList());
        Collections.sort(music);
        return music;
    }
}
