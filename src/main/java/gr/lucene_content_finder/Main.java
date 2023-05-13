package gr.lucene_content_finder; // Defines the package of the class

// Importing necessary classes from the Apache Lucene library
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

// Importing necessary classes from the Java library
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {

        String indexDirPath =  System.getProperty("user.home") + "/Downloads/index-path"; // Path to the index directory
        String dataDirPath = System.getProperty("user.home") + "/Downloads/data-path"; // Path to the folder containing text or HTML files

        try {
            Directory indexDir = FSDirectory.open(Paths.get(indexDirPath)); // Opening the directory for the index
            StandardAnalyzer analyzer = new StandardAnalyzer(); // Creating an analyzer
            IndexWriterConfig config = new IndexWriterConfig(analyzer); // Creating an index writer configuration
            IndexWriter writer = new IndexWriter(indexDir, config); // Creating an index writer

            // Accessing the data directory and getting the list of files
            File dataDir = new File(dataDirPath);
            File[] files = dataDir.listFiles();

            if (files != null) { // If files are found in the data directory
                for (File file : files) {
                    Document document = new Document(); // Creating a document and adding fields to it
                    Field pathField = new StringField("filepath", file.getCanonicalPath(), Field.Store.YES);
                    document.add(pathField);

                    // Adding content from the file to the document
                    Field contentField = new TextField("content", new FileReader(file));
                    document.add(contentField);

                    // Adding the document to the index
                    writer.addDocument(document);
                }
            } else { // If no files are found in the data directory
                System.out.println("No files found in the data directory: " + dataDirPath);
            }

            // Closing the index writer
            writer.close();

            // Opening the index reader
            IndexReader reader = DirectoryReader.open(indexDir);

            // Creating an index searcher
            IndexSearcher searcher = new IndexSearcher(reader);

            // Creating a query parser and parsing the query
            QueryParser parser = new QueryParser("content", analyzer);
            Query query = parser.parse("card");

            // Searching the index for the top 10 matching documents
            TopDocs topDocs = searcher.search(query, 10); // Retrieve top 10 matching documents

            // Iterating over each of the top documents and printing out information
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                int docId = scoreDoc.doc;
                Document document = searcher.doc(docId);
                String filePath = document.get("filepath");
                System.out.println("Score: " + scoreDoc.score);
                System.out.println("Document ID: " + docId);
                System.out.println("File Path: " + filePath);
            }

            // Closing the index reader
            reader.close();
        } catch (IOException e) { // Catch block for handling IOException
            e.printStackTrace(); // Print the stack trace for the IOException
        } catch (org.apache.lucene.queryparser.classic.ParseException e) { // Catch block for handling ParseException
            // Print the stack trace for the ParseException
            e.printStackTrace();
        }
    }
}
