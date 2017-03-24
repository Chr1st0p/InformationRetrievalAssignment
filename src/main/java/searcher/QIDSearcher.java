package searcher;

import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Exact freeTextQuestionSearch the question id
 * Created by str2n on 2017/2/25.
 */
public class QIDSearcher {

    private IndexSearcher QIDSearcher;
    private IndexReader QIDReader;

    public QIDSearcher() {
        try {
            QIDReader = DirectoryReader.open(FSDirectory.open(Paths.get(utils.Paths.QIDINDEXPATH)));
            QIDSearcher = new IndexSearcher(QIDReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int search(int ID) throws IOException {
        Query query = IntPoint.newExactQuery("ID", ID);
        return QIDSearcher.search(query, 10).totalHits;
    }

    public void close(){
        if (QIDReader!=null){
            try {
                QIDReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
