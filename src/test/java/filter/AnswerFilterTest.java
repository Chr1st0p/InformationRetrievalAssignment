package filter;

import indexer.QIDIndexer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by str2n on 2017/3/20.
 */
public class AnswerFilterTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void filterAnswerByQIdIndex() throws Exception {
        QIDIndexer indexer = new QIDIndexer();
        indexer.IndexQueID("python.xml");
        AnswerFilter.FilterAnswerByQIdIndex("Posts.xml", "python");
    }

}