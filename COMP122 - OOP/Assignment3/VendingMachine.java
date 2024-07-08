import java.util.ArrayList;
import java.util.List;

class test{
    public static void main(String[] args) {
        VendingMachine vm = new VendingMachine(1, "password");
        vm.insertCoin(0.01);
        vm.insertCoin(0.01);
        vm.insertCoin(0.01);
        vm.insertCoin(0.02);
        System.out.println(vm.returnCoins());
    }
}

public class VendingMachine {
    private List<Book> shelf;
    private double locationFactor;
    private double cassette;
    private double safe;
    private String password;

    public VendingMachine(double _locationFactor, String _password){
        locationFactor = _locationFactor;
        password = _password;
        shelf = new ArrayList<Book>();
    }

    public double getCassette(){
        return cassette;
    }

    public void insertCoin(double c){
        switch ((int)(c*100)) {
            case 1:
            case 2:
            case 5:
            case 10:
            case 20:
            case 50:
            case 100:
            case 200:
                cassette += c;
                return;
            default:
                throw new IllegalArgumentException();
        }
    }

    public double returnCoins(){
        double temp = cassette;
        cassette = 0;
        return temp;
    }

    public void restock(List<Book> books, String _password){
        if(!password.equals(_password))
        {throw new InvalidPasswordException();}
        shelf.addAll(books);
    }

    public double emptySafe(String password){
        double temp = safe;
        safe = 0;
        return temp;
    }

    public List<String> getCatalogue(){
        List<String> catalogue = new ArrayList<String>();
        for (Book book : shelf) {
            catalogue.add(book.toString());
        }
        return catalogue;
    }

    public double getPrice(int index){
        Book book = shelf.get(index);
        return book.getPages() * locationFactor;
    }

    public Book buyBook(int index){
        Book book = shelf.get(index);
        double price = getPrice(index);
        if(cassette < price){
            throw new CassetteException();
        }
        safe += price;
        cassette -= price;
        shelf.remove(index);
        return book;
    }
}