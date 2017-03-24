package parser;

import org.dom4j.*;
import stackoverflow.PostField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class XMLParser {

    public static Map<String, String> ParseQuestion(String post) throws DocumentException {

        Document doc = DocumentHelper.parseText(post);
        Element root = doc.getRootElement();
        String answerid;
        //To handle AcceptedAnswerId might missing
        try {
            Attribute acceptedanswerid = root.attribute(PostField.AcceptedAnswerId.toString());
            answerid = acceptedanswerid.getText();
        } catch (Exception e) {
            answerid = "0";
        }
        Map<String, String> question = new HashMap<>();
        question.put(PostField.Id.toString(), root.attribute(PostField.Id.toString()).getText());
        question.put(PostField.AcceptedAnswerId.toString(), answerid);
        question.put(PostField.Title.toString(), root.attribute(PostField.Title.toString()).getText());
        question.put(PostField.CreationDate.toString(), root.attribute(PostField.CreationDate.toString()).getText());
        question.put(PostField.Body.toString(), root.attribute(PostField.Body.toString()).getText());
        question.put(PostField.Tags.toString(), root.attribute(PostField.Tags.toString()).getText());
        question.put(PostField.AnswerCount.toString(), root.attribute(PostField.AnswerCount.toString()).getText());
        question.put(PostField.ViewCount.toString(), root.attribute(PostField.ViewCount.toString()).getText());
        question.put(PostField.Score.toString(), root.attribute(PostField.Score.toString()).getText());

        return question;
    }

    public static Map<String, String> ParseAnswer(String post) throws DocumentException {

        Document doc = DocumentHelper.parseText(post);
        Element root = doc.getRootElement();

        Map<String, String> answer = new HashMap<>();
        answer.put(PostField.Id.toString(), root.attribute(PostField.Id.toString()).getText());
        answer.put(PostField.ParentId.toString(), root.attribute(PostField.ParentId.toString()).getText());
        answer.put(PostField.CreationDate.toString(), root.attribute(PostField.CreationDate.toString()).getText());
        answer.put(PostField.Score.toString(), root.attribute(PostField.Score.toString()).getText());
        answer.put(PostField.Body.toString(), root.attribute(PostField.Body.toString()).getText());

        return answer;
    }

    public static Map<String, String> ParsePost(String post) throws DocumentException {

        Document doc = DocumentHelper.parseText(post);
        Element root = doc.getRootElement();
        List<Attribute> listAttr = root.attributes();
        Map<String, String> posts = new HashMap<>();
        for (Attribute attr:listAttr){
            posts.put(attr.getName(),attr.getValue());
        }
        return posts;
    }

}
