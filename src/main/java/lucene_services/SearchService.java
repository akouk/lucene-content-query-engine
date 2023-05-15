package lucene_services;

import lucene_searcher.Searcher;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;

public class SearchService {
    private Searcher searcher;

    public SearchService(String indexDirPath) throws IOException {
        this.searcher = new Searcher(indexDirPath);
    }

    public TopDocs searchIndex(String query, int numHits) throws IOException, org.apache.lucene.queryparser.classic.ParseException {
        return searcher.search(query, numHits);
    }

    public Searcher getSearcher() {
        return this.searcher;
    }
}
