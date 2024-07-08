public class StringApp {

    // Part 1
    public static String pow(String s, int n) {
        int i = 1;
        String newS = "";
        while (i<=n){
            newS += s;
            i++;
        }
        return newS;
    }


    // Part 2
    // a = string to look in
    // f = string to look for in a
    public static int factorCount(String a, String f){
        int count = 0;
        String currentString = "";
        for(int i = 0; i <= a.length()-f.length(); i++){
            currentString = a.substring(i, i+f.length());
            if(currentString.equals(f)){
                //System.out.println(count);
                i+= f.length();
                count++;
            }
        }
        return count;
    }
    
    public static int factorCount(String a, String f, boolean caseSensitive){
        if(caseSensitive){
            return factorCount(a, f);
        }
        else{
            return factorCount(a.toLowerCase(), f.toLowerCase());
        }
    }
    

    // Part 3
    public static void main(String[] args) {
         String input = args[0];
        int[] count = new int [26];

        for(int i = 0; i < input.length(); i++){
            char letter = input.charAt(i);
            if(letter >= 'A' && letter <= 'Z'){
                count[letter-65]++;
            }
            else if(letter >= 'a' && letter <= 'z')
            {
                count[letter-97]++;
            }
        }

        for(int i = 0; i < 26; i++)
        {
            System.out.println((char)(i+97)+": "+count[i]);
        }
        
    }
    
}
