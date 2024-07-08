public class Caesar extends MonoAlphaSubstitution {
    private int shift;
    
    /**
     * It encrypts or decrypts a message using the Caesar Cipher. Requring three
     * arguments 1)encrypt or decrypt, 2)shift, 3)word to encrypt/decrypt.
     * 
     * @param args 1)encrypt or decrypt, 2)shift, 3)word to encrypt/decrypt.
     */
    public static void main(String[] args) {
        if(args.length == 3){
            Caesar cipher = new Caesar(Integer.parseInt(args[1]));
            if(args[0].equals("decrypt")){
                System.out.println(cipher.decrypt(args[2]));
            }
            else if(args[0].equals("encrypt")){
                System.out.println(cipher.encrypt(args[2]));
            }
            else{
                System.out.println("The first parameter must be \"encrypt\" or \"decrypt\"!\nUsage: java Caesar encrypt n \"cipher text\"");
            }
        }
        else if(args.length>3) {
            System.out.println("Too many parameters!\nUsage: java Caesar encrypt n \"cipher text\"");
        }
        else if(args.length<3){
            System.out.println("Too few parameters!\nUsage: java Caesar encrypt n \"cipher text\"");
        }
    }
    /**
     * This is the constructor for the Caesar class. It sets the shift value to 0.
     */
    public Caesar(){
        shift = 0;
    }

    /**
     * This is the constructor for the Caesar class. It takes an integer as a parameter and sets the
     * shift value to that integer.
     * 
     * @param key Number of characters to shift by in cipher.
     */
    public Caesar(int key){
        shift = key;
    }

    /**
     * Encrypts a character by shifting it by the shift value.
     * 
     * @param character The character to encrypt.
     * @return The character that is encrypted.
     */
    @Override
    public char encrypt(char character) {
        if(shift < 0){
            shift = 26 + (shift%26);
        }
        if(character >= 'A' && character <= 'Z'){
            return (char) (((character-'A')+shift)%26+'A');
        }
        else if(character >= 'a' && character <= 'z')
        {
            return (char) ((((character-'a')+shift)%26)+'a');
        }
        return character;
    }

    /**
     * The decrypt function takes a character and returns the character shifted by the shift value.
     * 
     * @param character The character to be decrypted.
     * @return The character that is decrypted.
     */
    @Override
    public char decrypt(char character){ 
        int decryptShift = -shift;
        if(decryptShift < 0){
            decryptShift = 26 + (decryptShift%26);
        }
        if(character >= 'A' && character <= 'Z'){
            return (char) (((character-'A')+decryptShift)%26+'A');
        }
        else if(character >= 'a' && character <= 'z')
        {
            return (char) ((((character-'a')+decryptShift)%26)+'a');
        }
        return character; 
    }
}
