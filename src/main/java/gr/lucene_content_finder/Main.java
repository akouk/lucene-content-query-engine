package gr.lucene_content_finder; // Defines the package of the class

import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.document.Document;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter the path to the index directory:");
        String indexDir = scanner.nextLine();
        String indexDirPath = System.getProperty("user.home") + indexDir;

        System.out.println("Please enter the path to the data directory:");
        String dataDir = scanner.nextLine();
        String dataDirPath = System.getProperty("user.home") + dataDir;

        System.out.println("Please enter the search query:");
        String queryStr = scanner.nextLine();

        try {
            // Step 1: Initialize the indexer
            Indexer indexer = new Indexer(indexDirPath);

            // Step 2: Index the directory
            File dataDirFile = new File(dataDirPath);
            File[] files = dataDirFile.listFiles();

            if (files != null) {
                for (File file : files) {
                    indexer.indexFile(file);
                }
            } else {
                System.out.println("No files found in the data directory: " + dataDirPath);
            }

            indexer.close();

            int maxFiles = (files != null) ? files.length : 0;

            int numHits;
            while (true) {
                System.out.println("Please enter the maximum number of search results:");
                numHits = scanner.nextInt();
                if (numHits <= maxFiles) {
                    break;
                }
                System.out.println("The number of results cannot be greater than the number of files. Please enter a number less than or equal to " + maxFiles);
            }


            // Step 3: Initialize the searcher
            Searcher searcher = new Searcher(indexDirPath);

            // Step 4: Search the index
            TopDocs topDocs = searcher.search(queryStr, numHits);
            searcher.printTopDocs(topDocs);

            if (topDocs.totalHits.value > 0) {
                System.out.println("Do you want to save the search results to a directory? (Y/N)");
                Scanner scanner2 = new Scanner(System.in);
                String saveResults = scanner2.nextLine();

                if(saveResults.equalsIgnoreCase("Y")) {
                    System.out.println("Please enter the path to the directory where you want to store the search results:");
                    String saveDir = scanner2.nextLine();
                    String saveDirPath = System.getProperty("user.home") + saveDir;

                    // Check if the directory exists, if not create it
                    File saveDirFile = new File(saveDirPath);
                    if (!saveDirFile.exists()) {
                        saveDirFile.mkdirs();
                    }

                    // Copy each file to the new directory
                    for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                        int docId = scoreDoc.doc;
                        Document document = searcher.getDocument(docId);
                        String sourceFilePath = document.get("filepathString");

                        File sourceFile = new File(sourceFilePath);
                        String destFilePath = saveDirPath + "/" + sourceFile.getName();
                        File destFile = new File(destFilePath);

                        System.out.println("Source file path: " + sourceFilePath);
                        Files.copy(sourceFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }

                    System.out.println("Files successfully stored in the directory: " + saveDirPath);
                }
                scanner2.close();
            } else {
                System.out.println("No results were found for your search.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.apache.lucene.queryparser.classic.ParseException e) {
            e.printStackTrace();
        }


        scanner.close();
    }
}
