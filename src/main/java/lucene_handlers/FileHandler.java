package lucene_handlers;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileHandler {
    public static File[] getFilesFromDirectory(String directoryPath) {
        File directoryFile = new File(directoryPath);
        return directoryFile.listFiles();
    }

    public static void createDirectoryIfNotExists(String directoryPath) {
        File directoryFile = new File(directoryPath);
        if (!directoryFile.exists()) {
            directoryFile.mkdirs();
        }
    }
}
