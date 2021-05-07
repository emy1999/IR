package txtParsing;

public class MyDoc {

    private String id;
    private String title;
    private String body;

    public MyDoc(String id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    @Override
    public String toString() {
        String ret = "MyDoc{"
                + "\n\tID: " + id
                + "\n\tTitle: " + title
                + "\n\tBody: " + body;
        return ret + "\n}";
    }

    //---- Getters definition ----
    public String getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

}
