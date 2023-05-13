package gr.lucene_content_finder;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class Indexer {
    private final IndexWriter writer;

    public Indexer(String indexDirPath) throws IOException {
        Directory indexDir = FSDirectory.open(Paths.get(indexDirPath));
        StandardAnalyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        writer = new IndexWriter(indexDir, config);
    }

    public void indexFile(File file) throws IOException {
        DocumentBuilder documentBuilder = new DocumentBuilder();
        Document document = documentBuilder.buildDocument(file);
        writer.addDocument(document);
//        System.out.println("Indexed file: " + file.getAbsolutePath());
    }

    public void close() throws IOException {
        writer.close();
    }
}
