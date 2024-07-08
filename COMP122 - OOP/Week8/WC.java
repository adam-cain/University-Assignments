import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class WC {
    public static void main(String[] args) {
        if (args[0].equals("-w")) {
            System.out.println(wordCount(args[1]));
        }
        else if (args[0].equals("-m")) {
            System.out.println(charCount(args[1]));
        }
        else if (args[0].equals("-l")){
            System.out.println(lineCount(args[1]));
        }
        else if(args[0].equals("-s")){
            System.out.println(distinctCount(args[1])/wordCount(args[1]));
        }
        else if(args[0].equals("-b")){
            System.out.println(bagOfWords(args[1]));
        }
        else if(args[0].equals("-v")){
            bagOfWords(args[1],args[2]);
        }
        else if(args[0].equals("-d")){
            bagOfWords(args[1],args[2]);
        }
    }

    private static double wordCount(String input) {
        String[] words = input.split("\\s");
        return words.length;
    }

    private static int charCount(String input) {
        return input.length();
    }

    private static int lineCount(String inp){
        String[] words = inp.split("\\n");
        return words.length;
    }

    private static double distinctCount(String inp){
        String[] words = inp.split("\\s");
        HashSet<String> distinctWords = new HashSet<String>();
        for (String word : words) {
            if(!distinctWords.contains(word)){
                distinctWords.add(word);
            }
        }
        return distinctWords.size();
    }

    private static Vector<Integer> bagOfWords(String inp){
        ArrayList<String> words = new ArrayList<String>();
        Vector<Integer> count = new Vector<Integer>();
        Collections.addAll(words, inp.toLowerCase().split("\\s"));
        Collections.sort(words);
        words.add("");
        String prev = "";
        int counter = 1;
        for (int i = 0; i < words.size(); i++) {
            System.out.println(words.get(i));
            if(words.get(i).equals(prev)){
                counter++;
            }
            else{
                count.add(counter);
                prev = words.get(i);
                counter= 1;
            }
        }
        System.out.println(count);
        return count;
    }

    private static void bagOfWords(String s1, String s2){
        String[] s1Words = s1.toLowerCase().split("\\s");
        String[] s2Words = s2.toLowerCase().split("\\s");
        List<String> distinctWords = new ArrayList<String>();
        Collections.addAll(distinctWords,s1Words);
        Collections.addAll(distinctWords,s2Words);
        distinctWords = distinctWords.stream().distinct().collect(Collectors.toList());
        Vector <Integer> vec1 = new Vector<Integer>();
        Vector <Integer> vec2 = new Vector<Integer>();
        for (int i = 0; i < distinctWords.size(); i++) {
            int s1Count = 0;
            int s2Count = 0;
            for (String s1word : s1Words) {
                if(distinctWords.get(i).equals(s1word)){
                    s1Count++;
                }
            }
            for (String s2word : s1Words) {
                if(distinctWords.get(i).equals(s2word)){
                    s2Count++;
                }
            }
            vec1.add(s1Count);
            vec2.add(s2Count);
        }
        System.out.println(vec1);
        System.out.println(vec2);
    }
}
