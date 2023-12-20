package crypto_algos;

import java.nio.charset.StandardCharsets;
import java.security.*;

import javax.crypto.Cipher;

public class Digital_Signature {
    public static String generateDS(String message, int keySize, String algorithm) throws Exception {
        try {
            KeyPair keyPair = generateKeyPair(keySize, algorithm);
            byte[] signature = generateSignature(message, keyPair.getPrivate(), algorithm);
            return bytesToHex(signature);
        } catch (NoSuchAlgorithmException e) {
            throw new NoSuchAlgorithmException("Algorithm " + algorithm + " with key size " + keySize + " not supported.");
        }
    }

    private static KeyPair generateKeyPair(int keySize, String algorithm) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    private static byte[] generateSignature(String message, PrivateKey privateKey, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        if (algorithm == "RSA") {
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            Signature dsa = Signature.getInstance("SHA256withRSA");
            dsa.initSign(privateKey);
            dsa.update(message.getBytes(StandardCharsets.UTF_8));
            return dsa.sign();
        } else if (algorithm == "DSA") {
            Signature dsa = Signature.getInstance("SHA256withDSA");
            dsa.initSign(privateKey);
            dsa.update(message.getBytes(StandardCharsets.UTF_8));
            return dsa.sign();
        } else if (algorithm == "DH") {
            throw new UnsupportedOperationException("DH algorithm does not support digital signatures.");
        } else {
            throw new UnsupportedOperationException("Unsupported algorithm: " + algorithm);
        }
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
