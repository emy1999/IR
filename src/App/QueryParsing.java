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

            for(int i = 0; i <=  tokens.length - 1; i++) {

                if(!tokens[i].equals("\n")) {
                    int newLine = tokens[i].trim().indexOf("\n");
                    String ID = tokens[i].substring(0, newLine);
                    String body = tokens[i].substring(newLine + 1);

                    MyQuery query = new MyQuery(ID, body);

                    parsed_queries.add(query);
                    //System.out.println(query);
                }
            }
            return parsed_queries;


        }catch(Exception e) {
            e.printStackTrace();
            return  null;
        }
    }

//    public static void main(String[] args) {
//        try {
//            queryParsing("LISA/LISA.QUE");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }

}
