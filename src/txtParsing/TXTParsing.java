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
            String[] docs = txt_file.split("\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\\*\r\n");
            System.out.println("Read: "+docs.length + " docs");

            //Parse each document from the txt file
            List<MyDoc> parsed_docs= new ArrayList<MyDoc>();

            for (String doc:docs){
                if(!doc.equals("\r\n")) {
                    String[] adoc = new String[3];


                    int id_end = doc.indexOf("\r\n");

                    adoc[0] = doc.substring(8, id_end);

                    int title_start = id_end + 2;

                    int title_end = doc.indexOf(".", title_start);
                    adoc[1] = doc.substring(title_start, title_end+1);

                    int body_start = title_end+1;
                    while (isWhitespace(doc.charAt(body_start))){
                        body_start++;
                    }
                    adoc[2] = doc.substring(body_start).trim();

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
