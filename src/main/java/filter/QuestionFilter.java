package filter;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jsoup.Jsoup;
import utils.Paths;

import java.io.*;
import java.util.Scanner;

/**
 * Used for depart question posts from the such big file posts.xml
 * Created by str2n on 2017/2/25.
 */
public class QuestionFilter {

    public static void CountByTag(String fileName) throws FileNotFoundException {

        System.out.println("Start Read All Posts File:");

        Scanner in = new Scanner(new File(Paths.POSTSPATH + fileName));

        int lineCount = 0;
        String singleLine;

        int csharpCount = 0;
        int javaCount = 0;
        int cplusCount = 0;
        int pythonCount = 0;
        int javascriptCount = 0;
        int htmlCount = 0;

        while (lineCount<=10) {
            try {
                singleLine = in.nextLine().trim();

                if (lineCount >= 2) {
                    Document document = DocumentHelper.parseText(singleLine);
                    Element root = document.getRootElement();
                    String tag = "";
                    try {
                        Attribute ID = root.attribute("Tags");
                        Attribute body = root.attribute("Body");
                        System.out.println(body.getText());
                        org.jsoup.nodes.Document doc = Jsoup.parse(body.getText());
                        System.out.println(doc.select("pre").text());
                        System.out.println(doc.select("p").text());
                        tag = ID.getText();
                    } catch (Exception e) {
//                        System.out.println("No Tags");
                    }
                    if (tag.contains("c#"))
                        csharpCount++;
                    if (tag.contains("java") && !tag.contains("javascript"))
                        javaCount++;
                    if (tag.contains("c++"))
                        cplusCount++;
                    if (tag.contains("python"))
                        pythonCount++;
                    if (tag.contains("javascript"))
                        javascriptCount++;
                    if (tag.contains("html"))
                        htmlCount++;
                }
                lineCount++;
            } catch (Exception e) {
//                System.out.println("Error occur at Line:" + lineCount);
            }
        }
        in.close();

        FileWriter fw = null;
        try {
            File f = new File(System.getProperty("user.dir")+"count.txt");
            fw = new FileWriter(f, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PrintWriter pw = new PrintWriter(fw);
        pw.println("Java: " + javaCount + " Items.");
        pw.println("C# " + csharpCount + " Items.");
        pw.println("C++ " + cplusCount + " Items.");
        pw.println("Python " + pythonCount + " Items.");
        pw.println("Javascript " + javascriptCount + "Items.");
        pw.println("HTML " + htmlCount + "Items.");
        pw.println(lineCount + " Lines");
        pw.flush();

        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void FilterQuesByTag(String filename) throws FileNotFoundException {
        System.out.println("Start Read File:");

        Scanner in = new Scanner(new File(Paths.POSTSPATH + filename));

        int lineCount = 0;
        String singleLine;

        FileWriter CSharpWriter = null;
        FileWriter JavaWriter = null;
        FileWriter CPlusPlusWriter = null;
        FileWriter PythonWriter = null;
        FileWriter JavascriptWriter = null;
        FileWriter HtmlWriter = null;
        try {
            File CSharpFile = new File(Paths.DECOMPFILESTOREPATH + "c#.xml");
            File JavaFile = new File(Paths.DECOMPFILESTOREPATH + "java.xml");
            File CPlusPlusFile = new File(Paths.DECOMPFILESTOREPATH + "c++.xml");
            File PythonFile = new File(Paths.DECOMPFILESTOREPATH + "python.xml");
            File JavascriptFile = new File(Paths.DECOMPFILESTOREPATH + "javascript.xml");
            File HtmlFile = new File(Paths.DECOMPFILESTOREPATH + "html.xml");

            CSharpWriter = new FileWriter(CSharpFile, true);
            JavaWriter = new FileWriter(JavaFile, true);
            CPlusPlusWriter = new FileWriter(CPlusPlusFile, true);
            PythonWriter = new FileWriter(PythonFile, true);
            JavascriptWriter = new FileWriter(JavascriptFile, true);
            HtmlWriter = new FileWriter(HtmlFile, true);

        } catch (IOException e) {
            e.printStackTrace();
        }

        assert CSharpWriter != null : "C# Print writer is null";
        assert JavaWriter != null : "Java Print writer is null";
        assert CPlusPlusWriter != null : "C++ Print writer is null";
        assert PythonWriter != null : "Python Print writer is null";
        assert JavascriptWriter != null : "Javascript Print writer is null";
        assert HtmlWriter != null : "HTML Print writer is null";

        PrintWriter CSpw = new PrintWriter(CSharpWriter);
        PrintWriter Javapw = new PrintWriter(JavaWriter);
        PrintWriter CPpw = new PrintWriter(CPlusPlusWriter);
        PrintWriter Pythonpw = new PrintWriter(PythonWriter);
        PrintWriter JSpw = new PrintWriter(JavascriptWriter);
        PrintWriter Htmlpw = new PrintWriter(HtmlWriter);

        while (in.hasNextLine()) {
            try {
                singleLine = in.nextLine().trim();

                if (lineCount >= 2) {
                    Document document = DocumentHelper.parseText(singleLine);
                    Element root = document.getRootElement();
                    String tag = "";
                    try {
                        Attribute ID = root.attribute("Tags");
                        tag = ID.getText();
                    } catch (Exception e) {
//                        System.out.println("No Tags");
                    }
                    if (tag.contains("c#"))
                        CSpw.println(singleLine);
                    if (tag.contains("java") && !tag.contains("javascript"))
                        Javapw.println(singleLine);
                    if (tag.contains("c++"))
                        CPpw.println(singleLine);
                    if (tag.contains("python"))
                        Pythonpw.println(singleLine);
                    if (tag.contains("javascript"))
                        JSpw.println(singleLine);
                    if (tag.contains("html"))
                        Htmlpw.println(singleLine);
                }
                lineCount++;
            } catch (Exception e) {
//                System.out.println("Error occur at Line:" + lineCount);
            }
        }
        in.close();

        CSpw.flush();
        Javapw.flush();
        CPpw.flush();
        Pythonpw.flush();
        JSpw.flush();
        Htmlpw.flush();

        try {
            CSharpWriter.flush();
            JavaWriter.flush();
            CPlusPlusWriter.flush();
            PythonWriter.flush();
            JavascriptWriter.flush();
            HtmlWriter.flush();

            CSharpWriter.close();
            JavaWriter.close();
            CPlusPlusWriter.close();
            PythonWriter.close();
            JavascriptWriter.close();
            HtmlWriter.close();

            CSpw.close();
            Javapw.close();
            CPpw.close();
            Pythonpw.close();
            JSpw.close();
            Htmlpw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
