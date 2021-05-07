package App;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import txtParsing.*;

public class CreateIndex {

    /**
     * Configures IndexWriter.
     * Creates a lucene's inverted index.
     *
     */
    public CreateIndex() throws Exception{
        //ArrayList<String> files = readFolderContents("LISA-Documents");
        ArrayList<String> files = readFolderContents("/Users/emiliadan/Downloads/Anaktisi/lisa"); //for emy

        String indexLocation = ("Index"); //define were to store the index

        Date start = new Date();
        try {
            System.out.println("Indexing to directory '" + indexLocation + "'...");

            Directory dir = FSDirectory.open(Paths.get(indexLocation));
            // define which analyzer to use for the normalization of documents

            Analyzer analyzer = new EnglishAnalyzer();
            // define retrieval model
            Similarity similarity = new ClassicSimilarity();
            // configure IndexWriter

            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            iwc.setSimilarity(similarity);

            // Create a new index in the directory, removing any
            // previously indexed documents:
            iwc.setOpenMode(OpenMode.CREATE);

            // create the IndexWriter with the configuration as above
            IndexWriter indexWriter = new IndexWriter(dir, iwc);

            // parse txt document using TXT parser and index it
            for (String filename : files) {
                System.out.println(filename);
                List<MyDoc> docs = TXTParsing.parse(filename);
                for (MyDoc doc : docs) {
                    indexDoc(indexWriter, doc);
                }
            }
            indexWriter.close();

            Date end = new Date();
            System.out.println(end.getTime() - start.getTime() + " total milliseconds");

        } catch (IOException e) {
            System.out.println(" caught a " + e.getClass() +
                    "\n with message: " + e.getMessage());
        }


    }

    /**
     * Creates a Document by adding Fields in it and
     * indexes the Document with the IndexWriter
     *
     * @param indexWriter the indexWriter that will index Documents
     * @param mydoc the document to be indexed
     *
     */
    private void indexDoc(IndexWriter indexWriter, MyDoc mydoc){

        try {

            // make a new, empty document
            Document doc = new Document();

            // create the fields of the document and add them to the document
            StoredField ID = new StoredField("ID", mydoc.getID());
            doc.add(ID);
            StoredField title = new StoredField("title", mydoc.getTitle());
            doc.add(title);
            StoredField body = new StoredField("body", mydoc.getBody());
            doc.add(body);
            String fullSearchableText = mydoc.getTitle() + " " + mydoc.getBody();
            TextField contents = new TextField("contents", fullSearchableText, Field.Store.NO);
            doc.add(contents);

            if (indexWriter.getConfig().getOpenMode() == OpenMode.CREATE) {
                // New index, so we just add the document (no old document can be there):
                System.out.println("adding " + mydoc);
                indexWriter.addDocument(doc);
            }
        } catch(Exception e){
            e.printStackTrace();
        }

    }
    private ArrayList<String> readFolderContents(String path){
        Path dirPath = Paths.get(path);
        ArrayList<String> txtfiles = new ArrayList<>();
        try (DirectoryStream<Path> files = Files.newDirectoryStream(dirPath)) {
            for(Path file: files){
                if(!file.getFileName().toString().startsWith(".")) {
                    //txtfiles.add(path + "\\"+file.getFileName().toString());
                    txtfiles.add(path + "/"+file.getFileName().toString());


                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return txtfiles;
    }

    public static void main(String[] args) {
        try {
            CreateIndex indexer = new CreateIndex();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
