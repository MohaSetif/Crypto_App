package crypto_algos;

public class Vigenere {
    public static String vigenereEncrypt(String message, String key) {
        message = message.toUpperCase().replaceAll("[^A-Z]", "");
        key = key.toUpperCase();
        StringBuilder encryptedText = new StringBuilder();
        int keyIndex = 0;

        for (char messageChar : message.toCharArray()) {
            char keyChar = key.charAt(keyIndex % key.length());
            int encryptedChar = (messageChar + keyChar - 2 * 'A') % 26 + 'A';
            encryptedText.append((char) encryptedChar);
            keyIndex++;
        }

        return encryptedText.toString();
    }

    public static String vigenereDecrypt(String ciphertext, String key) {
        ciphertext = ciphertext.toUpperCase();
        key = key.toUpperCase();
        StringBuilder decryptedText = new StringBuilder();
        int keyIndex = 0;

        for (char encryptedChar : ciphertext.toCharArray()) {
            char keyChar = key.charAt(keyIndex % key.length());
            int decryptedChar = (encryptedChar - keyChar + 26) % 26 + 'A';
            decryptedText.append((char) decryptedChar);
            keyIndex++;
        }

        return decryptedText.toString();
    }
}
