public class Vigenere {
    public static String vigenereEncrypt(String message, String key) {
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

    public static String vigenereDecrypt(String encryptedText, String key) {
        StringBuilder decryptedText = new StringBuilder();
        int messageLength = encryptedText.length();
        int keyLength = key.length();

        for (int i = 0; i < messageLength; i++) {
            char encryptedChar = encryptedText.charAt(i);
            char keyChar = key.charAt(i % keyLength);
            int decryptedChar = (encryptedChar - keyChar + 26) % 26 + 'A';
            decryptedText.append((char) decryptedChar);
        }

        return decryptedText.toString();
    }
}
