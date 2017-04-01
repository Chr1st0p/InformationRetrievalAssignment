package indexer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.dom4j.Attribute;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * To index Question ID in order to filter corresponding answers
 * Created by Fang Mingmin on 2017/2/25.
 */
public class QIDIndexer {

    private IndexWriter writer = null;

    public QIDIndexer() throws IOException {

        Directory QIdIndexDir = null;

        QIdIndexDir = FSDirectory.open(Paths.get(utils.Paths.QIDINDEXPATH));


        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

        try {
            writer = new IndexWriter(QIdIndexDir, config);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Document getDocument(int qid) {
        Document doc = new Document();
        doc.add(new IntPoint("ID", qid));
        return doc;
    }

    public void IndexQueID() throws FileNotFoundException {

        System.out.println("Start question Id indexing");

        Scanner in = new Scanner(new File(utils.Paths.FILTEREDFILEPATH + "python.xml"));

        String line;
        int lineCount = 0;

        while (in.hasNextLine()) {
            lineCount++;
            line = in.nextLine().trim();
            try {
                org.dom4j.Document XMLDoc = DocumentHelper.parseText(line);
                Element root = XMLDoc.getRootElement();
                Attribute IDAttr = root.attribute("Id");
                int qID = Integer.parseInt(IDAttr.getText());

                Document doc = getDocument(qID);
                writer.addDocument(doc);
            } catch (DocumentException e) {
                System.out.println("Error at Line:" + lineCount);
            } catch (IOException e) {
                System.out.println("can not write index at Line" + lineCount);
            }
        }
        in.close();

        System.out.println("Total number of documents indexed: " + writer.maxDoc());

        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("IndexWriter be closed with errors!");
        }
        System.out.println("Question Id indexing ending.");
    }
}
