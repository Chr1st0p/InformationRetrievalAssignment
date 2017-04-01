package exampleapp.app2;


import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.dom4j.DocumentException;
import parser.XMLParser;
import stackoverflow.Post;
import stackoverflow.PostField;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReIndex2 {
    private IndexWriter writer = null;

    public ReIndex2() throws IOException {
        Directory reIndexDir = FSDirectory.open(Paths.get(utils.Paths.REINDEX2PATH));


        IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);

        try {
            writer = new IndexWriter(reIndexDir, config);
        } catch (IOException e) {
            System.out.println("Answer IndexWriter initialize Error.");
        }
    }

    private static Document getDocument(Post p) throws ParseException {

        Document doc = new Document();

        doc.add(new IntPoint(PostField.CreateHour.toString(), getHour(p.getCreationDate())));
        doc.add(new IntPoint(PostField.CreateDay.toString(), getDay(p.getCreationDate())));

        return doc;
    }

    public void ReIndexAnswer() throws FileNotFoundException {
        System.out.println("Start Example02 Answer Indexing:");

        Scanner answerIn = new Scanner(new File(utils.Paths.FILTEREDFILEPATH + "pythonanswer.xml"));
        String line;
        int lineCount = 0;
        while (answerIn.hasNextLine()) {
            lineCount++;
            line = answerIn.nextLine().trim();
            try {
                Document doc = getDocument(new Post(XMLParser.ParsePost(line)));
                writer.addDocument(doc);
            } catch (DocumentException e) {
                System.out.println("Parse data from pythonanswer.xml wrong. Answer Document can't be added at line:" + lineCount);
            } catch (IOException e) {
                System.out.println("Writer add  documents wrong." + utils.Paths.FILTEREDFILEPATH + "pythonanswer.xml" + " at line " + lineCount);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        answerIn.close();
        System.out.println("Total number of Answer documents indexed: " + writer.maxDoc());
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("Answer IndexWriter be closed with errors!");
        }
        System.out.println("Index end.");
    }

    static int getHour(String dateStr) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Date date = sdf.parse(dateStr);
        return date.getHours();
    }

    static int getDay(String dateStr) throws ParseException {
        List<String> days = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        Date date = sdf.parse(dateStr);
        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE", Locale.ENGLISH);

        return days.indexOf(dateFm.format(date)) + 1;
    }
}
