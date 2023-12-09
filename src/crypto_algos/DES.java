package crypto_algos;

import java.security.spec.KeySpec;
import java.util.Base64;
import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;

public class DES {

    public static String DES_Enc(String message, String key) throws Exception {
        message = message.replaceAll(" ", "").toUpperCase();
        StringBuilder result = new StringBuilder();
        byte[] desKeyBytes = key.getBytes();

        KeySpec desKeySpec = new DESKeySpec(desKeyBytes);

        SecretKeyFactory desKeyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey desKeySecret = desKeyFactory.generateSecret(desKeySpec);

        Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

        desCipher.init(Cipher.ENCRYPT_MODE, desKeySecret);
        byte[] encryptedBytes = desCipher.doFinal(message.getBytes());
        String encryptedMessage = Base64.getEncoder().encodeToString(encryptedBytes);
        result.append(encryptedMessage);
        return result.toString();
    }

    public static String DES_Dec(String cipher, String key) throws Exception {
        StringBuilder result = new StringBuilder();
        byte[] cipherBytes = Base64.getDecoder().decode(cipher);
        byte[] desKeyBytes = key.getBytes();

        KeySpec desKeySpec = new DESKeySpec(desKeyBytes);

        SecretKeyFactory desKeyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey desKeySecret = desKeyFactory.generateSecret(desKeySpec);

        Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

         desCipher.init(Cipher.DECRYPT_MODE, desKeySecret);
        byte[] decryptedBytes = desCipher.doFinal(cipherBytes);
        String decryptedMessage = new String(decryptedBytes);
        result.append(decryptedMessage);
        return result.toString();
    }
}
