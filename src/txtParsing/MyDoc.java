package txtParsing;

public class MyDoc {

    private String title;
    private String summary;
    private String body;

    public MyDoc(String title, String summary, String body) {
        this.title = title;
        this.summary = summary;
        this.body = body;
    }

    @Override
    public String toString() {
        String ret = "MyDoc{"
                + "\n\tTitle: " + title
                + "\n\tSummary: " + summary
                + "\n\tBody: " + body;
        return ret + "\n}";
    }

    //---- Getters & Setters definition ----
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
