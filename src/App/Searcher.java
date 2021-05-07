package App;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.store.FSDirectory;
import txtParsing.MyQuery;

import static App.QueryParsing.queryParsing;


public class Searcher {

    //String query_path;

    public Searcher(String index_path, String query_path) {
        try {
            //this.query_path = query_path;

            // String indexLocation = (index_path); //define where the index is stored
            String field = "contents"; //define which field will be searched

            //Access the index using indexReaderFSDirectory.open(Paths.get(index_path))
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(index_path))); //CustomIndexReader is an abstract class, providing an interface for accessing an index.
            IndexSearcher indexSearcher = new IndexSearcher(indexReader); //Creates a searcher searching the provided index, Implements search over a single CustomIndexReader.
            indexSearcher.setSimilarity(new ClassicSimilarity());

            //Search the index using indexSearcher
            search(indexSearcher, field,query_path);

            //Close indexReader
            indexReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Searches the index given a specific user query.
     */
    private void search(IndexSearcher indexSearcher, String field, String query_path) {
        try {
            // define which analyzer to use for the normalization of user's query
            Analyzer analyzer = new EnglishAnalyzer();

            // create a query parser on the field "contents"
            QueryParser parser = new QueryParser(field, analyzer);

            System.out.println(" Available queries: ");

            List<MyQuery> available_queries = queryParsing(query_path);

            int groups[] = {20, 30, 50,0};

            for (int n = 0; n < groups.length; n++) {
                File results_file;
                FileWriter writer = null;
                try {
                    if ( n != 3) {
                        results_file = new File("our_results" + "_k=" + groups[n] + ".txt");
                    }else{
                        results_file = new File("our_results.txt");
                    }

                    writer = new FileWriter(results_file);
                    if (results_file.createNewFile()) {
                        System.out.println("File created: " + results_file.getName());
                    } else {
                        System.out.println("File already exists.");
                    }

                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                //This line is not necessary, will be deleted later
                //writer.write("q_id" + "    " + "iter" + "    " + "docno" + "    " + "rank" + "    " + "sim" + "    " + "run_id");

                for (int i = 0; i < available_queries.size(); i++) {
                    String query_body = available_queries.get(i).getQuery_body();

                    // parse the query according to QueryParser
                    Query query = parser.parse(query_body);
                    System.out.println("Searching for: " + query.toString(field));

                    // search the index using the indexSearcher
                    TopDocs results = indexSearcher.search(query, 100);
                    ScoreDoc[] hits = results.scoreDocs;
                    long numTotalHits = results.totalHits;

                    System.out.println(numTotalHits + " total matching documents");


                    //display results
                    if (n != 3) {
                        for (int y = 0; y < groups[n]; y++) {
                            if (y < numTotalHits) { //in case there are not 20 hits

                                Document hitDoc = indexSearcher.doc(hits[y].doc);

                                writer.write(available_queries.get(i).getQuery_id() + "       " + 0 + "       " + hitDoc.get("ID") + "    " + "0" + "    " + hits[y].score + "    " + "Lucene");
                                System.out.println("\tScore " + hits[y].score + "\tID =" + hitDoc.get("ID"));
                                writer.write("\n");

                            }
                        }

                    }else{
                    for (int y = 0; y < hits.length; y++) {


                            Document hitDoc = indexSearcher.doc(hits[y].doc);

                            writer.write(available_queries.get(i).getQuery_id() + "       " + 0 + "       " + hitDoc.get("ID") + "    " + "0" + "    " + hits[y].score + "    " + "Lucene");
                            System.out.println("\tScore " + hits[y].score + "\tID =" + hitDoc.get("ID"));
                            writer.write("\n");

                        }
                    }
                }
                writer.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
