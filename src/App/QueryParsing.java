package App;

import txtParsing.MyQuery;
import utils.IO;

import java.util.ArrayList;
import java.util.List;

public class QueryParsing {

    public static List<MyQuery> queryParsing(String pathfile) throws Exception {
        try {
            StringBuffer txt_file = IO.ReadFileIntoAStringLineByLine(pathfile);

            String[] tokens = txt_file.toString().split("#");

            List<MyQuery> parsed_queries = new ArrayList<MyQuery>();

            for (int i = 0; i < tokens.length - 1; i++) {

                tokens[i] = tokens[i].trim();

                int temp = tokens[i].trim().indexOf("\n");

                String ID = tokens[i].substring(0, temp);

                int pos = tokens[i].trim().indexOf("\n") + 1;

                String body = tokens[i].substring(pos, tokens[i].length() - 1);

                MyQuery query = new MyQuery(ID, body);
                parsed_queries.add(query);

            }
            System.out.println(parsed_queries);

            return parsed_queries;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
