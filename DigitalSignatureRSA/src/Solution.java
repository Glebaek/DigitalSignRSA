import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.BitSet;

public class Solution {

    public static class AdvancedEuclidean {
        AdvancedEuclidean(BigInteger one, BigInteger two, BigInteger three) {
            d = one;
            x = two;
            y = three;
        }

        AdvancedEuclidean() {
            ;
        }

        BigInteger d;
        BigInteger x;
        BigInteger y;


        AdvancedEuclidean gcdWide(BigInteger a, BigInteger b) {
            AdvancedEuclidean temphere = new AdvancedEuclidean(a, BigInteger.ONE, BigInteger.ZERO);
            AdvancedEuclidean temphere2 = new AdvancedEuclidean();

            if (b == BigInteger.ZERO) {
                return temphere;
            }

            temphere2 = gcdWide(b, a.mod(b));
            temphere = new AdvancedEuclidean();

            temphere.d = temphere2.d;
            temphere.x = temphere2.y;
            temphere.y = temphere2.x.subtract(a.divide(b).multiply(temphere2.y));

            return temphere;
        }
    }

    public static void main(String[] args) {
        int length = 1024;
        BigInteger factorOfQ = BigInteger.TWO.pow(length - 2);
        while(!factorOfQ.multiply(BigInteger.TWO).add(BigInteger.ONE).isProbablePrime(1)) {
            while (!factorOfQ.isProbablePrime(1))
                factorOfQ = factorOfQ.add(BigInteger.ONE);
            factorOfQ = factorOfQ.add(BigInteger.ONE);
        }

        BigInteger factorOfP = factorOfQ.add(BigInteger.ONE);
        while(!factorOfP.multiply(BigInteger.TWO).add(BigInteger.ONE).isProbablePrime(1)) {
            while (!factorOfP.isProbablePrime(1))
                factorOfP = factorOfP.add(BigInteger.ONE);
            factorOfP = factorOfP.add(BigInteger.ONE);
        }

        BigInteger q = factorOfQ.multiply(BigInteger.TWO).add(BigInteger.ONE);
        BigInteger p = factorOfP.multiply(BigInteger.TWO).add(BigInteger.ONE);
        BigInteger n = p.multiply(q);
        BigInteger phiN = (p.subtract(BigInteger.ONE)).multiply((q).subtract(BigInteger.ONE));

        BigInteger e = phiN.divide(BigInteger.TEN);
        while(e.gcd(phiN).compareTo(BigInteger.ONE) != 0)
            e = e.subtract(BigInteger.ONE);

        AdvancedEuclidean findD = new AdvancedEuclidean();
        findD = findD.gcdWide(e, phiN);
        if(findD.x.compareTo(BigInteger.ZERO) == -1)
            findD.x = findD.x.add(phiN);

        BigInteger message = BigInteger.valueOf(1234567890);
        BigInteger sign = message.modPow(findD.x, n);
        BigInteger probMessage = sign.modPow(e, n);

        System.out.println("p = " + p);
        System.out.println("q = " + q);
        System.out.println("n = " + n);
        System.out.println("phi of n = " + phiN);
        System.out.println("e = " + e);
        System.out.println("d = " + findD.x);
        System.out.println();
        System.out.println("message = " + message);
        System.out.println("sign = " + sign);
        System.out.println("a probable message = " + probMessage);
    }
}
