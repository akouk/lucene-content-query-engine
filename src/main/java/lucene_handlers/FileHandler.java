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

//    public static void deleteDirectoryIfExists(String directoryPath) throws IOException {
//        Path pathToBeDeleted = Paths.get(directoryPath);
//
//        if (Files.exists(pathToBeDeleted)) {
//            Files.walk(pathToBeDeleted)
//                    .sorted(Comparator.reverseOrder())
//                    .forEach(path -> {
//                        try {
//                            Files.delete(path);
//                        } catch (IOException e) {
//                            // log the exception
//                        }
//                    });
//        }
//    }

}
