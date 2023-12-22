package crypto_algos;

public class DoubleHill {
    private static final int MATRIX_SIZE = 2;

    public static String Hill_Enc(String message, String key) {
        message = message.replaceAll("[^a-zA-Z]", "").toUpperCase();
        key = key.replaceAll("[^a-zA-Z]", "").toUpperCase();

        int old_message_length = message.length();
        int padding = 4 - (message.length() % 4);

        if (padding > 0 && padding < 4) {
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

    public static String Hill_Dec(String ciphertext, String key) {
        ciphertext = ciphertext.replaceAll("[^a-zA-Z]", "").toUpperCase();
        key = key.replaceAll("[^a-zA-Z]", "").toUpperCase();

        int original_length = ciphertext.length();
        int padding = 4 - (ciphertext.length() % 4);

        if (padding > 0 && padding < 4) {
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

        int[][] inverseKey = inv_key(k);

        for (int i = 0; i < ciphertext.length() / MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                r[i][j] = 0;
                for (int x = 0; x < MATRIX_SIZE; x++) {
                    r[i][j] += inverseKey[j][x] * c[i][x];
                    r[i][j] %= 26;
                }
            }
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < original_length; i++) {
            result.append((char) (r[i / MATRIX_SIZE][i % MATRIX_SIZE] + 'A'));
        }

        return result.toString();
    }

    public static int[][] inv_key(int[][] key) {
        int det = key[0][0] * key[1][1] - key[0][1] * key[1][0];
        int detInverse = Mult_Inv.mult_inv(det, 26);

        int[][] adjugate = new int[MATRIX_SIZE][MATRIX_SIZE];
        adjugate[0][0] = key[1][1];
        adjugate[0][1] = -key[0][1];
        adjugate[1][0] = -key[1][0];
        adjugate[1][1] = key[0][0];

        int[][] inverse = new int[MATRIX_SIZE][MATRIX_SIZE];
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                inverse[i][j] = (adjugate[i][j] * detInverse) % 26;
                if (inverse[i][j] < 0) {
                    inverse[i][j] += 26;
                }
            }
        }
        return inverse;
    }
}
