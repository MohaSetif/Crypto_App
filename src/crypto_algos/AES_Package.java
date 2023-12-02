import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AES_Package {
    public static byte[] encrypt(byte[] input, SecretKey key, IvParameterSpec iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        return cipher.doFinal(input);
    }

    public static byte[] decrypt(byte[] encryptedInput, SecretKey key, IvParameterSpec iv) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(encryptedInput);
    }

    public static IvParameterSpec genIV(int size) throws Exception {
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[16]; // 16 bytes for AES
        random.nextBytes(iv);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        return ivParameterSpec;
    }

    public static void main(String[] args) throws Exception {
        String inputFile = "input.txt";
        String encryptedFile = "encrypted.txt";
        String decryptedFile = "decrypted.txt";

        //1. Read the file content into a byte array
        byte[] fileContent = Files.readAllBytes(Paths.get(inputFile));

        //2. Generate a symmetric key for AES encryption
        KeyGenerator keyGen = KeyGenerator.getInstance("AES");
        IvParameterSpec iv = genIV(128);
        keyGen.init(128);
        SecretKey secretKey = keyGen.generateKey();

        //3. Encrypt the file content
        long startTimeEncrypt = System.nanoTime();
        byte[] encryptedContent = encrypt(fileContent, secretKey, iv);
        long endTimeEncrypt = System.nanoTime();
        long encryptionTime = endTimeEncrypt - startTimeEncrypt;

        //4. Write the encrypted content to a file
        String encodedEncryptedContent = Base64.getEncoder().encodeToString(encryptedContent);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(encryptedFile))) {
            writer.write(encodedEncryptedContent);
        }

        //5. Decrypt the encrypted content
        long startTimeDecrypt = System.nanoTime();
        byte[] decryptedContent = decrypt(encryptedContent, secretKey, iv);
        long endTimeDecrypt = System.nanoTime();
        long decryptionTime = endTimeDecrypt - startTimeDecrypt;

        //6. Write the decrypted content to a file
        FileOutputStream decryptedOutputStream = new FileOutputStream(decryptedFile);
        decryptedOutputStream.write(decryptedContent);
        decryptedOutputStream.close();

        //7. Display execution times
        System.out.println("Encryption Time: " + encryptionTime + " nanoseconds");
        System.out.println("Decryption Time: " + decryptionTime + " nanoseconds");
    }
}
