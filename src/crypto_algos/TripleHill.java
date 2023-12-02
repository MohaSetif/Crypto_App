package crypto_algos;

public class TripleHill {
    public static int[][] inverseKey(int[][] key) {
        int det = (key[0][0] * key[1][1] * key[2][2] - key[0][0] * key[1][2] * key[2][1]
                - key[1][0] * key[0][1] * key[2][2] + key[1][0] * key[0][2] * key[2][1]
                + key[2][0] * key[0][1] * key[1][2] - key[2][0] * key[0][2] * key[1][1]) % 26;
        
        int detInverse = Mult_Inv.mult_inv(det, 26);
        
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
                if (inverse[i][j] < 0) {
                    inverse[i][j] += 26;
                }
            }
        }
        
        return inverse;
    }

    public static String hillCipherDecrypt(String ciphertext, String key) {
        ciphertext = ciphertext.replaceAll(" ", "").toUpperCase();
        key = key.replaceAll(" ", "").toUpperCase();
    
        int keyLength = key.length();
        int numBlocks = ciphertext.length() / keyLength;
    
        int[][] c = new int[keyLength][numBlocks];
        int[][] k = new int[keyLength][keyLength];
        int[][] r = new int[keyLength][numBlocks];
    
        // Convert the ciphertext and key to matrices
        for (int i = 0; i < ciphertext.length(); i++) {
            c[i % keyLength][i / keyLength] = ciphertext.charAt(i) - 'A';
            k[i / keyLength][i % keyLength] = key.charAt(i % keyLength) - 'A';
        }
    
        int[][] inverseKey = inverseKey(k);
    
        // Perform matrix multiplication
        for (int i = 0; i < keyLength; i++) {
            for (int j = 0; j < numBlocks; j++) {
                for (int x = 0; x < keyLength; x++) {
                    r[i][j] += inverseKey[i][x] * c[x][j];
                }
                r[i][j] %= 26;
            }
        }
    
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++) {
            result.append((char) (r[i % keyLength][i / keyLength] + 'A'));
        }
    
        return result.toString().replaceAll("X", "");
    }
    
    public static String hillCipherEncrypt(String message, String key) {
        message = message.replaceAll(" ", "").toUpperCase();
        key = key.replaceAll(" ", "").toUpperCase();
    
        int old_message_length = message.length();
    
        int padding = 9 - (message.length() % 9);
        System.out.println(padding);
        if (padding != 9) {
            for (int i = 0; i < padding; i++) {
                message += 'X';
            }
        }

        int[][] m = new int[message.length() / 3][3];
        int[][] k = new int[3][3];
        int[][] r = new int[message.length() / 3][3];
    
        for (int i = 0; i < message.length(); i++) {
            m[i / 3][i % 3] = message.charAt(i) - 'A';
        }

        for (int i = 0; i < 9; i++) {
             k[i / 3][i % 3] = key.charAt(i) - 'A';
        }
    
        for (int i = 0; i < message.length() / 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int x = 0; x < 3; x++) {
                    r[i][j] += k[j][x] * m[i][x]; // Perform matrix multiplication: r = k * m
                }
                r[i][j] %= 26; // Apply modulo 26
            }
        }
    
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < old_message_length; i++) {
            result.append((char) (r[i / 3][i % 3] + 'A'));
        }

        return result.toString();
    }
    
}


//  message = message.replaceAll(" ", "").toUpperCase();
//         key = key.replaceAll(" ", "").toUpperCase();

//         int[][] m = new int[3][3];
//         int[][] k = new int[3][3];
//         int[][] r = new int[3][3];

//         // Convert the message and key to matrices
//         for (int i = 0; i < 9; i++) {
//             m[i / 3][i % 3] = message.charAt(i) - 'A';
//             k[i / 3][i % 3] = key.charAt(i) - 'A';
//         }

//         // Perform matrix multiplication
//         for (int i = 0; i < 3; i++) {
//             for (int j = 0; j < 3; j++) {
//                 for (int x = 0; x < 3; x++) {
//                     r[i][j] += k[i][x] * m[x][j];
//                 }
//                 r[i][j] %= 26;
//             }
//         }

//         // Convert the result matrix to a string
//         StringBuilder result = new StringBuilder();
//         for (int i = 0; i < 9; i++) {
//             result.append((char) (r[i / 3][i % 3] + 'A'));
//         }

//         return result.toString();

// int paddingLength = keyLength - (messageLength % keyLength);
//         if (paddingLength != keyLength) {
//             char paddingChar = 'X';
//             char[] padding = new char[paddingLength];
//             Arrays.fill(padding, paddingChar);
//             message += new String(padding);
//         }