package crypto_algos;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class Digital_Signature {
    public static String generateDS(String message, int keySize, String algorithm) throws Exception {
        try {
            KeyPair keyPair = generateKeyPair(keySize, algorithm);
            byte[] signature = generateSignature(message, keyPair.getPrivate(), algorithm);
            return bytesToHex(signature);
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException("Algorithm "+algorithm+" with key size "+keySize+" not supported.");
        }
    }

    private static KeyPair generateKeyPair(int keySize, String algorithm) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    private static byte[] generateSignature(String message, PrivateKey privateKey, String algorithm) throws Exception {
        Signature signature = Signature.getInstance("SHA256with"+algorithm);
        signature.initSign(privateKey);
        signature.update(message.getBytes(StandardCharsets.UTF_8));
        return signature.sign();
    }

    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
