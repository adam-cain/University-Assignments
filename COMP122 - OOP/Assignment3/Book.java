public class Book{
    private String title;
    private String author;
    private String content;
    private int edition;

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public String getContent(){
        return content;
    }

    public int getEdition(){
        return edition;
    }

    public Book(String t, String a, String c, int e){
        title = t;
        author = a;
        content = c;
        edition = e;
    }

    public int getPages(){
        int len = content.length();
        return(len / 700 + (len % 700 != 0 ? 1 : 0));
    }

    public String toString(){
        return "Title: "+title+"\nAuthor: "+author+"\nEdition: "+edition;
    }
}