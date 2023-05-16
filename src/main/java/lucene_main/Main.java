package lucene_main; // Defines the package of the class

import lucene_utilities.UserInteraction;
import lucene_services.IndexService;
import lucene_services.SearchService;
import lucene_services.ResultService;


import org.apache.lucene.search.TopDocs;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        UserInteraction userInteractionService = new UserInteraction();
        String indexDirPath = userInteractionService.getIndexDirPath();
        String dataDirPath = userInteractionService.getDataDirPath();
        String queryStr = userInteractionService.getQuery();

        try {
            // Step 1: Initialize and use the IndexService
            IndexService indexService = new IndexService(indexDirPath);
            indexService.indexFiles(dataDirPath);

            int maxFiles = indexService.getIndexedFilesCount();

            int numHits = userInteractionService.getNumHits(maxFiles);

            // Step 2: Initialize and use the SearchService
            SearchService searchService = new SearchService(indexDirPath);
            TopDocs topDocs = searchService.searchIndex(queryStr, numHits);

            // Step 3: Initialize and use the ResultService
            ResultService resultService = new ResultService(topDocs, searchService.getSearcher(), userInteractionService);

            resultService.handleResults();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.apache.lucene.queryparser.classic.ParseException e) {
            e.printStackTrace();
        }
    }
}
