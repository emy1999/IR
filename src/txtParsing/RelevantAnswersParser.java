package txtParsing;

import utils.IO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RelevantAnswersParser {

    private static void parse_relevant(){
        try {
            File rel_answers;
            FileWriter writer = null;
            try {
                //rel_answers = new File("C:\\Users\\elena\\Desktop\\ir_local2\\src\\rel_answers_formatted.txt");
                rel_answers = new File("rel_results.test");
                writer = new FileWriter(rel_answers);
                if (rel_answers.createNewFile()) {
                    System.out.println("File created: " + rel_answers.getName());
                } else {
                    System.out.println("File already exists.");
                }


                //String txt_file = IO.ReadEntireFileIntoAString("LISA-Relevant/LISARJ.NUM");
                String txt_file = IO.ReadEntireFileIntoAString("/Users/emiliadan/Downloads/GitLab/ir/LISA-Relevant/LISARJ.NUM");
                txt_file = txt_file.trim().replaceAll("\\s+", "-");

                String tokens[] = txt_file.split("-");

                System.out.println(txt_file);
                System.out.println(txt_file.length());

                int i = 0;
                while (i != tokens.length) {

                    String query_id = tokens[i];
                    int number_of_docs = Integer.parseInt(tokens[i + 1]);

                    for (int n = 0; n < number_of_docs; n++) {


                        writer.write(query_id + "       " + 0 + "       " + tokens[i + 2 + n] + "    " + "1");
                        System.out.println("\tQueryID " + query_id + "\tRelevant Doc = " + tokens[i + 2 + n]);
                        writer.write("\n");
                    }
                    i += 2;
                    i += number_of_docs;
                }

            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main (String args[]){
        parse_relevant();
    }
}
