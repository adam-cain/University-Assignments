public class Caesar {
    /**
     * Encodes a string, argument 0, with a caesar cipher by a shift,
     * argument 1. 
     * @param args Usage: java Caesar n "cipher text"
     */
    public static void main(String[] args) {
        if(args.length == 2){
        System.out.println(rotate(Integer.parseInt(args[0]), args[1]));
        }
        else if (args.length > 2){
            System.out.println("Too many parameters!\nUsage: java Caesar n \"cipher text\"");
        }
        else if (args.length < 2){
            System.out.println("Too few parameters!\nUsage: java Caesar n \"cipher text\"");
        }
    }

    /**
     * Rotates an entire string by a shift, negative or positive.
     * The rotation of the string keeps the capitilization of the
     * characters and does not affect punctuation  
     * @param shift The number to rotate the string by
     * @param word The word to be rotated
     * @return The word shifted by the number
     */
    public static String rotate(int shift, String word){
        String newWord = "";
        for(int i = 0; i < word.length(); i++){
            newWord += rotate(shift, word.charAt(i));
        }
        return newWord;
    } 

    //A = 65
    //Z= 90
    //a = 97
    //z = 122
    /**
     * Rotates a single character by a shift, negative or positive.
     * The rotation of the character keeps the capitilization of the
     * character and does not affect punctuation  
     * @param shift The number to rotate the character by
     * @param character The character to shift
     * @return The character shifted by the number
     */
    public static char rotate(int shift,char character) {
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
  
}