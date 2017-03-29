package indexer;

import analyzer.CodeAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.dom4j.DocumentException;
import parser.HTMLParser;
import parser.XMLParser;
import stackoverflow.Post;
import stackoverflow.PostField;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


import static org.apache.lucene.document.Field.Store.*;

/**
 * Created by str2n on 2017/3/24.
 */
public class PostIndexer {

    private IndexWriter writer = null;

    public PostIndexer() throws IOException {
        Directory questionIndexDir = FSDirectory.open(Paths.get(utils.Paths.POSTINDEXPATH));

        Map<String, Analyzer> codeSpecialAnalyzer = new HashMap<String, Analyzer>();
        codeSpecialAnalyzer.put("Code", new CodeAnalyzer());
        PerFieldAnalyzerWrapper wrapper = new PerFieldAnalyzerWrapper(new StandardAnalyzer(), codeSpecialAnalyzer);
        IndexWriterConfig config = new IndexWriterConfig(wrapper);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

        try {
            writer = new IndexWriter(questionIndexDir, config);
        } catch (IOException e) {
            System.out.println("Question IndexWriter initialize Error.");
        }
    }

    private static Document getDocument(Post p) {

        Document doc = new Document();
        // Common field
        doc.add(new IntPoint(PostField.Id.toString(), p.getId()));
        doc.add(new StoredField(PostField.IdCopy.toString(), p.getId()));

        doc.add(new StoredField(PostField.Score.toString(), p.getScore()));
        doc.add(new StoredField(PostField.CreationDate.toString(), p.getCreationDate()));

        doc.add(new TextField(PostField.Body.toString(), HTMLParser.ParseText(p.getBody()), YES));
        doc.add(new TextField(PostField.Code.toString(), HTMLParser.ParseCode(p.getBody()), YES));
        // Only for answer
        if (p.getParentId() != 0) {
            doc.add(new IntPoint(PostField.ParentId.toString(), p.getParentId()));
            doc.add(new StoredField(PostField.ParentIdCopy.toString(), p.getParentId()));
        } else {
            // Only for question
            doc.add(new TextField(PostField.Title.toString(), p.getTitle(), YES));
            doc.add(new StoredField(PostField.AnswerCount.toString(), p.getAnswerCount()));
            doc.add(new StoredField(PostField.ViewCount.toString(), p.getViewCount()));
            // Only for specific question
            if (p.getAcceptedAnswerId() != 0) {
                doc.add(new StoredField(PostField.AcceptedAnswerId.toString(), p.getAcceptedAnswerId()));
            }
            if (p.getTags() != null) {
                doc.add(new TextField(PostField.Tags.toString(), HTMLParser.ParseTag(p.getTags()), YES));
            }
        }
        return doc;
    }

    public void IndexPost() throws FileNotFoundException {
        System.out.println("Start Question Indexing:");

        Scanner questionIn = new Scanner(new File(utils.Paths.DECOMPFILESTOREPATH + "python.xml"));

        String line;

        int lineCount = 0;
        while (questionIn.hasNextLine()) {
            lineCount++;
            line = questionIn.nextLine().trim();
            try {
                Document doc = getDocument(new Post(XMLParser.ParsePost(line)));
                writer.addDocument(doc);
            } catch (DocumentException e) {
                System.out.println("Parse data from xml wrong. Question Document can't be added at line:" + lineCount);
            } catch (IOException e) {
                System.out.println("Writer add Question documents wrong." + utils.Paths.DECOMPFILESTOREPATH + "python.xml" + " at line " + lineCount);
            }
        }
        questionIn.close();
        System.out.println("Total number of Question documents indexed: " + writer.maxDoc());

        System.out.println("Start Answer Indexing:");

        Scanner answerIn = new Scanner(new File(utils.Paths.DECOMPFILESTOREPATH + "pythonanswer.xml"));

        lineCount = 0;
        while (answerIn.hasNextLine()) {
            lineCount++;
            line = answerIn.nextLine().trim();
            try {
                Document doc = getDocument(new Post(XMLParser.ParsePost(line)));
                writer.addDocument(doc);
            } catch (DocumentException e) {
                System.out.println("Parse data from xml wrong. Answer Document can't be added at line:" + lineCount);
            } catch (IOException e) {
                System.out.println("Writer add Answer documents wrong." + utils.Paths.DECOMPFILESTOREPATH + "pythonanswer.xml" + " at line " + lineCount);
            }
        }
        answerIn.close();
        System.out.println("Total number of Answer documents indexed: " + writer.maxDoc());
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("Question IndexWriter be closed with errors!");
        }
        System.out.println("Index end.");
    }

}
