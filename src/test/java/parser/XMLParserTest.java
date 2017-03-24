package parser;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by str2n on 2017/3/24.
 */
public class XMLParserTest {
    @Test
    public void parsePost() throws Exception {
        System.out.println(XMLParser.ParsePost("<row Id=\"337\" PostTypeId=\"1\" AcceptedAnswerId=\"342\" CreationDate=\"2008-08-02T03:35:55.697\" Score=\"49\" ViewCount=\"6340\" Body=\"&lt;p&gt;I'm about to build a piece of a project that will need to construct and post an XML document to a web service and I'd like to do it in Python, as a means to expand my skills in it.  &lt;/p&gt;&#xA;&#xA;&lt;p&gt;Unfortunately, whilst I know the XML model fairly well in .NET, I'm uncertain what the pros and cons are of the XML models in Python.  &lt;/p&gt;&#xA;&#xA;&lt;p&gt;Anyone have experience doing XML processing in Python? Where would you suggest I start? The XML files I'll be building will be fairly simple.&lt;/p&gt;&#xA;\" OwnerUserId=\"111\" LastEditorUserId=\"1039608\" LastEditDate=\"2012-05-04T09:45:16.460\" LastActivityDate=\"2016-03-25T20:14:43.473\" Title=\"XML Processing in Python\" Tags=\"&lt;python&gt;&lt;xml&gt;\" AnswerCount=\"16\" CommentCount=\"1\" FavoriteCount=\"6\" ClosedDate=\"2016-03-26T01:51:47.153\" />\n").get("ParentId"));
    }

}