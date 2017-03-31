package filter;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import searcher.QIDSearcher;
import utils.Paths;

import java.io.*;
import java.util.Scanner;

public class AnswerFilter {

    public static void FilterAnswerByQIdIndex() throws FileNotFoundException {
        System.out.println("Start Read File:");

        Scanner in = new Scanner(new File(Paths.POSTSPATH + "Posts.xml"));

        int lineCount = 0;
        String singleLine;

        FileWriter writer = null;
        try {
            File ansFile = new File(Paths.FILTEREDFILEPATH + "pythonanswer.xml");

            writer = new FileWriter(ansFile, true);

        } catch (IOException e) {
            e.printStackTrace();
        }

        assert writer != null : "Print Writer is NULL";
        PrintWriter pWriter = new PrintWriter(writer);

        QIDSearcher searcher = new QIDSearcher();

        while (in.hasNextLine()) {
            try {
                singleLine = in.nextLine().trim();
                if (lineCount >= 2) {
                    Document document = DocumentHelper.parseText(singleLine);
                    Element root = document.getRootElement();
                    int IdType;
                    int parentId;
                    try {
                        Attribute ID = root.attribute("PostTypeId");
                        IdType = Integer.parseInt(ID.getText());
                        if (IdType == 2) {
                            Attribute pId = root.attribute("ParentId");
                            parentId = Integer.parseInt(pId.getText());
                            if (searcher.search(parentId) == 1) {
                                pWriter.println(singleLine);
                            }
                        }
                    } catch (Exception e) {
//                        System.out.println("No Tags");
                    }
                }
                lineCount++;
            } catch (Exception e) {
//                System.out.println("Error occur at Line:" + lineCount);
            }
        }
        in.close();
        searcher.close();
        try {
            pWriter.flush();
            writer.flush();
            pWriter.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
