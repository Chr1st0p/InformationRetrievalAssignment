package parser;

import org.dom4j.*;
import stackoverflow.PostField;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class XMLParser {

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
