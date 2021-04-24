package myLuceneApp;

import utils.IO;

import java.util.ArrayList;

public class QueryParser {

    public QueryParser(){
        try{
            String pathfile =  "/Users/emiliadan/Downloads/Anaktisi/LISA.QUE";
            StringBuffer txt_file = IO.ReadFileIntoAStringLineByLine(pathfile);

            String []tokens = txt_file.toString().split("#");
            
            ArrayList<String> queries = new ArrayList<>();
            int pos = 0;

            for(int i = 0; i <=  tokens.length - 1; i++) {

                pos = tokens[i].trim().indexOf("\n") + 1;

                if (pos != -1) {

                    queries.add(tokens[i].substring(pos, tokens[i].length() - 1));

                }
            }


        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            QueryParser parser = new QueryParser();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
