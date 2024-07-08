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

public class IterationCount {
  public static void main(String[] args) throws Exception {
    PBEKeySpec pbeKeySpec;
    PBEParameterSpec pbeParamSpec;
    SecretKeyFactory keyFac;

    // Salt
    byte[] salt = { (byte) 0xc7, (byte) 0x73, (byte) 0x21, (byte) 0x8c, (byte) 0x7e, (byte) 0xc8, (byte) 0xee, (byte) 0x99 };
    final String PASSWORD = "newpass";
    char[] password = PASSWORD.toCharArray();
    pbeKeySpec = new PBEKeySpec(password);
    // Create instance of SecretKeyFactory for password-based encryption using DES and MD5
    keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
    Key pbeKey = keyFac.generateSecret(pbeKeySpec);

    // Our plaintext
    String clearText = "This is another example";
    byte[] byteText = clearText.getBytes();

    // Initialize PBE Cipher in encrypt mode with key and parameters
    Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");

    for (int i = 20000; i <= 400000; i+=20000) {
      long startTime = System.nanoTime();
      pbeParamSpec = new PBEParameterSpec(salt, i);
      pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);
      pbeCipher.doFinal(byteText);
      long endTime = System.nanoTime();
      System.out.println("Iteration count:" + i + "   Time:" + (endTime - startTime));
    }
  }
}