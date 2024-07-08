public class Vigenere extends Substitution {
    private Caesar[] ciphers;
    private int index = 0;

    /**
     * It encrypts or decrypts a string using the Vigenere cipher. Requring 
     * three arguments 1)encrypt or decrypt, 2)translation word, 3)word to
     * encrypt/decrypt.
     * 
     * @param args 1)encrypt or decrypt, 2)translation word, 3)word to encrypt/decrypt.
     */
    public static void main(String[] args) {
        if(args.length == 3){
            Vigenere cipher = new Vigenere(args[1]);
            if(args[0].equals("decrypt")){
                System.out.println(cipher.decrypt(args[2]));
            }
            else if(args[0].equals("encrypt")){
                System.out.println(cipher.encrypt(args[2]));
            }
            else{
                System.out.println("First parameter must be \"encrypt\" or \"decrypt\"!\nUsage: java Vigenere encrypt key \"cipher text\"");
            }
        }
        else if(args.length>3) {
            System.out.println("Too many parameters!\nUsage: java Vigenere encrypt key \"cipher text\"");
        }
        else if(args.length<3){
            System.out.println("Too few parameters!\nUsage: java Vigenere encrypt key \"cipher text\"");
        }
    }
    
    /**
     * It creates an array of Caesar ciphers with the length of zero. Creating a shift of zero.
     */
    public Vigenere(){
        ciphers = new Caesar[0];
        ciphers[0] = new Caesar(0);
    }

    /**
     * It creates an array of Caesar ciphers with the length of the key. 
     * 
     * @param key key word to decrypt and encrypt word with.
     */
    public Vigenere(String key){
        int len = key.length();
        char[] keyArr = key.toCharArray();
        ciphers = new Caesar[len];
        for (int i = 0; i < len; i++) {
            ciphers[i] = new Caesar(keyArr[i]-'A');
        }
    }

    /**
     * The function takes a character and returns the encrypted character.
     * 
     * @param c The character to encrypt.
     * @return The encrypted character.
     */
    @Override
    public char encrypt(char c){
        char out = ciphers[index].encrypt(c);
        index++;
        if (index>ciphers.length){index = 0; }
        return out;
    }

    /**
     * The function takes a character and returns the decrypted character.
     * 
     * @param c The character to be decrypted.
     * @return The decrypted character.
     */
    @Override
    public char decrypt(char c){
        char out = ciphers[index].decrypt(c);
        index++;
        if (index>ciphers.length){index = 0; }
        return out;
    }
}