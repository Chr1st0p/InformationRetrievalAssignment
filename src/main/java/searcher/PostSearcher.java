package searcher;

import analyzer.CodeAnalyzer;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import stackoverflow.Answer;
import stackoverflow.Post;
import stackoverflow.PostField;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PostSearcher {

    //    public static List<Question> questions;
    private IndexReader postReader;
    private IndexSearcher postSearcher;

    public PostSearcher() throws IOException {
        postReader = DirectoryReader.open(FSDirectory.open(Paths.get(utils.Paths.POSTINDEXPATH)));
        postSearcher = new IndexSearcher(postReader);

    }

    public void search(String queryStr, int hitNum) throws IOException, ParseException {

        List<Post> posts = new ArrayList<Post>();
        List<Answer> answers;

        Map<String, Analyzer> customizeAnalyzer = new HashMap<>();
        customizeAnalyzer.put("Code", new CodeAnalyzer());
        PerFieldAnalyzerWrapper wrapper = new PerFieldAnalyzerWrapper(new StandardAnalyzer(), customizeAnalyzer);

        MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[]
                {PostField.Body.toString(), PostField.Title.toString(), PostField.Code.toString(), PostField.Tags.toString()}, wrapper);
        Query query = parser.parse(queryStr);

        TopDocs hitdocs = postSearcher.search(query, hitNum);
        for (ScoreDoc doc : hitdocs.scoreDocs) {
            Document document = postSearcher.doc(doc.doc);

            if (document.get(PostField.ParentIdCopy.toString()) == null) {
                int id = Integer.parseInt(document.get(PostField.IdCopy.toString()));
                answers = findAllAnswer(id);
                posts.add(new Post(document, answers));
            } else {
                int parentId = Integer.parseInt(document.get(PostField.ParentIdCopy.toString()));
                if (findPost(parentId) != null) {
                    posts.add(findPost(parentId));
                }
            }
        }
        for (Post p : posts) {
            System.out.print("Question" + posts.indexOf(p) + " ID:" + p.getId());
            System.out.println();
            System.out.print("Question" + posts.indexOf(p) + " Title:" + p.getTitle().replace('\n', ' '));
            System.out.println();
            System.out.print("Question" + posts.indexOf(p) + " Body:" + p.getBody().replace('\n', ' '));
            System.out.println();
            System.out.print("Question" + posts.indexOf(p) + " Code:" + p.getCode().replace('\n', ' '));
            System.out.println();
            System.out.print("Question" + posts.indexOf(p) + " Tags:" + p.getTags().replace('\n', ' '));
            System.out.println();
            if (p.answers != null) {
                for (Answer a : p.answers) {
                    System.out.print("   Answer" + (p.answers.indexOf(a) + 1) + " ID" + a.getId());
                    System.out.println();
                    System.out.print("   Answer" + (p.answers.indexOf(a) + 1) + " Body" + a.getBody().replace('\n', ' '));
                    System.out.println();
                    System.out.print("   Answer" + (p.answers.indexOf(a) + 1) + " Code" + a.getCode().replace('\n', ' '));
                    System.out.println();
                }
            } else {
                System.out.println("No Answers.");
            }
        }
    }

    private List<Answer> findAllAnswer(int parentId) throws IOException {
        List<Answer> answers = new ArrayList<>();

        Query query = IntPoint.newExactQuery(PostField.ParentId.toString(), parentId);
        ScoreDoc[] docs = postSearcher.search(query, 100).scoreDocs;
        if (docs.length != 0) {
            for (ScoreDoc doc : docs) {
                Document document = postSearcher.doc(doc.doc);
                answers.add(new Answer(document));
            }
            return answers;
        } else {
            return null;
        }
    }

    private Post findPost(int questionId) throws IOException {
        List<Answer> answers;
        Query query = IntPoint.newExactQuery(PostField.Id.toString(), questionId);
        ScoreDoc[] docs = postSearcher.search(query, 100).scoreDocs;

        if (docs.length == 1) {
            Document document = postSearcher.doc(docs[0].doc);
            int id = Integer.parseInt(document.get(PostField.IdCopy.toString()));
            answers = findAllAnswer(id);
            return new Post(document, answers);
        } else {
            return null;
        }
    }
}
