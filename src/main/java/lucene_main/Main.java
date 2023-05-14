package lucene_main; // Defines the package of the class

import lucene_indexer.Indexer;
import lucene_searcher.Searcher;
import lucene_utilities.SearchResultPrinter;
import lucene_handlers.UserInputHandler;
import lucene_handlers.FileHandler;
import lucene_handlers.SearchResultHandler;


import org.apache.lucene.search.TopDocs;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        UserInputHandler userInputHandler = new UserInputHandler();

        String indexDirPath = System.getProperty("user.home") + userInputHandler.getInput("Please enter the path to the index directory:");
        String dataDirPath = System.getProperty("user.home") + userInputHandler.getInput("Please enter the path to the data directory:");
        String queryStr = userInputHandler.getInput("Please enter the search query:");

        try {
            // Step 1: Initialize the indexer
            Indexer indexer = new Indexer(indexDirPath);

            // Step 2: Index the directory
            File[] files = FileHandler.getFilesFromDirectory(dataDirPath);

            if (files != null) {
                for (File file : files) {
                    indexer.indexFile(file);
                }
            } else {
                System.out.println("No files found in the data directory: " + dataDirPath);
            }

            indexer.close();

            int maxFiles = (files != null) ? files.length : 0;

            int numHits = userInputHandler.getIntInput("Please enter the maximum number of search results:", maxFiles);

            // Step 3: Initialize the searcher
            Searcher searcher = new Searcher(indexDirPath);

            // Step 4: Search the index
            TopDocs topDocs = searcher.search(queryStr, numHits);
            SearchResultPrinter printer = new SearchResultPrinter();
            printer.printTopDocs(topDocs, searcher);
//            searcher.printTopDocs(topDocs);

            if (topDocs.totalHits.value > 0) {

                String saveResults = userInputHandler.getInput("Do you want to save the search results to a directory? (Y/N)");

                if(saveResults.equalsIgnoreCase("Y")) {
                    String saveDirPath = System.getProperty("user.home") + userInputHandler.getInput("Please enter the path to the directory where you want to store the search results:");
                    SearchResultHandler.handleSearchResults(topDocs, searcher, saveDirPath);
                }
            } else {
                System.out.println("No results were found for your search.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.apache.lucene.queryparser.classic.ParseException e) {
            e.printStackTrace();
        }
    }
}
