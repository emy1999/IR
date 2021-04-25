package txtParsing;

import utils.IO;

import java.util.ArrayList;
import java.util.List;

public class MyQuery {

    private String query_id;
    private String query_body;

    public MyQuery(String query_id, String query_body) {
        this.query_id = query_id;
        this.query_body = query_body;
    }


    @Override
    public String toString() {
        String ret = "MyQuery{"
                + "\n\tID: " + query_id
                + "\n\tBody: " + query_body;
        return ret + "\n}";
    }


    //---- Getters & Setters definition ----
    public String getQuery_id() {
        return query_id;
    }

    public void setQuery_id(String title) {
        this.query_id = query_id;
    }

    public String getQuery_body() {
        return query_body;
    }

    public void setQuery_body(String body) {
        this.query_body = query_body;
    }
}
