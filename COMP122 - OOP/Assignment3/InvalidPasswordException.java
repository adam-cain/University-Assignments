public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(String msg){
        super(msg);
    }
    public InvalidPasswordException(){
        super();
    }
}
