import java.io.*;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        String message = readFromFile("input.txt");
        Scanner sc = new Scanner(System.in);
        System.out.println("Insert the first prime number p:");
        int p = sc.nextInt();
        System.out.println("Now, insert the second prime number q:");
        int q = sc.nextInt();

        while(!RSA.prime_checker(p) || !RSA.prime_checker(q)){
            System.out.println("One of the inputs or both are wrong, try again!");
            System.out.println("Insert the first prime number p:");
            p = sc.nextInt();
            System.out.println("Insert the second prime number q:");
            q = sc.nextInt();
        }

        sc.close();

        System.out.println("Encrypting '"+message+"': ");
        int[] encryptedText = RSA.RSA_enc(message, p, q);
        for (int value : encryptedText) {
            System.out.print(value + " ");
        }
        System.out.println("");
        System.out.println("================================");
        System.out.println("Decrypting the cipher numbers: ");
        int[] decryptedMessage = RSA.RSA_dec(encryptedText, p, q);
        String decrypt = "";
        for (int value : decryptedMessage) {
            decrypt += (char) (value + 'A');
            System.out.print((char) (value + 'A') + " ");
        }

        writeToFile(decrypt, "output.txt");
    }

    public static String readFromFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }

    public static void writeToFile(String content, String fileName) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
        bw.write(content);
        bw.close();
    }
}
