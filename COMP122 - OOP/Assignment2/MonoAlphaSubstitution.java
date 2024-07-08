public class MonoAlphaSubstitution extends Substitution {
    private char[] mapping = new char[255];
    
    /**
     * It encrypts or decrypts a message using the Monoalphabetic Substitution Cipher.
     * Requring three arguments 1)encrypt or decrypt, 2)translation mapping, 3)word to
     * encrypt/decrypt.
     * 
     * @param args 1)encrypt or decrypt, 2)translation mapping, 3)word to encrypt/decrypt.
     */
    public static void main(String[] args) {
        if(args.length == 3){
            MonoAlphaSubstitution cipher = new MonoAlphaSubstitution(args[1]);
            if(args[0].equals("decrypt")){
                System.out.println(cipher.decrypt(args[2]));
            }
            else if(args[0].equals("encrypt")){
                System.out.println(cipher.encrypt(args[2]));
            }
            else{
                System.out.println("The first parameter must be \"encrypt\" or \"decrypt\"!\nUsage: java MonoAlphaSubstitution encrypt key \"cipher text\"");
            }
        }
        else if(args.length>3) {
            System.out.println("Too many parameters!\nUsage: java MonoAlphaSubstitution encrypt key \"cipher text\"");
        }
        else if(args.length<3){
            System.out.println("Too few parameters!\nUsage: java MonoAlphaSubstitution encrypt key \"cipher text\"");
        }
    }
    
    /**
     * This is the constructor for the MonoAlphaSubstitution class. It initializes the mapping array to
     * contain the default mapping of each ASCII character to itself.
     */
    public MonoAlphaSubstitution(){
        defaultMapping();
    }

    
    /**
     * It initializes the mapping array to contain the default mapping of each ASCII character to
     * itself. Then interprets the inputted tranlsation table into correct translation array.
     *
     * @param translation translation table.
     */
    public MonoAlphaSubstitution(String translation){
        defaultMapping();
        char[] translationTable = translation.toCharArray();

        for (int i = 0; i < Math.floorDiv(translationTable.length, 2)*2; i+=2) {
            char from = translationTable[i];
            char to = translationTable[i+1];
            mapping [from] = to;
        }
    }

    /**
     * The mapping array is initialized to contain the default mapping of each ASCII character to
     * itself.
     */
    private void defaultMapping()
    {
        for (int i = 0; i < 255; i++) {
            mapping [i] = (char)i;
        }
    }

    /**
     * Given a character, return the corresponding character in the mapping.
     * 
     * @param character The character to encrypt.
     * @return The character that was encrypted.
     */
    @Override
    public char encrypt(char character){
        return mapping[character];
    }

    /**
     * If the character is not in the mapping, return the character. Otherwise, return the mapping.
     * 
     * @param character The character to decrypt.
     * @return The character that was decrypted.
     */
    @Override
    public char decrypt(char character){
        if(mapping[character] != character){
            for (int i = 0; i < mapping.length; i++) {
                if(mapping[i]==character){
                    return (char)i;
                }
            }
        }
        return character;
    }
}