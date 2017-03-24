package stackoverflow;

import org.apache.lucene.document.Document;

import java.util.List;
import java.util.Map;

/**
 * Created by str2n on 2017/3/24.
 */
public class Post {

    private int id;
    private String creationDate;
    private String body;
    private String code;
    private int score;
    //  Only Question
    private String title;
    private int answerCount;
    private int viewCount;

    //  Only some questions
    private int acceptedAnswerId;
    private String tags;

    //  Only answer
    private int parentId;

    public List<Answer> answers;
    public double searchScore;

    public Post(Map<String, String> post) {
        id = Integer.parseInt(post.get(PostField.Id.toString()));
        score = Integer.parseInt(post.get(PostField.Score.toString()));
        creationDate = post.get(PostField.CreationDate.toString());
        body = post.get(PostField.Body.toString());
        if (post.get(PostField.ParentId.toString()) != null) {
            parentId = Integer.parseInt(post.get(PostField.ParentId.toString()));
        } else {
            parentId = 0;
            title = post.get(PostField.Title.toString());
            answerCount = Integer.parseInt(post.get(PostField.AnswerCount.toString()));
            viewCount = Integer.parseInt(post.get(PostField.ViewCount.toString()));
            if (post.get(PostField.AcceptedAnswerId.toString()) != null) {
                acceptedAnswerId = Integer.parseInt(post.get(PostField.AcceptedAnswerId.toString()));
            } else {
                acceptedAnswerId = 0;
            }
            if (post.get(PostField.Tags.toString()) != null) {
                tags = post.get(PostField.Tags.toString());
            }
        }
    }

    public Post(Document doc, List<Answer> answerList, double searchscore) {
        id = Integer.parseInt(doc.get(PostField.IdCopy.toString()));
        score = Integer.parseInt(doc.get(PostField.Score.toString()));
        creationDate = doc.get(PostField.CreationDate.toString());
        body = doc.get(PostField.Body.toString());
        code = doc.get(PostField.Code.toString());
        title = doc.get(PostField.Title.toString());
        answerCount = Integer.parseInt(doc.get(PostField.AnswerCount.toString()));
        viewCount = Integer.parseInt(doc.get(PostField.ViewCount.toString()));

        if (doc.get(PostField.AcceptedAnswerId.toString()) != null) {
            acceptedAnswerId = Integer.parseInt(doc.get(PostField.AcceptedAnswerId.toString()));
        } else {
            acceptedAnswerId = 0;
        }
        if (doc.get(PostField.Tags.toString()) != null) {
            tags = doc.get(PostField.Tags.toString());
        }
        answers = answerList;
        searchScore = searchscore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAcceptedAnswerId() {
        return acceptedAnswerId;
    }

    public void setAcceptedAnswerId(int acceptedAnswerId) {
        this.acceptedAnswerId = acceptedAnswerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getAnswerCount() {
        return answerCount;
    }

    public void setAnswerCount(int answerCount) {
        this.answerCount = answerCount;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }
}
