package searcher;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import stackoverflow.Post;

import static org.junit.Assert.*;

/**
 * Created by str2n on 2017/3/24.
 */
public class PostSearcherTest {
    private PostSearcher searcher;

    @Before
    public void setUp() throws Exception {
        searcher = new PostSearcher();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void search() throws Exception {
        searcher.search("pandas python", 10);
    }

}