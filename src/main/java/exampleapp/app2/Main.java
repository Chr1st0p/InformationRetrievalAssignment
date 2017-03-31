package exampleapp.app2;


import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.FSDirectory;
import stackoverflow.PostField;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws IOException {
        ReIndex2 indexer = new ReIndex2();
        indexer.ReIndexAnswer();

        IndexReader reIndex2Reader;
        IndexSearcher reIndex2Searcher;

        reIndex2Reader = DirectoryReader.open(FSDirectory.open(Paths.get(utils.Paths.REINDEX2PATH)));
        reIndex2Searcher = new IndexSearcher(reIndex2Reader);

        File result1 = new File(utils.Paths.REINDEX2PATH + "hour.txt");
        FileWriter result1Writer = new FileWriter(result1, true);
        PrintWriter result1PrintWriter = new PrintWriter(result1Writer);

        for (int i = 0; i < 24; i++) {
            Query query = IntPoint.newExactQuery(PostField.CreateHour.toString(), i);
            ScoreDoc[] docs;
            docs = reIndex2Searcher.search(query, 10000000).scoreDocs;
            result1PrintWriter.println("Hour" + i + " Answers Count:" + docs.length);
        }

        result1PrintWriter.flush();
        result1Writer.flush();
        result1PrintWriter.close();
        result1Writer.close();

        File result2 = new File(utils.Paths.REINDEX2PATH + "day.txt");
        FileWriter result2Writer = new FileWriter(result2, true);
        PrintWriter result2PrintWriter = new PrintWriter(result2Writer);

        for (int i = 1; i < 8; i++) {
            Query query = IntPoint.newExactQuery(PostField.CreateDay.toString(), i);
            ScoreDoc[] docs;
            docs = reIndex2Searcher.search(query, 100000000).scoreDocs;
            result2PrintWriter.println("Day" + i + " Answers Count:" + docs.length);
        }

        result2PrintWriter.flush();
        result2Writer.flush();
        result2PrintWriter.close();
        result2Writer.close();
    }
}
