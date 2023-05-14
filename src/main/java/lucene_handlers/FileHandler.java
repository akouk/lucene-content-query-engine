package lucene_handlers;

import java.io.File;

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
