import org.apache.lucene.queryparser.classic.ParseException;
import searcher.PostSearcher;

import java.io.IOException;
import java.util.Scanner;

public class PostSearchMain {

    public static void main(String[] args) throws IOException, ParseException {
        Scanner sc = new Scanner(System.in);

        PostSearcher searcher = new PostSearcher();

        System.out.println("Input your query(\"\" means phrase query):");
        String queryStr = sc.nextLine();

        System.out.println("Select the field that you are going to query(0 means all, 1 means customized fields):");
        int select = sc.nextInt();
        int hitNum;
        switch (select) {
            case 0: {
                System.out.println("Input the result number Top N:");
                hitNum = sc.nextInt();
                searcher.search(queryStr, "0", "", hitNum);
                break;
            }
            case 1: {
                System.out.println("Specify the fields you need to query, 1 means Body(Text), 2 means Title(question), 3 means Code, 4 means Tags(question).");
                String fields = sc.next();

                System.out.println("Input the result number Top N:");
                hitNum = sc.nextInt();
                searcher.search(queryStr, "1", fields, hitNum);
                break;
            }
        }
    }
}
