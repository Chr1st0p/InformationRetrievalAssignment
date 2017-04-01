import filter.AnswerFilter;
import filter.QuestionFilter;
import indexer.QIDIndexer;

import java.io.IOException;

/**
 * Created by Fang Mingmin on 2017/3/20.
 */
public class FilterPythonMain {
    public static void main(String[] args) throws IOException {

        // Filter question with tag "python". And store it in a single file "python.xml"
        QuestionFilter.FilterQuesByTag();

        // Create index on question id with "python.xml" for filtering answers associated with python question
        QIDIndexer indexer = new QIDIndexer();
        indexer.IndexQueID();

        //Filter answers associated with python question and create "pythonanswer.xml" to store it
        AnswerFilter.FilterAnswerByQIdIndex();

    }
}
