package App;

import txtParsing.MyQuery;
import utils.IO;

import java.util.ArrayList;
import java.util.List;

public class QueryParsing {

    public static List<MyQuery> queryParsing(String pathfile) throws  Exception{
        try{
            StringBuffer txt_file = IO.ReadFileIntoAStringLineByLine(pathfile);

            String []tokens = txt_file.toString().split("#");

            List<MyQuery> parsed_queries = new ArrayList<MyQuery>();

            for(int i = 0; i < tokens.length - 1; i++) {
                //System.out.println(i);

                tokens[i] = tokens[i].trim();
                int temp = tokens[i].trim().indexOf("\n");

                //System.out.println(tokens[i].substring(0, temp));
                String ID = tokens[i].substring(0, temp);
                //System.out.println(ID);

                int pos = tokens[i].trim().indexOf("\n") + 1;
                //System.out.println(pos);

                String body = tokens[i].substring(pos, tokens[i].length() - 1);

                MyQuery query = new MyQuery(ID, body);
                parsed_queries.add(query);


            }
            System.out.println(parsed_queries);

            return parsed_queries;


        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
       try {
           //queryParsing("/Users/emiliadan/Downloads/Anaktisi/LISA.QUE");
           queryParsing("LISA/LISA.QUE");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
