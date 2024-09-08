package utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class T_Com {
    public static BigInteger Factorial(int n) {
        BigInteger bi = BigInteger.ONE;
        while(n>=1) {
            bi = bi.multiply(BigInteger.valueOf(n));
            n--;
        }
        return bi;
    }

    public static BigInteger comb(int n, int m) {
        return Factorial(n).divide(Factorial(m).multiply(Factorial(n-m)));
    }

//    static Map<String,BigInteger> map= new HashMap<String, BigInteger>();
//    private static BigInteger comb(int m,int n){
//        String key= m+","+n;
//        if(n==0)
//            return BigInteger.valueOf(1);
//        if (n==1)
//            return BigInteger.valueOf(m);
//        if(n>m/2)
//            return comb(m,m-n);
//        if(n>1){
//            if(!map.containsKey(key))
//                map.put(key, comb(m-1,n-1).add(comb(m-1,n)));
//            return map.get(key);
//        }
//        return BigInteger.valueOf(-1);
//    }
//
    public static BigDecimal Compute_T(int s, int iota, int L, int t){
        if (s<t) return BigDecimal.ZERO;
        BigDecimal[] p_w = new BigDecimal[s-t];
        BigDecimal T = BigDecimal.valueOf(s);
        for (int w = t+1; w<=s;w++){
            if ((iota-s+w)<0) continue;
            BigInteger tmp1 = comb(s, s-w);
            BigDecimal Dec_tmp1 = new BigDecimal(tmp1);
            BigInteger tmp2 = comb(L-s, iota-s+w);
            BigDecimal Dec_tmp2 = new BigDecimal(tmp2);
            BigInteger tmp3 = comb(L, iota);
            BigDecimal Dec_tmp3 = new BigDecimal(tmp3);
            p_w[w-t-1] = (Dec_tmp1.multiply(Dec_tmp2)).divide(Dec_tmp3, 6, BigDecimal.ROUND_HALF_EVEN);
            T = T.subtract(p_w[w-t-1].multiply(BigDecimal.valueOf(w-t)));
        }
        return T;
    }

//    public static void main (String[] args){
//        int L = (int) Math.pow(2, 14);
//        int[] s = {(int) Math.pow(2, 10), (int) Math.pow(2, 11), (int) Math.pow(2, 12)};
//        int[] iota = {1000, 2000, 3000, 4000};
//        int[] t = {500, 1000, 1500, 2000};
//        double[][][] T = new double[s.length][iota.length][t.length];
//        for (int i=0;i<s.length;i++){
//            for (int j=0;j<iota.length;j++){
//                for (int k=0;k<t.length;k++){
//                    T[i][j][k] = Compute_T(s[i], iota[j], L, t[k]);
//                }
//            }
//        }
//        System.out.println(T);
//    }
}
