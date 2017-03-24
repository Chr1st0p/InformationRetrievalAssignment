package filter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by str2n on 2017/3/20.
 */
public class QuestionFilterTest {
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void countByTag() throws Exception {
        QuestionFilter.CountByTag("Posts.xml");
    }

    @Test
    public void filterQuesByTag() throws Exception {
        QuestionFilter.FilterQuesByTag("Posts.xml");
    }

}