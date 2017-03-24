package parser;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by str2n on 2017/3/2.
 */
public class HTMLParser {
    public static String ParseCode(String body) {
        Document doc = Jsoup.parse(body);
        return doc.select("pre").text();
    }

    public static String ParseText(String body) {
        Document doc = Jsoup.parse(body);
        return doc.select("p").text();
    }

    public static String ParseTag(String tags) {
        Pattern p = Pattern.compile("<(.*?)>");
        Matcher m = p.matcher(tags);
        StringBuilder tag = new StringBuilder();
        while (m.find()) {
            tag.append(m.group(1)).append(" ");
        }
        return tag.toString().trim();
    }
}
