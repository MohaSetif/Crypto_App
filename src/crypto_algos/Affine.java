public class Affine {
    public static String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static char Affine_Enc(char letter, int k1, int k2){
        int C = 1;
        if(Euclidean.is_euclidean(k1, 26) == 1){
            C = (k1 * (letter - 'A') + k2) % 26;
            if (C < 0) C += 26;
        }
        return alphabets.charAt(C);
    }

    public static char Affine_Dec(char letter, int k1, int k2){
        int C = 1;
        int inv_k1 = Mult_Inv.mult_inv(k1, 26);

        C = (inv_k1 * ((letter - 'A') - k2)) % 26;
        if (C < 0) C += 26;

        return alphabets.charAt(C);
    }
}
