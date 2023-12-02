package crypto_algos;

public class Mult_Inv {
    public static int mult_inv(int a, int b) {
        a = (a % b + b) % b;
    
        for (int x = 1; x < b; x++) {
            if ((a * x) % b == 1) {
                return x;
            }
        }
        return -1;
    }
}
