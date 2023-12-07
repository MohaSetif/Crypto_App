package crypto_algos;

public class Vigenere {
    public static String vigenereEncrypt(String message, String key) {
        message = message.replaceAll(" ", "").toUpperCase();
        key = key.replaceAll(" ", "").toUpperCase();
        StringBuilder encryptedText = new StringBuilder();
        int messageLength = message.length();
        int keyLength = key.length();

        for (int i = 0; i < messageLength; i++) {
            char messageChar = message.charAt(i);
            char keyChar = key.charAt(i % keyLength);
            int encryptedChar = (messageChar + keyChar - 2 * 'A') % 26 + 'A';
            encryptedText.append((char) encryptedChar);
        }

        return encryptedText.toString();
    }

    public static String vigenereDecrypt(String ciphertext, String key) {
        ciphertext = ciphertext.replaceAll(" ", "").toUpperCase();
        key = key.replaceAll(" ", "").toUpperCase();
        StringBuilder decryptedText = new StringBuilder();
        int messageLength = ciphertext.length();
        int keyLength = key.length();

        for (int i = 0; i < messageLength; i++) {
            char encryptedChar = ciphertext.charAt(i);
            char keyChar = key.charAt(i % keyLength);
            int decryptedChar = (encryptedChar - keyChar + 26) % 26 + 'A';
            decryptedText.append((char) decryptedChar);
        }

        return decryptedText.toString();
    }
}
