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
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import stackoverflow.Answer;
import stackoverflow.Post;
import stackoverflow.PostField;
import utils.PostComparator;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;


public class PostSearcher {

    private IndexReader postReader;
    private IndexSearcher postSearcher;

    public PostSearcher() throws IOException {
        postReader = DirectoryReader.open(FSDirectory.open(Paths.get(utils.Paths.POSTINDEXPATH)));
        postSearcher = new IndexSearcher(postReader);

    }

    public void search(String queryStr, int hitNum) throws IOException, ParseException {

        Set<Post> posts = new TreeSet<>(new PostComparator());
        List<Answer> answers;
        //  Analyzer customization
        Map<String, Analyzer> customizeAnalyzer = new HashMap<>();
        customizeAnalyzer.put("Code", new CodeAnalyzer());
        PerFieldAnalyzerWrapper wrapper = new PerFieldAnalyzerWrapper(new StandardAnalyzer(), customizeAnalyzer);
        //  Weight customization
        Map<String, Float> boosts = new HashMap<>();
        boosts.put(PostField.Body.toString(), 1.0f);
        boosts.put(PostField.Title.toString(), 1.0f);
        boosts.put(PostField.Code.toString(), 1.0f);
        boosts.put(PostField.Tags.toString(), 1.0f);
        //  Add customized analyzer and weight to QueryParser
        MultiFieldQueryParser parser = new MultiFieldQueryParser(new String[]
                {PostField.Body.toString(), PostField.Title.toString(), PostField.Code.toString(), PostField.Tags.toString()}, wrapper, boosts);
        parser.setDefaultOperator(QueryParser.Operator.AND);
        Query query = parser.parse(queryStr);

        TopDocs hitDocs = postSearcher.search(query, hitNum);

        for (ScoreDoc doc : hitDocs.scoreDocs) {
            Document document = postSearcher.doc(doc.doc);

            if (document.get(PostField.ParentIdCopy.toString()) == null) {

                int id = Integer.parseInt(document.get(PostField.IdCopy.toString()));
                answers = findAllAnswer(id);
                posts.add(new Post(document, answers, (double) doc.score));
            } else {
                int parentId = Integer.parseInt(document.get(PostField.ParentIdCopy.toString()));
                if (findPost(parentId, doc.score) != null) {
                    posts.add(findPost(parentId, (double) doc.score));
                }
            }
        }
        postReader.close();

        int i = 0;
        if (posts.size() != 0) {
            for (Post p : posts) {
                i++;
                System.out.println("Question" + i + " ID:" + p.getId());
                System.out.println("Question" + i + " search Score:" + p.searchScore);
                System.out.println("Question" + i + " Title:" + p.getTitle().replace('\n', ' '));
                System.out.println("Question" + i + " Body:" + p.getBody().replace('\n', ' '));
                System.out.println("Question" + i + " Code:" + p.getCode().replace('\n', ' '));
                System.out.println("Question" + i + " Tags:" + p.getTags().replace('\n', ' '));
                if (p.answers != null) {
                    for (Answer a : p.answers) {
                        System.out.println("   Answer" + (p.answers.indexOf(a) + 1) + " ID:" + a.getId());
                        System.out.println("   Answer" + (p.answers.indexOf(a) + 1) + " Body:" + a.getBody().replace('\n', ' '));
                        System.out.println("   Answer" + (p.answers.indexOf(a) + 1) + " Code:" + a.getCode().replace('\n', ' '));
                    }
                } else {
                    System.out.println("No Answers.");
                }
            }
        } else {
            System.out.println("No Posts matching.");
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

    private Post findPost(int questionId, double score) throws IOException {
        List<Answer> answers;
        Query query = IntPoint.newExactQuery(PostField.Id.toString(), questionId);
        ScoreDoc[] docs = postSearcher.search(query, 100).scoreDocs;

        if (docs.length == 1) {
            Document document = postSearcher.doc(docs[0].doc);
            int id = Integer.parseInt(document.get(PostField.IdCopy.toString()));
            answers = findAllAnswer(id);
            return new Post(document, answers, score);
        } else {
            return null;
        }
    }
}
