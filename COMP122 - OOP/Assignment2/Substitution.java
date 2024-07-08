public abstract class Substitution implements Cipher{
    /**
     * An abstract method that will be used to encrypt a single character.
     * 
     * @param c The character to encrypt.
     * @return The encrypted character.
     */
    public abstract char encrypt(char c);

    /**
     * An abstract method that will be used to decrypt a single character.
     * 
     * @param c The character to decrypt.
     * @return The decrypted character.
     */
    public abstract char decrypt(char c);

    /**
     * Encrypts a string of plaintext into ciphertext.
     * 
     * @param plainText The text to be encrypted.
     * @return The encrypted text.
     */
    public String encrypt(String plainText){
        String encryptedText = "";
        for (char character: plainText.toCharArray()) {
            encryptedText += encrypt(character);
        }
        return encryptedText;
    }
    /**
     * Decrypts a string of ciphertext into plaintext.
     * 
     * @param cryptoText The text to be decrypted.
     * @return The decrypted text.
     */
    public String decrypt(String cryptoText){
        String decryptedText = "";
        for (char character: cryptoText.toCharArray()) {
            decryptedText += decrypt(character);
        }
        return decryptedText;
    }
}
