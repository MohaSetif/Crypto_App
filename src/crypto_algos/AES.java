package crypto_algos;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AES {

    public static String AES_Enc(String message, String key, String mode) throws Exception {
        StringBuilder result = new StringBuilder();
        byte[] plainText = message.getBytes();
        IvParameterSpec iv;
        SecretKeySpec secretKeySpec = generateKey(key);

        if(mode == "ECB"){
            iv = null;
        }else{
            iv = genIV();
        }

        byte[] encryptedContent = encrypt(plainText, secretKeySpec, iv, mode);

        String encodedEncryptedContent = Base64.getEncoder().encodeToString(encryptedContent);
        result.append(encodedEncryptedContent);

        return result.toString();
    }

    public static String AES_Dec(String cipher, String key, String mode) throws Exception {
        StringBuilder result = new StringBuilder();
        IvParameterSpec iv;
        byte[] cipherText = Base64.getDecoder().decode(cipher);

        SecretKeySpec secretKeySpec = generateKey(key);
        
        if(mode == "ECB"){
            iv = null;
        }else{
            iv = genIV();
        }

        byte[] decryptedContent = decrypt(cipherText, secretKeySpec, iv, mode);
        String decryptedMessage = new String(decryptedContent);
        result.append(decryptedMessage);
        return result.toString();
    }

    private static SecretKeySpec generateKey(String key) throws Exception {
        byte[] keyBytes = key.getBytes();
        return new SecretKeySpec(keyBytes, "AES");
    }

    private static byte[] encrypt(byte[] input, SecretKeySpec key, IvParameterSpec iv, String mode) throws Exception {
        Cipher cipher;
        if(mode == "CTR"){
            cipher = Cipher.getInstance("AES/"+mode+"/NoPadding");
        }else{
            cipher = Cipher.getInstance("AES/"+mode+"/PKCS5Padding");
        }
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        return cipher.doFinal(input);
    }

    private static byte[] decrypt(byte[] encryptedInput, SecretKeySpec key, IvParameterSpec iv, String mode) throws Exception {
        Cipher cipher;
        if(mode == "CTR"){
            cipher = Cipher.getInstance("AES/"+mode+"/NoPadding");
        }else{
            cipher = Cipher.getInstance("AES/"+mode+"/PKCS5Padding");
        }
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return cipher.doFinal(encryptedInput);
    }

    private static IvParameterSpec genIV() {
        byte[] iv = new byte[16];
        return new IvParameterSpec(iv);
    }
}

