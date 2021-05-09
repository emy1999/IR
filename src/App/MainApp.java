package App;

import txtParsing.RelevantAnswersParser;

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        System.out.println("Insert folder's path where the documents are stored (and press enter):");
        Scanner input = new Scanner(System.in);
        String doc_path = input.nextLine();

        System.out.println("Insert folder's path where you want to store the index (and press enter):");
        String index_path = input.nextLine();

        System.out.println("Insert the path to the file \"LISA.QUE\" (and press enter):");
        String query_path = input.nextLine();

        System.out.println("Insert the path to the file \"LISARJ.NUM\" (and press enter):");
        String relevant_path = input.nextLine();

        System.out.println("Insert folder's path where you want to store the formatted relevant answers .test file (and press enter):");
        String answers_formatted = input.nextLine();

        try {
            CreateIndex indexer = new CreateIndex(doc_path, index_path);

            //CustomIndexReader readerDemo = new CustomIndexReader(index_path);

            Searcher searcher = new Searcher(query_path, index_path);

            RelevantAnswersParser rel = new RelevantAnswersParser();
            rel.parse_relevant(relevant_path, answers_formatted);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
