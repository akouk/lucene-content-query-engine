package lucene_services;

import lucene_searcher.Searcher;
import lucene_utilities.SearchResultPrinter;
import lucene_handlers.SearchResultHandler;

import lucene_utilities.UserInteraction;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;

public class ResultService {

    private TopDocs topDocs;
    private Searcher searcher;
    private UserInteraction userInteractionService;

    public ResultService(TopDocs topDocs, Searcher searcher, UserInteraction userInteractionService) {
        this.topDocs = topDocs;
        this.searcher = searcher;
        this.userInteractionService = userInteractionService;
    }

    public void handleResults() throws IOException {
        if (topDocs.totalHits.value > 0) {
            SearchResultPrinter printer = new SearchResultPrinter();
            printer.printTopDocs(topDocs, searcher);

            if(userInteractionService.wantsToSaveResults()) {
                String saveDirPath = userInteractionService.getSaveDirPath();
                SearchResultHandler.handleSearchResults(topDocs, searcher, saveDirPath);
            }
        } else {
            System.out.println("No results were found for your search.");
        }
    }

}

