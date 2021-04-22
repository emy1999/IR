package txtParsing;

import utils.IO;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;
import static java.lang.Character.isWhitespace;

public class TXTParsing {

    public static List<MyDoc> parse(String file) throws Exception {
        try{
            //Parse txt file
            String txt_file = IO.ReadEntireFileIntoAString(file);
            String[] docs = txt_file.split("\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*");
            System.out.println("Read: "+docs.length + " docs");

            //Parse each document from the txt file
            List<MyDoc> parsed_docs= new ArrayList<MyDoc>();

            for (String doc:docs){
                if(!doc.equals("\r\n")) {
                    String[] adoc = new String[3];

                    int title_end = doc.indexOf("\r\n");
                    adoc[0] = doc.substring(0, title_end);

                    int summary_start = title_end + 2;
                    int summary_end = doc.indexOf("\r\n\r\n", summary_start);
                    adoc[1] = doc.substring(summary_start, summary_end);

                    int body_start = summary_end + 4;
                    adoc[2] = doc.substring(body_start);

                    MyDoc mydoc = new MyDoc(adoc[0], adoc[1], adoc[2]);
                    parsed_docs.add(mydoc);
                }
            }

            return parsed_docs;
        } catch (Throwable err) {
            err.printStackTrace();
            return null;
        }

    }

}
