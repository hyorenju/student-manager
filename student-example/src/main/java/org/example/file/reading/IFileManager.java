package org.example.file.reading;

import java.io.IOException;
import java.util.stream.Stream;

public interface IFileManager {
    Stream<String> readFile(String pathFile) throws IOException;
}
