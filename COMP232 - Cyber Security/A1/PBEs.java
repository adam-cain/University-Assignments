import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * Example of using Password-based encryption
 */

public class PBEs {
  public static void main(String[] args) throws Exception {
    PBEKeySpec pbeKeySpec;
    PBEParameterSpec pbeParamSpec;
    SecretKeyFactory keyFac;

    // Salt 
    byte[] salt = { (byte)0xc7, (byte)0x73, (byte)0x21, 
      (byte)0x8c, (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99 };
    // Iteration count 
    int count = 2048; 

    // Create PBE parameter set
    pbeParamSpec = new PBEParameterSpec(salt, count);

    // Initialization of the password
    final String PASSWORD = "newpass";

    char[] password = PASSWORD.toCharArray();

    // Create parameter for key generation
    pbeKeySpec = new PBEKeySpec(password);

    // Create instance of SecretKeyFactory for password-based encryption
    // using DES and MD5
    keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");

    // Generate a key
    Key pbeKey = keyFac.generateSecret(pbeKeySpec);

    // Create PBE Cipher

    // Our plaintext
    String clearText = "This is another example";
    byte[] byteText = clearText.getBytes();

    // Initialize PBE Cipher in encrypt mode with key and parameters
    Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
    byte[] encryptedText = new byte[]{};
    
    final int ITERATIONS = 10;

    long encrpytTimeSum = 0;
    long decryptTimeSum = 0;

    pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);
    for (int i = 0; i < ITERATIONS; i++) {
      long startTime = System.nanoTime();
      encryptedText = pbeCipher.doFinal(byteText);
      long endTime = System.nanoTime();
      encrpytTimeSum += endTime - startTime;
      System.out.println("-  "+(endTime - startTime));
    }


    long avgEncryptTime = encrpytTimeSum/ITERATIONS;

    System.out.println("Avg encrypt: "+avgEncryptTime+" nano seconds\n\n");
    
    pbeCipher.init(Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);
    for (int i = 0; i < ITERATIONS; i++) {
      long startTime = System.nanoTime();
      pbeCipher.doFinal(encryptedText);
      long endTime = System.nanoTime();
      decryptTimeSum += endTime - startTime;
      System.out.println("-  "+(endTime - startTime));
    }

    long avgDecryptTime = decryptTimeSum/ITERATIONS;

    System.out.println("Avg decrypt: "+ avgDecryptTime+" nano seconds");
  }
}
