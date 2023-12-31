package crypto_algos;

public class Affine {
    public static String Affine_Enc(String message, int k1, int k2) {
        message = message.replaceAll(" ", "").toUpperCase();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < message.length(); i++) {
            char ch = message.charAt(i);
            if(Character.isLetter(ch)){
                int C = (k1 * (message.charAt(i) - 'A') + k2) % 26;
                if (C < 0) {
                    C += 26;
                }
                result.append((char) (C + 'A'));
            }else{
                continue;
            }
        }
        return result.toString();
    }

    public static String Affine_Dec(String ciphertext, int inv_k1, int k2) {
        ciphertext = ciphertext.replaceAll(" ", "").toUpperCase();

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++) {
            int M = (inv_k1 * ((ciphertext.charAt(i) - 'A') - k2)) % 26;
            if (M < 0) {
                M += 26;
            }
            result.append((char) (M + 'A'));
        }
        return result.toString();
    }
}
