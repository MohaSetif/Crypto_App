package crypto_algos;

public class TripleHill {
    private static final int MATRIX_SIZE = 3;

    public static int[][] inverseKey(int[][] key) {
        int determinant = calculateDeterminant(key);
        int detInverse = calculateDetInverse(determinant);

        int[][] adjugate = calculateAdjugate(key);

        int[][] inverse = new int[MATRIX_SIZE][MATRIX_SIZE];
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                inverse[i][j] = mod((adjugate[i][j] * detInverse), 26);
            }
        }

        return inverse;
    }

    private static int calculateDeterminant(int[][] key) {
        int det = key[0][0] * ((key[1][1] * key[2][2]) - (key[1][2] * key[2][1]))
                - key[0][1] * ((key[1][0] * key[2][2]) - (key[1][2] * key[2][0]))
                + key[0][2] * ((key[1][0] * key[2][1]) - (key[1][1] * key[2][0]));

        return mod(det, 26);
    }

    private static int calculateDetInverse(int determinant) {
        int detInverse = Mult_Inv.mult_inv(determinant, 26);
        return mod(detInverse, 26);
    }

    private static int[][] calculateAdjugate(int[][] key) {
        int[][] adjugate = new int[MATRIX_SIZE][MATRIX_SIZE];
        adjugate[0][0] = key[1][1] * key[2][2] - key[1][2] * key[2][1];
        adjugate[0][1] = key[0][2] * key[2][1] - key[0][1] * key[2][2];
        adjugate[0][2] = key[0][1] * key[1][2] - key[0][2] * key[1][1];
        adjugate[1][0] = key[1][2] * key[2][0] - key[1][0] * key[2][2];
        adjugate[1][1] = key[0][0] * key[2][2] - key[0][2] * key[2][0];
        adjugate[1][2] = key[0][2] * key[1][0] - key[0][0] * key[1][2];
        adjugate[2][0] = key[1][0] * key[2][1] - key[1][1] * key[2][0];
        adjugate[2][1] = key[0][1] * key[2][0] - key[0][0] * key[2][1];
        adjugate[2][2] = key[0][0] * key[1][1] - key[0][1] * key[1][0];

        return adjugate;
    }

    public static String hillCipherDecrypt(String ciphertext, String key) {
        String cipher = preprocessText(ciphertext);
        key = preprocessText(key);
    
        int[][] c = createMatrixFromText(cipher);
        int[][] k = createMatrixFromText(key);
    
        int[][] inverseKey = inverseKey(k);
        int[][] r = performMatrixMultiplication(c, inverseKey);
    
        return generateText(r, cipher.length());
    }
    
    public static String hillCipherEncrypt(String message, String key) {
        String msg = preprocessText(message);
        key = preprocessText(key);
    
        int[][] m = createMatrixFromText(msg);
        int[][] k = createMatrixFromText(key);
    
        int[][] r = performMatrixMultiplication(m, k);
    
        return generateText(r, msg.length());
    }

    private static String preprocessText(String text) {
        text = text.replaceAll("[^a-zA-Z]", "").toUpperCase();
        int padding = 9 - (text.length() % 9);
        if (padding != 9) {
            for (int i = 0; i < padding; i++) {
                text += 'X';
            }
        }
        return text;
    }

    private static int[][] createMatrixFromText(String text) {
        int[][] matrix = new int[text.length() / MATRIX_SIZE][MATRIX_SIZE];

        for (int i = 0; i < text.length(); i++) {
            matrix[i / MATRIX_SIZE][i % MATRIX_SIZE] = text.charAt(i) - 'A';
        }

        return matrix;
    }

    private static int[][] performMatrixMultiplication(int[][] matrix1, int[][] matrix2) {
        int[][] result = new int[matrix1.length][MATRIX_SIZE];

        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                for (int x = 0; x < MATRIX_SIZE; x++) {
                    result[i][j] += matrix1[i][x] * matrix2[x][j];
                }
                result[i][j] = mod(result[i][j], 26);
            }
        }

        return result;
    }

    private static String generateText(int[][] matrix, int originalLength) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < originalLength; i++) {
            result.append((char) (matrix[i / MATRIX_SIZE][i % MATRIX_SIZE] + 'A'));
        }
        return result.toString();
    }

    private static int mod(int a, int b) {
        return ((a % b) + b) % b;
    }
}
