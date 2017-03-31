package searcher;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


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
        searcher.search("\"python numpy\"", "0", "", 20);
//        searcher.search("python numpy append array  axis", "0", 100);
    }

}