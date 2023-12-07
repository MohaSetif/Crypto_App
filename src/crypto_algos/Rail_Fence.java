package crypto_algos;

import java.util.Arrays;

public class Rail_Fence {

    public static String railFenceEncrypt(String message, int rails) {
        message = message.replaceAll(" ", "").toUpperCase();

        if (rails <= 1 || message.isEmpty()) {
            return message; // No encryption needed for 1 rail or empty message
        }

        StringBuilder[] railFence = new StringBuilder[rails];
        for (int i = 0; i < rails; i++) {
            railFence[i] = new StringBuilder();
        }

        int row = 0;
        boolean down = false;

        for (char c : message.toCharArray()) {
            railFence[row].append(c);
            
            if (row == 0 || row == rails - 1) {
                down = !down;
            }
            row += down ? 1 : -1;
        }

        StringBuilder result = new StringBuilder();
        for (StringBuilder rail : railFence) {
            result.append(rail);
        }
        return result.toString();
    }

    public static String railFenceDecrypt(String cipher, int rails) {
        cipher = cipher.replaceAll(" ", "").toUpperCase();
        char[][] rail = new char[rails][cipher.length()];
 
        for (int i = 0; i < rails; i++)
            Arrays.fill(rail[i], '\n');

        boolean dirDown = true;
 
        int row = 0, col = 0;
 
        for (int i = 0; i < cipher.length(); i++) {
            if (row == 0)
                dirDown = true;
            if (row == rails - 1)
                dirDown = false;
 
            rail[row][col++] = '*';
 
            if (dirDown)
                row++;
            else
                row--;
        }
 
        int index = 0;
        for (int i = 0; i < rails; i++)
            for (int j = 0; j < cipher.length(); j++)
                if (rail[i][j] == '*'
                    && index < cipher.length())
                    rail[i][j] = cipher.charAt(index++);
 
        StringBuilder result = new StringBuilder();
 
        row = 0;
        col = 0;
        for (int i = 0; i < cipher.length(); i++) {
            if (row == 0)
                dirDown = true;
            if (row == rails - 1)
                dirDown = false;
 
            if (rail[row][col] != '*')
                result.append(rail[row][col++]);
 
            if (dirDown)
                row++;
            else
                row--;
        }
        return result.toString();
    }           
}
