public class Brutus {

    public static final double[] english = {
        0.0855, 0.0160, 0.0316, 0.0387, 0.1210, 0.0218, 0.0209, 0.0496, 0.0733,
        0.0022, 0.0081, 0.0421, 0.0253, 0.0717, 0.0747, 0.0207, 0.0010, 0.0633,
        0.0673, 0.0894, 0.0268, 0.0106, 0.0183, 0.0019, 0.0172, 0.0011
    };
    /**
     * Decodes encoded ceasar cipher from a string argument to 
     * decoded plaintext through statistical anylasis of the 
     * freqeuncy of charcters in the string.
     * 
     * @param args Usage: java Brutus "cipher text"
     */
    public static void main(String[] args) {
        if (args.length == 1){
        String word = args[0];
        double chi = chiSquared(frequency(word), english);
        int shift = 0;
        for(int i = 1; i < 26; i++){
            String shiftedWord = Caesar.rotate(i, word);
            double newChi = chiSquared(frequency(shiftedWord), english);
            if(newChi < chi){
                chi = newChi;
                shift = i;
            }
        }
        System.out.println(Caesar.rotate(shift, word));
        }
        else if (args.length > 1){
            System.out.println("Too many parameters!\nUsage: java Brutus \"cipher text\"");
        }
        else{
            System.out.println("Too few parameters!\nUsage: java Brutus \"cipher text\"");
        }
    }

    /**
     * Counts the amount of times a character appears in a string.
     * @param word String to count
     * @return A int array of the amount of times each character appears, ordered alphabetically
     */
    public static int[] count(String word) {
        int[] count = new int [26];
        for(int i = 0; i < word.length(); i++){
            char letter = word.charAt(i);
            if(letter >= 'A' && letter <= 'Z'){
                count[letter-65]++;
            }
            else if(letter >= 'a' && letter <= 'z')
            {
                count[letter-97]++;
            }
        }
        return count;
    }

    /**
     * Calculates the frequency of the amount of times a character appears in a string,
     * Done by finding how many times a character appears in teh string and dividing by the length 
     * of the string. (Character count/String length)
     * @param word String to find frequency of
     * @return A double array of the frequency of each character in string, ordered alphabetically
     */
    public static double[] frequency(String word){
        double[] freq = new double[26];
        int[] count = count(word);
        int length = word.length();
        for(int i = 0; i <26; i++){
            freq[i] = ((double)count[i])/length;
        }
        return freq;
    }
    //lower the chi the better

    /**
     * Calculates the diffrence between frequency of character appearance in string and character 
     * appearance in english langue. The lower the chi the closer the diffrence.
     * @param frequency The freqency of each character appearing in the String
     * @param englishFreq The frequency of each character that each character appears in english langue
     * @return A double of the diffrence between frequency and english frequency
     */
    public static double chiSquared(double[] frequency, double[] englishFreq){
        double chi = 0;
        for(int i = 0; i < 26; i++){
            chi += Math.pow((frequency[i] - englishFreq[i]),2)/englishFreq[i];
        }
        return chi;
    }
}
