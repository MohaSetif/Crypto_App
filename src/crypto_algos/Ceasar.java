public class Ceasar {
    public static String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static char Ceasar_Enc(int letter){
        int new_pos = 0;
        new_pos = (letter - 'A' + 3) % 26;
        return alphabets.charAt(new_pos);
    }

    public static char Ceasar_Dec(int letter){
        int new_pos = 0;
        new_pos = (letter - 'A' - 3) % 26;
        return alphabets.charAt(new_pos);
    }

}
