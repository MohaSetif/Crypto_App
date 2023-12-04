package crypto_algos;

public class DoubleHill {
    public static String Hill_Enc(String message, String key){
        message = message.replaceAll(" ", "").toUpperCase();
        key = key.replaceAll(" ", "").toUpperCase();

        int old_message_length = message.length();

        int padding = 4 - (message.length() % 4);

        if (padding != 4) {
            for (int i = 0; i < padding; i++) {
                message += 'X';
            }
        }

        int[][] m = new int[message.length() / 2][2];
        int[][] k = new int[2][2];
        int[][] r = new int[message.length() / 2][2];
    
        for (int i = 0; i < message.length(); i++) {
            m[i / 2][i % 2] = message.charAt(i) - 'A';
        }

        for (int i = 0; i < 4; i++) {
            k[i % 2][i / 2] = key.charAt(i) - 'A';
        }
    
        for (int i = 0; i < message.length() / 2; i++) {
            for (int j = 0; j < 2; j++) {
                r[i][j] = 0;
                for (int x = 0; x < 2; x++) {
                    r[i][j] += k[x][j] * m[i][x];
                    r[i][j] %= 26;
                }
            }
        }
    
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < old_message_length; i++) {
            result.append((char) (r[i / 2][i % 2] + 'A'));
        }

        return result.toString();
    }

    public static String Hill_Dec(String ciphertext, String key){
        ciphertext = ciphertext.replaceAll(" ", "").toUpperCase();
        key = key.replaceAll(" ", "").toUpperCase();
    
        int original_length = ciphertext.length();
    
        int padding = 4 - (ciphertext.length() % 4);
    
        if (padding != 4) {
            for (int i = 0; i < padding; i++) {
                ciphertext += 'X';
            }
        }
    
        int[][] c = new int[ciphertext.length() / 2][2];
        int[][] k = new int[2][2];
        int[][] r = new int[ciphertext.length() / 2][2];
    
        for (int i = 0; i < ciphertext.length(); i++) {
            c[i / 2][i % 2] = ciphertext.charAt(i) - 'A';
        }
    
        for (int i = 0; i < 4; i++) {
            k[i % 2][i / 2] = key.charAt(i) - 'A';
        }
    
        int[][] inverseKey = inv_key(k);
    
        for (int i = 0; i < ciphertext.length() / 2; i++) {
            for (int j = 0; j < 2; j++) {
                r[i][j] = 0;
                for (int x = 0; x < 2; x++) {
                    r[i][j] += inverseKey[x][j] * c[i][x];
                    r[i][j] %= 26;
                }
            }
        }
    
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < original_length; i++) {
            result.append((char) (r[i / 2][i % 2] + 'A'));
        }
    
        return result.toString();
    }


    public static int[][] inv_key(int[][] key){
        int det = key[0][0] * key[1][1] - key[0][1] * key[1][0];
        int inv_det = Mult_Inv.mult_inv(det, 26);
        int[][] adjugate = new int[2][2];
        adjugate[0][0] = (key[1][1]) + 26 % 26;
        adjugate[0][1] = (-key[0][1]) + 26 % 26;
        adjugate[1][0] = (-key[1][0]) + 26 % 26;
        adjugate[1][1] = (key[0][0]) + 26 % 26;

        int[][] inverse = new int[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                inverse[i][j] = (adjugate[i][j] * inv_det) % 26;
                inverse[i][j] = (inverse[i][j] + 26) % 26;
            }
        }
        return inverse;
    }
}
