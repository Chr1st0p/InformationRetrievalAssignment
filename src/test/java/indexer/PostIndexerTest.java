package indexer;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by str2n on 2017/3/24.
 */
public class PostIndexerTest {
    private PostIndexer indexer;
    @Before
    public void setUp() throws Exception {
        indexer = new PostIndexer();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void indexPost() throws Exception {
        indexer.IndexPost();
    }

}