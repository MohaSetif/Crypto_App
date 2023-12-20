package crypto_algos;

public class RSA {
    public static int[] RSA_enc(String message, int p, int q, int e){
        int[] publicKey = PublicKeyGeneration(p, q, e);

        message = message.toUpperCase();
        int[] encrypted = new int[message.length()];
        for(int i = 0; i < message.length(); i++){
            int m = message.charAt(i) - 'A'; // Assuming A represents 0, B represents 1, and so on
            int C = SquareMultiply(m, publicKey[1], publicKey[0]);
            encrypted[i] = C;
        }
        return encrypted;
    }

    public static String RSA_dec(int[] encrypted, int p, int q, int e){
        int[] privateKey = PrivateKeyGeneration(p, q, e);
        
        StringBuilder decrypted = new StringBuilder();
        for(int i = 0; i < encrypted.length; i++){
            int M = SquareMultiply(encrypted[i], privateKey[1], privateKey[0]);
            decrypted.append((char) M);
        }
        return decrypted.toString();
    }

    public static int SquareMultiply(int base, int exponent, int modulus) {
        if (modulus == 1) return 0;
    
        int result = 1;
        base = base % modulus;
        while(exponent > 0) {
            if(exponent % 2 == 1){
                result = (result * base) % modulus;
            }
            base = (base * base) % modulus;
            exponent = exponent / 2;
        }
        return result;
    }

    public static boolean prime_checker(int x){
        boolean is_prime = true;
        if (x <= 1) {
            return is_prime;
        }
        for(int i = 2; i <= x/2; i++){
            if(x%i == 0){
                is_prime = false;
                break;
            }
        }
        return is_prime;
    }

    public static int[] PublicKeyGeneration(int p, int q, int e){
        int n = p*q;
        int phi = (p-1)*(q-1);
        if(Euclidean.is_euclidean(e, phi) == 1){
            return new int[]{n, e};
        }
        return null;
    }

    public static int[] PrivateKeyGeneration(int p, int q, int e){
        int n = p * q;
        int phi = (p-1)*(q-1);
        int d = Mult_Inv.mult_inv(e, phi);
        return new int[]{n, d};
    }
}
