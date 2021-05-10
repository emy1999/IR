package txtParsing;

import utils.IO;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class RelevantAnswersParser {
    public RelevantAnswersParser() {
    }

    public void parse_relevant(String path_to_relevant, String path_to_write){
        try {
            File rel_answers;
            FileWriter writer = null;
            try {
                rel_answers = new File(path_to_write+"/rel_results.test");

                writer = new FileWriter(rel_answers);
                if (rel_answers.createNewFile()) {
                    System.out.println("File created: " + rel_answers.getName());
                } else {
                    System.out.println("File already exists.");
                }


                String txt_file = IO.ReadEntireFileIntoAString(path_to_relevant);

                txt_file = txt_file.trim().replaceAll("\\s+", "-");

                String tokens[] = txt_file.split("-");



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

}
