public class AES {
    private static final int[][] s_box = {
        {0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76},
        {0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0},
        {0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15},
        {0x04, 0xC7, 0x23, 0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75},
        {0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84},
        {0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58, 0xCF},
        {0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8},
        {0x51, 0xA3, 0x40, 0x8F, 0x92, 0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2},
        {0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19, 0x73},
        {0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB},
        {0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79},
        {0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08},
        {0xBA, 0x78, 0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A},
        {0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E},
        {0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF},
        {0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16}
    };

    private static final int RCON[] = {
        0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80, 0x1b, 0x36
    };

    private static final int[][] mixColumns = {
        {0x02, 0x03, 0x01, 0x01},
        {0x01, 0x02, 0x03, 0x01},
        {0x01, 0x01, 0x02, 0x03},
        {0x03, 0x01, 0x01, 0x02}
    };

    public static String[][] AES_Enc(String message, String key){
        String[][] State = new String[4][4];
        String[][] Key = new String[4][4];
        String[][] result = new String[4][4];
        int[][] intResult = new int[4][4];
        
        int col = 0;

        int rowIndex = 0;
        int colIndex = 0;

        for(int i = 0; i < 16; i++){
            State[i / 4][i % 4] = Integer.toHexString(message.charAt(i));
            Key[i / 4][i % 4] = Integer.toHexString(key.charAt(i));
            result[i / 4][i % 4] = Integer.toHexString(
                Integer.parseInt(State[i / 4][i % 4], 16) ^ Integer.parseInt(Key[i / 4][i % 4], 16)
            );

            rowIndex = Character.digit(State[i / 4][i % 4].charAt(0), 16);
            colIndex = Character.digit(State[i / 4][i % 4].charAt(1), 16);
            
            result[i / 4][i % 4] = Integer.toHexString(s_box[rowIndex][colIndex]);
        }

        for(int i = 0; i < 4; i++){
            col = i + 1;
            if (col == 1) {
                shiftRow(result, 1, 1);
            } else if (col == 2) {
                shiftRow(result, 2, 2);
            } else if (col == 3) {
                shiftRow(result, 3, 3);
            }
        }

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                intResult[i][j] = Integer.parseInt(result[i][j], 16);
            }
        }

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                int sum = 0;
                for(int k = 0; k < 4; k++){
                    int mult = multiplyGF(intResult[k][j], mixColumns[i][k]);
                    sum ^= mult;
                }
                intResult[i][j] = sum;
            }
        }

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                result[i][j] = Integer.toHexString(intResult[i][j]);
            }
        }

        return result;
    }

    private static int multiplyGF(int a, int b){
        int p = 0;
        int hiBitSet;
        for(int i = 0; i < 8; i++){
            if((b & 1) == 1){
                p ^= a;
            }
            hiBitSet = a & 0x80;
            a <<= 1;
            if(hiBitSet == 0x80){
                a ^= 0x1b;
            }
            b >>= 1;
        }
        return p & 0xFF;
    }

    private static void shiftRow(String[][] matrix, int rowIndex, int numberOfShifts) {
        int rowLength = matrix[rowIndex].length;

        // Perform the shift using a temporary array
        String[] temp = new String[rowLength];
        for (int i = 0; i < rowLength; i++) {
            temp[(i + numberOfShifts) % rowLength] = matrix[rowIndex][i];
        }

        // Update the original row with the shifted elements
        System.arraycopy(temp, 0, matrix[rowIndex], 0, rowLength);
    }

    private static final int WORD_SIZE = 4;

    public static int[][] keySchedule(byte[] key) {
        int[][] w = new int[4][4 * (10 + 1)];

        for (int i = 0; i < 4 * WORD_SIZE; i++) {
            w[i / WORD_SIZE][i % WORD_SIZE] = key[i] & 0xFF;
        }

        for (int i = WORD_SIZE; i < 4 * (10 + 1); i++) {
            int[] temp = new int[WORD_SIZE];

            for (int k = 0; k < WORD_SIZE; k++) {
                temp[k] = w[k][i - 1];
            }

            if (i % WORD_SIZE == 0) {
                temp = keyScheduleCore(temp, i / WORD_SIZE);
                temp[0] ^= RCON[(i / WORD_SIZE) - 1];
            }

            for (int k = 0; k < WORD_SIZE; k++) {
                w[k][i] = w[k][i - WORD_SIZE] ^ temp[k];
            }
        }

        return w;
    }

    private static int[] keyScheduleCore(int[] in, int round) {
        int temp = in[0];
        for (int i = 0; i < WORD_SIZE - 1; i++) {
            in[i] = in[i + 1];
        }
        in[WORD_SIZE - 1] = temp;
    
        for (int i = 0; i < WORD_SIZE; i++) {
            in[i] = s_box[0][in[i]];
        }
    
        return in;
    }

    public static String hexArrayToString(String[][] hexValues) {
        StringBuilder sb = new StringBuilder();

        for (String[] row : hexValues) {
            for (String hex : row) {
                int intValue = Integer.parseInt(hex, 16);
                char charValue = (char) intValue;
                sb.append(charValue);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        // Example input
        String message = "iamkhalbasuniversitynicetomeetyou";
        String key = "MySecretKey98765";

        String[][] encrypted = AES_Enc(message, key);

        String result = hexArrayToString(encrypted);
        System.out.println("String: " + result);
    }

}