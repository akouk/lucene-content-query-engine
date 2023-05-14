package lucene_handlers;

import lucene_searcher.Searcher;

import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.document.Document;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class SearchResultHandler {
    public static void handleSearchResults(TopDocs topDocs, Searcher searcher, String saveDirPath) {
        if (topDocs.totalHits.value > 0) {
            FileHandler.createDirectoryIfNotExists(saveDirPath);

            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                try {
                    copyFileToDirectory(scoreDoc, searcher, saveDirPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("Files successfully stored in the directory: " + saveDirPath);
        } else {
            System.out.println("No results were found for your search.");
        }
    }

    private static void copyFileToDirectory(ScoreDoc scoreDoc, Searcher searcher, String saveDirPath) throws IOException {
        int docId = scoreDoc.doc;
        Document document = searcher.getDocument(docId);
        String sourceFilePath = document.get("filepathString");

        File sourceFile = new File(sourceFilePath);
        String destFilePath = saveDirPath + "/" + sourceFile.getName();
        File destFile = new File(destFilePath);

        System.out.println("Source file path: " + sourceFilePath);
        Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}

