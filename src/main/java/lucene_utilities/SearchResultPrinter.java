package lucene_utilities;

import lucene_searcher.Searcher;

import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.document.Document;
import java.io.IOException;


public class SearchResultPrinter {
    public void printTopDocs(TopDocs topDocs, Searcher searcher) throws IOException {
        ScoreDoc[] hits = topDocs.scoreDocs;
        System.out.println("Number of hits: " + hits.length);

        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            int docId = scoreDoc.doc;
            Document document = searcher.getDocument(docId);
            String filePath = document.get("filepathString");
            System.out.println("Score: " + scoreDoc.score);
            System.out.println("Document ID: " + docId);
            System.out.println("File Path: " + filePath);
        }
    }
}
