package gr.lucene_content_finder; // Defines the package of the class

import org.apache.lucene.search.TopDocs;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

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


            // Step 3: Initialize the searcher
            Searcher searcher = new Searcher(indexDirPath);

            // Step 4: Search the index
            TopDocs topDocs = searcher.search(queryStr, 10);
            searcher.printTopDocs(topDocs);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.apache.lucene.queryparser.classic.ParseException e) {
            e.printStackTrace();
        }

        scanner.close();
    }
}
