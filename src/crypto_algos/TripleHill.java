package crypto_algos;

public class TripleHill {
    private static final int MATRIX_SIZE = 3;

    public static int[][] inverseKey(int[][] key) {
        int x = key[0][0] * ((key[1][1] * key[2][2]) - (key[1][2] * key[2][1]));
        int y = -key[1][0] * ((key[0][1] * key[2][2]) - (key[0][2] * key[2][1]));
        int z = key[2][0] * ((key[0][1] * key[1][2]) - (key[0][2] * key[1][1]));
    
        long det = (x + y + z) % 26;
        det = (det + 26) % 26;
    
        int detInverse = Mult_Inv.mult_inv((int) det, 26);
    
        int[][] adjugate = new int[3][3];
        adjugate[0][0] = key[1][1] * key[2][2] - key[1][2] * key[2][1];
        adjugate[0][1] = key[0][2] * key[2][1] - key[0][1] * key[2][2];
        adjugate[0][2] = key[0][1] * key[1][2] - key[0][2] * key[1][1];
        adjugate[1][0] = key[1][2] * key[2][0] - key[1][0] * key[2][2];
        adjugate[1][1] = key[0][0] * key[2][2] - key[0][2] * key[2][0];
        adjugate[1][2] = key[0][2] * key[1][0] - key[0][0] * key[1][2];
        adjugate[2][0] = key[1][0] * key[2][1] - key[1][1] * key[2][0];
        adjugate[2][1] = key[0][1] * key[2][0] - key[0][0] * key[2][1];
        adjugate[2][2] = key[0][0] * key[1][1] - key[0][1] * key[1][0];
    
        int[][] inverse = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                inverse[i][j] = (adjugate[i][j] * detInverse) % 26;
                inverse[i][j] = (inverse[i][j] + 26) % 26;
            }
        }
    
        return inverse;
    }    

    public static String hillCipherDecrypt(String ciphertext, String key) {
        ciphertext = ciphertext.replaceAll("[^a-zA-Z]", "").toUpperCase();
        key = key.replaceAll("[^a-zA-Z]", "").toUpperCase();
    
        int originalLength = ciphertext.length();
    
        // Adjusting the padding based on the original length
        int padding = 9 - (originalLength % 9);
        if (padding != 9) {
            for (int i = 0; i < padding; i++) {
                ciphertext += 'X';
            }
        }
    
        int[][] c = new int[ciphertext.length() / MATRIX_SIZE][MATRIX_SIZE];
        int[][] k = new int[MATRIX_SIZE][MATRIX_SIZE];
        int[][] r = new int[ciphertext.length() / MATRIX_SIZE][MATRIX_SIZE];
    
        for (int i = 0; i < ciphertext.length(); i++) {
            c[i / MATRIX_SIZE][i % MATRIX_SIZE] = ciphertext.charAt(i) - 'A';
        }
    
        for (int i = 0; i < MATRIX_SIZE * MATRIX_SIZE; i++) {
            k[i / MATRIX_SIZE][i % MATRIX_SIZE] = key.charAt(i) - 'A';
        }
    
        int[][] inverseKey = inverseKey(k);
    
        // Performing matrix multiplication
        for (int i = 0; i < ciphertext.length() / MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                r[i][j] = 0;
                for (int x = 0; x < MATRIX_SIZE; x++) {
                    r[i][j] += inverseKey[j][x] * c[i][x];
                    r[i][j] %= 26;
                }
            }
        }
    
        // Generating the decrypted text excluding the padding
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < originalLength; i++) {
            result.append((char) (r[i / MATRIX_SIZE][i % MATRIX_SIZE] + 'A'));
        }
    
        return result.toString();
    }              

    public static String hillCipherEncrypt(String message, String key) {
        message = message.replaceAll("[^a-zA-Z]", "").toUpperCase();
        key = key.replaceAll("[^a-zA-Z]", "").toUpperCase();

        int old_message_length = message.length();
        int padding = 9 - (message.length() % 9);

        if (padding != 9) {
            for (int i = 0; i < padding; i++) {
                message += 'X';
            }
        }

        int[][] m = new int[message.length() / MATRIX_SIZE][MATRIX_SIZE];
        int[][] k = new int[MATRIX_SIZE][MATRIX_SIZE];
        int[][] r = new int[message.length() / MATRIX_SIZE][MATRIX_SIZE];

        for (int i = 0; i < message.length(); i++) {
            m[i / MATRIX_SIZE][i % MATRIX_SIZE] = message.charAt(i) - 'A';
        }

        for (int i = 0; i < MATRIX_SIZE * MATRIX_SIZE; i++) {
            k[i / MATRIX_SIZE][i % MATRIX_SIZE] = key.charAt(i) - 'A';
        }

        for (int i = 0; i < message.length() / MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                r[i][j] = 0;
                for (int x = 0; x < MATRIX_SIZE; x++) {
                    r[i][j] += k[j][x] * m[i][x];
                    r[i][j] %= 26;
                }
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < old_message_length; i++) {
            result.append((char) (r[i / MATRIX_SIZE][i % MATRIX_SIZE] + 'A'));
        }

        return result.toString();
    }
}
