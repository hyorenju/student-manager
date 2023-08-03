package org.example.file.reading;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileTextManager implements IFileManager {
    @Override
    public Stream<String> readFile(String pathFile) throws IOException {
        Path path = Paths.get(pathFile);
        return Files.lines(path, StandardCharsets.UTF_8);
    }
}
