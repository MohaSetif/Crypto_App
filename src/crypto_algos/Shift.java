package crypto_algos;

public class Shift {
    public static String Shift_Enc(String message, int s) {
        message = message.replaceAll(" ", "").toUpperCase();
        StringBuilder result = new StringBuilder();

        for (int j = 0; j < message.length(); j++) {
            char ch = message.charAt(j);
            if(Character.isLetter(ch)){
                int C = (message.charAt(j) - 'A' + s) % 26;
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

    public static String Shift_Dec(String cipher, int s) {
        cipher = cipher.replaceAll(" ", "").toUpperCase();
        StringBuilder result = new StringBuilder();

        for (int j = 0; j < cipher.length(); j++) {
            int M = (cipher.charAt(j) - 'A' - s) % 26;
            if (M < 0) {
                M += 26;
            }
            result.append((char) (M + 'A'));
        }

        return result.toString();
    }
}
