import indexer.PostIndexer;

import java.io.IOException;

public class PostIndexMain {
    public static void main(String[] args) throws IOException {
        PostIndexer indexer = new PostIndexer();
        indexer.IndexPost();
    }
}
