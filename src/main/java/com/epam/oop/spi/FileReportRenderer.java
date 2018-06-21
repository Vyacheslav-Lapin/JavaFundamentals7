package com.epam.oop.spi;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.val;

import java.io.FileWriter;

import static lombok.AccessLevel.PACKAGE;

@FieldDefaults(level = PACKAGE, makeFinal = true)
public class FileReportRenderer extends ReportRenderer {

    static String FILE_NAME = "./music.txt";

    @Override
    @SneakyThrows
    public void generateReport() {
        @Cleanup val writer = new FileWriter(FILE_NAME);
        for (String composition: findMusic())
            writer.append(composition).append("\n");
        writer.flush();
        super.generateReport();
    }
}
