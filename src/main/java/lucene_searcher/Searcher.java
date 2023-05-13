package lucene_searcher;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.Document;

import java.io.IOException;
import java.nio.file.Paths;

public class Searcher {
    private final IndexSearcher searcher;
    private final QueryParser parser;

    public Searcher(String indexDirPath) throws IOException {
        Directory indexDir = FSDirectory.open(Paths.get(indexDirPath));
        IndexReader reader = DirectoryReader.open(indexDir);
        searcher = new IndexSearcher(reader);
        parser = new QueryParser("content", new StandardAnalyzer());
    }

    public TopDocs search(String queryString, int numHits) throws org.apache.lucene.queryparser.classic.ParseException, IOException {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        QueryParser parser = new QueryParser("content", analyzer);
        Query query = parser.parse(queryString);
        System.out.println("Searching for: " + queryString);
        return searcher.search(query, numHits);
    }

    public void printTopDocs(TopDocs topDocs) throws IOException {
        ScoreDoc[] hits = topDocs.scoreDocs;
        System.out.println("Number of hits: " + hits.length);

        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            int docId = scoreDoc.doc;
            Document document = searcher.doc(docId);
            String filePath = document.get("filepathString");
            System.out.println("Score: " + scoreDoc.score);
            System.out.println("Document ID: " + docId);
            System.out.println("File Path: " + filePath);
        }
    }

    public Document getDocument(int docId) throws IOException {
        return searcher.doc(docId);
    }

}
