package utils;

import stackoverflow.Post;

import java.util.Comparator;

/**
 * Created by str2n on 2017/3/24.
 */
public class PostComparator implements Comparator<Post> {
    @Override
    public int compare(Post post, Post t1) {
        return new Double(t1.searchScore).compareTo(post.searchScore);
    }
}
