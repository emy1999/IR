package App;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.store.FSDirectory;

public class CustomIndexReader {

    public CustomIndexReader(String index_path){
        try{
            //Access the index using indexReader
            org.apache.lucene.index.IndexReader indexReader = DirectoryReader.open(FSDirectory.open(Paths.get(index_path))); //CustomIndexReader is an abstract class, providing an interface for accessing an index.

            //Retrieve all docs in the index using the indexReader
            printIndexDocuments(indexReader);

            //Close indexReader
            indexReader.close();

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void printIndexDocuments(org.apache.lucene.index.IndexReader indexReader){
        try {
            System.out.println("--------------------------");
            System.out.println("Documents in the index...");

            for (int i=0; i<indexReader.maxDoc(); i++) {
                Document doc = indexReader.document(i);
                System.out.println("\tID = "+doc.getField("ID")+"\n"+"\ttitle = "+doc.getField("title")+"\n"+"\tbody = "+doc.getField("body")+"\n");
            }
        } catch (CorruptIndexException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
