import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Press {
    private Map<String,Integer> edition = new HashMap<String,Integer>();
    private Map<String,List<Book>> shelf  = new HashMap<String,List<Book>>();
    private int shelfSize;
    
    public Press(String pathToBooKDir, int _shelfSize){
        shelfSize = _shelfSize;
        File file = new File(pathToBooKDir); 
        if(file.exists() && file.isDirectory()) {
            File books[] = file.listFiles();
            for (int i = 0; i < books.length; i++) {
                edition.put(books[i].getName(), 0);
                shelf.put(books[i].getName(), new ArrayList<Book>());
                if(i >= _shelfSize){break;}
            }
        }  
    }

    public List<String> getCatalogue(){
        List<String> catalogue = new ArrayList<>();
        for (Map.Entry<String,Integer> book : edition.entrySet()) {
            catalogue.add(book.getKey());
        }
        return catalogue;
    }

    public Book print(String bookID, int edition) {
        File bookFile = new File(bookID);
        String rawText = "";
        String title = "";
        String author = "";
        Pattern titlePat = Pattern.compile("Title: (.*)");
        Pattern authorPat = Pattern.compile("Author: (.*)");
        Pattern startPat = Pattern.compile("\\*\\*\\* START OF .*");

        Boolean eoInfo = false;
        try (Scanner scanner = new Scanner(bookFile,"UTF-8")) {
            while (scanner.hasNextLine()) {
                String curr = scanner.nextLine();
                
                Matcher titleMat = titlePat.matcher(curr);
                Matcher authorMat = authorPat.matcher(curr);
                Matcher startMat = startPat.matcher(curr);
                if(titleMat.find()){
                    title = titleMat.group(1);
                }
                if(authorMat.find()){
                    author = authorMat.group(1);
                }
                if(eoInfo){
                    rawText += curr+"\n";
                }
                else if(startMat.find()){
                    eoInfo = true;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        }

        if(title.isBlank() || author.isBlank()){
            //throw new IOException();
            throw new IllegalArgumentException();
        }
        
        return new Book(title, author, rawText ,0);
    }

    public List<Book> request(String bookID, int amount){
        List<Book> requestedBooks = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            if(shelf.get(bookID).size() > 0){
                Book curr = shelf.get(bookID).get(0);
                requestedBooks.add(curr);
                shelf.get(bookID).remove(curr);
            }
            else{
                try{
                    int latestEdition = edition.get(bookID) + 1;
                    for (int j = i; j < amount; j++) {
                        requestedBooks.add(print(bookID, latestEdition));
                    }
                }
                catch(Exception e){
                    throw new IllegalArgumentException();
                }
            }
        }
        return requestedBooks;
    }
}