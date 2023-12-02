public class DoubleHill {
    
    public static String Hill_Enc(String message, String key){
        int[][] m = new int[2][2];
        int[][] k = new int[2][2];
        int[][] r = new int[2][2];
        
        for (int i = 0; i < 4; i++) {
            m[i / 2][i % 2] = message.charAt(i) - 'A';
            k[i / 2][i % 2] = key.charAt(i) - 'A';
        }
    
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int x = 0; x < 2; x++) {
                    r[i][j] += k[i][x] * m[x][j];
                }
                r[i][j] %= 26;
            }
        }
    
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            result.append((char) (r[i / 2][i % 2] + 'A'));
        }
    
        return result.toString();
    }

    public static String Hill_Dec(String message, String key){
        int[][] m = new int[2][2];
        int[][] k = new int[2][2];
        int[][] r = new int[2][2];
        
        for (int i = 0; i < 4; i++) {
            m[i / 2][i % 2] = message.charAt(i) - 'A';
            k[i / 2][i % 2] = key.charAt(i) - 'A';
        }

        int[][] inv_k = inv_key(k);
    
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                for (int x = 0; x < 2; x++) {
                    r[i][j] += inv_k[i][x] * m[x][j];
                }
                r[i][j] %= 26;
            }
        }
    
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            result.append((char) (r[i / 2][i % 2] + 'A'));
        }
    
        return result.toString();
    }


    public static int[][] inv_key(int[][] key){
        int det = key[0][0] * key[1][1] - key[0][1] * key[1][0];
        int inv_det = Mult_Inv.mult_inv(det, 26);
        int[][] inverse = new int[2][2];
        inverse[0][0] = (key[1][1] * inv_det) % 26;
        inverse[0][1] = (26 + (-key[0][1] * inv_det)) % 26;
        inverse[1][0] = (26 + (-key[1][0] * inv_det)) % 26;
        inverse[1][1] = (key[0][0] * inv_det) % 26;
    
        return inverse;
    }
}
