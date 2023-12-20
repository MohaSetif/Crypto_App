package crypto_algos;

import java.nio.charset.StandardCharsets;
import java.security.*;

public class Hash {
    public static String hash_msg(String message, String sha_type) throws Exception {
        MessageDigest digest = MessageDigest.getInstance(sha_type);
        byte[] hashValue = digest.digest(message.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hashValue);
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
