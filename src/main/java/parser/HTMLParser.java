package parser;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by str2n on 2017/3/2.
 */
public class HTMLParser {
    public static String ParseCode(String body){
        Document doc = Jsoup.parse(body);
        return doc.select("pre").text();
    }

    public static String ParseText(String body){
        Document doc = Jsoup.parse(body);
        return doc.select("p").text();
    }
}
