package crypto_algos;

public class Euclidean {
    public static int is_euclidean(int a, int b){
        if(b == 0){
            return a;
        } else {
            return is_euclidean(b, a % b);
        }
    }  
}
