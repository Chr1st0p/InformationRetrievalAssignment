package stackoverflow;

import org.apache.lucene.document.Document;
import org.jetbrains.annotations.NotNull;


public class Answer implements Comparable {

    private int id;
    private int parentId;
    private String creationDate;
    private int score;
    private String body;
    private String code;

    public Answer(Document doc) {
        id = Integer.parseInt(doc.get(PostField.IdCopy.toString()));
        parentId = Integer.parseInt(doc.get(PostField.ParentIdCopy.toString()));
        creationDate = doc.get(PostField.CreationDate.toString());
        score = Integer.parseInt(doc.get(PostField.Score.toString()));
        body = doc.get(PostField.Body.toString());
        code = doc.get(PostField.Code.toString());
    }

    public int getId() {
        return id;
    }

    public int getParentId() {
        return parentId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public int getScore() {
        return score;
    }

    public String getBody() {
        return body;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int compareTo(@NotNull Object o) {

        Answer ans = (Answer) o;

        Integer obj1 = this.getScore();
        Integer obj2 = ans.getScore();

        return obj2.compareTo(obj1);
    }
}
