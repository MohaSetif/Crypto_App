package crypto_algos;

public class Caesar {
    public static String Caesar_Enc(String message){
        message = message.replaceAll(" ", "").toUpperCase();

        StringBuilder result = new StringBuilder();
        int c;

        for (int j = 0; j < message.length(); j++) {
            c = ((message.charAt(j) - 'A') + 3) % 26;
            result.append((char) (c + 'A'));
        }

        return result.toString();
    }

    public static String Caesar_Dec(String cipher){
        cipher = cipher.replaceAll(" ", "").toUpperCase();

        StringBuilder result = new StringBuilder();
        int m;

        for (int j = 0; j < cipher.length(); j++) {
            m = ((cipher.charAt(j) - 'A') - 3) % 26;
            result.append((char) (m + 'A'));
        }

        return result.toString();
    }

}
