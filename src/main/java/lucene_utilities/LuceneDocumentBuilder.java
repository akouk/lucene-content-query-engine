package lucene_utilities;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class LuceneDocumentBuilder {
    public Document buildDocument(File file) throws IOException {
        Document document = new Document();

        // Index file path
        Field pathField = new StringField("filepathString", file.getCanonicalPath(), Field.Store.YES);
        document.add(pathField);

        // Index file content
        String content = new String(Files.readAllBytes(file.toPath()));
        Field contentField = new TextField("content", content, Field.Store.YES);
        document.add(contentField);

        return document;
    }
}
