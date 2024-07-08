import java.util.HashMap;

import javax.sound.midi.Track;

public class Credit {
  public static void main(String[] args) {
    long number = Comp122.getLong("Number: ");
    int length = Long.toString(number).length();
    if (length>=13 && length<=16){

      int[] digits = seperateDigits(number);

      int total = 0;
      for (int i = length-2; i >= 0; i-=2){
        for (int digit: seperateDigits(2 * digits[i])){
          total += digit;
        }
      }

      for (int i = length-1; i >= 0; i-=2){
        total += digits[i];
      }

      if (total % 10 == 0){
        String startingDigits = (Integer.toString(digits[0]) + Integer.toString(digits[1]));
        String[] visaStart = {"4"};
        String[] amexStart = {"34","37"};
        String[] masterCardStart = {"51","52","53","54","55"};
        if(findCard(startingDigits, "VISA", visaStart)){System.out.println("VISA");}
        else if(findCard(startingDigits, "AMEX", amexStart)){System.out.println("AMEX");}
        else if(findCard(startingDigits, "MASTERCARD", masterCardStart)){System.out.println("MASTERCARD");}
        else{System.out.println("INVALID");}
      }
      else{
        System.out.println("INVALID");
      }
    }
    else{ System.out.println("INVALID");}
  }

  public static Boolean findCard(String cardNumber ,String cardName, String[] cardSet){
    for(String cardNum: cardSet){
      if(cardNumber.startsWith(cardNum)){
        return true;
      }
    }
    return false;
  }

  public static int[] seperateDigits (long number){
    int length = Long.toString(number).length();
    int[] digits = new int[length];

    for (int i = length-1; i >= 0; i--) {
      digits[i] = (int)(number % 10);
      number = number / 10;
    }
    return digits;
  }
}
