package crypto_algos;

public class TripleHill {
    public static int[][] inverseKey(int[][] key) {
        int x=key[0][0]*((key[1][1]*key[2][2])-(key[1][2]*key[2][1]));
        int y=-key[1][0]*((key[0][1]*key[2][2])-(key[0][2]*key[2][1]));
        int z=key[2][0]*((key[0][1]*key[1][2])-(key[0][2]*key[1][1]));
        System.out.println("x:"+x);
        System.out.println("y:"+y);
        System.out.println("z:"+z);
        int det=x+y+z;
        System.out.println("Det:"+det);
        det %= 26;

        if (det < 0) {
            det += 26;
        }
        System.out.println("Det after mod:"+det);
        
        int detInverse = Mult_Inv.mult_inv(det, 26);

        System.out.println(detInverse);
        System.out.println("Det inv:"+det);
        
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

        int old_ciphertext_length = ciphertext.length();

        int padding = 9 - (ciphertext.length() % 9);

        if (padding != 9) {
            for (int i = 0; i < padding; i++) {
                ciphertext += 'X';
            }
        }

        int[][] c = new int[ciphertext.length() / 3][3];
        int[][] k = new int[3][3];
        int[][] r = new int[ciphertext.length() / 3][3];

        for (int i = 0; i < ciphertext.length(); i++) {
            c[i / 3][i % 3] = ciphertext.charAt(i) - 'A';
        }

        for (int i = 0; i < 9; i++) {
            k[i % 3][i / 3] = key.charAt(i) - 'A';
            System.out.println("Key index at "+i%3+" and "+i/3+" is: "+k[i%3][i/3]);
        }
    
        int[][] inverseKey = inverseKey(k);

        for (int i = 0; i < inverseKey.length; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.println(inverseKey[i][j]);
            }
        }
    
        for (int i = 0; i < ciphertext.length() / 3; i++) {
            for (int j = 0; j < 3; j++) {
                r[i][j] = 0;
                for (int x = 0; x < 3; x++) {
                    r[i][j] += inverseKey[x][j] * c[i][x];
                    r[i][j] %= 26;
                }
            }
        }
    
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < old_ciphertext_length; i++) {
            result.append((char) (r[i / 3][i % 3] + 'A'));
        }

        return result.toString();
    }
    
    public static String hillCipherEncrypt(String message, String key) {
        message = message.replaceAll(" ", "").toUpperCase();
        key = key.replaceAll(" ", "").toUpperCase();

        int old_message_length = message.length();

        int padding = 9 - (message.length() % 9);

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
            k[i % 3][i / 3] = key.charAt(i) - 'A';
            System.out.println("Key index at "+i%3+" and "+i/3+" is: "+k[i%3][i/3]);
        }
    
        for (int i = 0; i < message.length() / 3; i++) {
            for (int j = 0; j < 3; j++) {
                r[i][j] = 0;
                for (int x = 0; x < 3; x++) {
                    r[i][j] += k[x][j] * m[i][x];
                    r[i][j] %= 26;
                }
            }
        }
    
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < old_message_length; i++) {
            result.append((char) (r[i / 3][i % 3] + 'A'));
        }

        return result.toString();
    }
    
}