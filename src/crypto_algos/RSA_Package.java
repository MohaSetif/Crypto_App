import java.io.*;
import java.security.*;
import javax.crypto.Cipher;
import java.util.Base64;

public class RSA_Package {

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    public static byte[] encrypt(String message, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(message.getBytes());
    }

    public static String decrypt(byte[] encryptedMessage, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedMessage);
        return new String(decryptedBytes);
    }

    public static String readFromFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    public static void writeToFile(String content, String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write(content);
        bw.close();
    }

    public static void main(String[] args) throws Exception {
        KeyPair keyPair = generateKeyPair();

        String messageFromFile = readFromFile("input.txt");
        System.out.println("Message from file: " + messageFromFile);

        byte[] encrypted = encrypt(messageFromFile, keyPair.getPublic());
        System.out.println("Encrypted message: " + Base64.getEncoder().encodeToString(encrypted));

        String decrypted = decrypt(encrypted, keyPair.getPrivate());
        System.out.println("Decrypted message: " + decrypted);

        writeToFile(decrypted, "output.txt");
    }
}
