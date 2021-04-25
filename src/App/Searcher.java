package App;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

    public Searcher(){
        try{
            String indexLocation = ("Index"); //define where the index is stored
            String field = "contents"; //define which field will be searched

            //Access the index using indexReaderFSDirectory.open(Paths.get(index))
            IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(indexLocation))); //IndexReader is an abstract class, providing an interface for accessing an index.
            IndexSearcher indexSearcher = new IndexSearcher(indexReader); //Creates a searcher searching the provided index, Implements search over a single IndexReader.
            indexSearcher.setSimilarity(new ClassicSimilarity());

            //Search the index using indexSearcher
            search(indexSearcher, field);

            //Close indexReader
            indexReader.close();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Searches the index given a specific user query.
     */
    private void search(IndexSearcher indexSearcher, String field){
        try{
            //read file with queries


            // define which analyzer to use for the normalization of user's query
            Analyzer analyzer = new EnglishAnalyzer();

            // create a query parser on the field "contents"
            QueryParser parser = new QueryParser(field, analyzer);

            System.out.println(" Available queries: ");
            List<MyQuery> parsed_queries = queryParsing("LISA/LISA.QUE");
            for(MyQuery query: parsed_queries){
                System.out.println(query);
            }

            // read user's query from stdin
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter the query's ID number, for multiple queries separate ID's using commas. ");
            System.out.print(">>");
            String line = br.readLine();
            while(line!=null && !line.equals("")){
                String[] input_ids = line.split(",");

                for (String id: input_ids){
                    System.out.println(id);

                    for(int i=0; i<parsed_queries.size(); i++){
                        if(parsed_queries.get(i).getQuery_id().equals(id)){
                            String query_body = parsed_queries.get(i).getQuery_body();

                            // parse the query according to QueryParser
                            Query query = parser.parse(query_body);
                            System.out.println("Searching for: " + query.toString(field));

                            // search the index using the indexSearcher
                            TopDocs results = indexSearcher.search(query, 100);
                            ScoreDoc[] hits = results.scoreDocs;
                            long numTotalHits = results.totalHits;
                            System.out.println(numTotalHits + " total matching documents");

                            //display results
                            for(int y=0; y<hits.length; y++){
                                Document hitDoc = indexSearcher.doc(hits[y].doc);
                                System.out.println("\tScore "+hits[y].score +"\ttitle="+hitDoc.get("title")+"\tcaption:"+hitDoc.get("caption")+"\tmesh:"+hitDoc.get("mesh"));
                            }
                        }
                    }
                }
                System.out.println("Enter query or 'q' to quit: ");
                System.out.print(">>");
                line = br.readLine();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Initialize a SearcherDemo
     */
    public static void main(String[] args){
        Searcher searcher = new Searcher();
    }
}
