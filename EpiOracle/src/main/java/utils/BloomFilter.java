package utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

public class BloomFilter {
    public static void BloomFilterInit(BitSet BF, SimpleHash[] func, int L, int s) throws NoSuchAlgorithmException {
//        BF = new BitSet(L);
//        func = new SimpleHash[s];

        for (int i = 0; i < s; i++) {
            SecureRandom generator = SecureRandom.getInstance("SHA1PRNG");
            long random = generator.nextLong();
            func[i] = new SimpleHash(L, Long.toString(random));
        }
    }

    public static boolean add(BitSet BF, SimpleHash[] func, String m) throws Exception {
        boolean flag = false;
        for (SimpleHash f : func){
            if(BF.get(f.hash(m))) continue;
            else {
//                System.out.println("add:" + f.hash(m));
                BF.set(f.hash(m), true);
                flag = true;
                break;
            }
        }
        return flag;
    }

    public static int count(BitSet BF, SimpleHash[] func, String m) throws Exception {
        int num = 0;
        for (SimpleHash f:func){
            if(BF.get(f.hash(m))) {
//                System.out.println("count:" + f.hash(m));
//                System.out.println("num:" + num);
                num++;
            }
        }
        return num;
    }

    public static class SimpleHash {
        private int cap;
        private String seed;
        public SimpleHash(int cap, String seed) {
            this.cap = cap;
            this.seed = seed;
        }
//        public int hash(String m) throws Exception {
////            int h;
////            return (m == null) ? 0 : Math.abs(seed * (cap - 1) & ((h = m.hashCode()) ^ (h>>>16)));
////            BigInteger valBigInteger =  new BigInteger(m).add(seed);
//            byte[] val = Crypto.getHash("SHA-256", (m + seed.toString()).getBytes());
////            int tmp = 0;
////            for (int i=0;i<val.length;i++){
////                tmp += (int) val[i];
////                tmp = tmp<<4;
////            }
//            String valString = utils.Crypto.byte2Hex(val);
//            BigInteger res = new BigInteger(valString, 16).mod(BigInteger.valueOf(cap));
//            return Integer.valueOf(res.toString());
//        }

        public int hash(String value) throws Exception {
//            int result = 0;
//            int len = value.length();
//            for (int i = 0; i < len; i++) {
//                result = seed * result + value.charAt(i);
//            }
//            return (cap - 1) & result;
            byte[] val = Crypto.getHash("SHA-256", (value + seed).getBytes());
            String valString = utils.Crypto.byte2Hex(val);
            BigInteger valBigInteger = new BigInteger(valString, 16);
            BigInteger res = valBigInteger.mod(BigInteger.valueOf(cap));
            return Integer.parseInt(res.toString());
        }
    }

    public static void main(String args[]) throws Exception {
        SecureRandom r = new SecureRandom();
        byte[] targetTag = new byte[32];
        r.nextBytes(targetTag);
        int L = (int) Math.pow(2, 12);
        int s = (int) Math.pow(2, 11);
        BitSet BF = new BitSet(L);
        SimpleHash[] func = new SimpleHash[s];
        BloomFilter.BloomFilterInit(BF, func, L, s);
        int t = 1200;
        int[] iota = {600, 1200, 1800, 2400};
        for (int j=0;j<iota.length;j++){
            BigDecimal T = T_Com.Compute_T(s, iota[j], L, t);
            System.out.println(T);
            for (int i=0;i<t;i++){
                add(BF, func, utils.Crypto.byte2Hex(targetTag));
            }
            SecureRandom r2 = new SecureRandom();
            for (int i=0;i<iota[j];i++){
                byte[] tag = new byte[32];
                r2.nextBytes(tag);
                add(BF, func, utils.Crypto.byte2Hex(tag));
            }
            System.out.println(count(BF, func, utils.Crypto.byte2Hex(targetTag)));
            BF.clear();
        }


//        for (int i=0; i<2; i++){
//            add(BF, func, "1234567890");
//        }
////        for (int i=0;i<BF.length();i++){
////            if (BF.get(i) == true) System.out.println(i);
////        }
//        System.out.println(count(BF, func, "1234567890"));
    }
}
