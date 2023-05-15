package lucene_services;

import lucene_indexer.Indexer;
import lucene_handlers.FileHandler;

import java.io.File;
import java.io.IOException;

public class IndexService {
    private Indexer indexer;
    private int indexedFilesCount;

    public IndexService(String indexDirPath) throws IOException {
        this.indexer = new Indexer(indexDirPath);
    }

    public void indexFiles(String dataDirPath) throws IOException {
        File[] files = FileHandler.getFilesFromDirectory(dataDirPath);

        if (files != null) {
            for (File file : files) {
                indexer.indexFile(file);
            }
            this.indexedFilesCount = files.length;
        } else {
            System.out.println("No files found in the data directory: " + dataDirPath);
        }

        indexer.close();
    }

    public int getIndexedFilesCount() {
        return this.indexedFilesCount;
    }
}
