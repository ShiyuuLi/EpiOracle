import utils.BloomFilter;
import utils.JDBCTools;
import utils.T_Com;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static utils.BloomFilter.add;
import static utils.BloomFilter.count;

public class Main {
//    public static BitSet BF;
//    public static int alpha = 27;
    public static int thres = 2;
    public static double SimRatio = 0.6;
    //    public BitSet BF;
//    public BloomFilter.SimpleHash[] func;
    public static BigInteger P = BigInteger.probablePrime(128, new Random());

    public static byte[][][][] c;
    public static HashSet<Integer>[][] ind;

    public static byte[][] tau;

    public static ArrayList<byte[]> Diff_tau;

    public static int L = (int) Math.pow(2, 12);
    public static int s = (int) Math.pow(2, 11);

    public static int Sym_Num = 2000;
    public static int Noise_Num = 0;
    public static int Total_Num = Sym_Num + Noise_Num;
    public static void main(String args[]) throws Exception {
        int l = 1;
        ind = new HashSet[Total_Num][l];
        c = new byte[Total_Num][l][2][32];
        tau = new byte[Total_Num][32];
        int i = 0;
//        int t = 300;
//        int Inc_Count = 0;

        BitSet BF = new BitSet(L);
        BloomFilter.SimpleHash[] func = new BloomFilter.SimpleHash[s];
        BloomFilter.BloomFilterInit(BF, func, L, s);

        Connection con;
        try {
            con = JDBCTools.getConnection();
            if (!con.isClosed())
                System.out.println("Successfully connected to the Database!");
            else {
                System.out.println("Failure connection!");
            }
            Statement statement = con.createStatement();
            double TotalTime =0;
            while (i < Total_Num) {
                String sql = "SELECT Code FROM EpiOracle.Symptoms WHERE SymptomID = '%s'";
                ResultSet resultSet = statement.executeQuery(String.format(sql, i+1));
                if (resultSet.next()){
                    String code = resultSet.getString("Code");
                    if (code.length()==0){
//                        if (code.length()<alpha){
//                            for (int a=0; a<alpha-code.length();a++){
//                                code+="0";
//                            }
                        code +="000000000000000000";
                    }
                    long Time1 = System.currentTimeMillis();
                    HelParGen.HelParGen(l, i, code, HelParGen.SimCheck(l, i, code));
//                    long T_Increase1 = System.currentTimeMillis();
                    add(BF, func, new String(tau[i]));
//                    Inc_Count++;
//                    BigDecimal T = BigDecimal.ZERO;
//                    if (Inc_Count - t>0){
//                        T = T_Com.Compute_T(s, (Inc_Count-t), L, t);
//                        if (T.equals(BigDecimal.valueOf(count(BF, func, new String(tau[i]))))){
//                            System.out.println(i+1);
//                        }
//                    }
//                    long T_Increase2 = System.currentTimeMillis();
//                    long T_Increase = T_Increase2 - T_Increase1;
//                    System.out.println("T_Increase:" + " " + T_Increase);
//                    long T_Count1 = System.currentTimeMillis();
//                    long T_Count2 = System.currentTimeMillis();
//                    long T_Count = T_Count2 - T_Count1;
//                    System.out.println("T_Count:" + " " + T_Count + "\n");
                    int count = count(BF, func, new String(tau[i]));
                    long Time2 = System.currentTimeMillis();
                    TotalTime += Time2 - Time1;
                    System.out.println("CountOfTag: " + count);
                }
                i = i + 1;
            }
            System.out.println("Total Time: " + " " + TotalTime);
            System.out.println("AverageTime:" + " " + TotalTime/2000);
            JDBCTools.close(statement);
            JDBCTools.close(con);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
//        for(int a=0;a<Sym_Num;a++){
////            System.out.println("tau_" + a + ":" + Arrays.toString(tau[a]));
//            long T_Increase1 = System.currentTimeMillis();
//            add(BF, func, new String(tau[a]));
//            long T_Increase2 = System.currentTimeMillis();
//            long T_Increase = T_Increase2 - T_Increase1;
//            System.out.println("T_Increase:" + " " + T_Increase);
//            long T_Count1 = System.currentTimeMillis();
//            int count = count(BF, func, new String(tau[a]));
//            long T_Count2 = System.currentTimeMillis();
//            long T_Count = T_Count2 - T_Count1;
//            System.out.println("T_Count:" + " " + T_Count + "\n");
//            System.out.println("tau" + a + ":" + Arrays.toString(tau[a]));
//            System.out.println("ind" + a + ":" + ind[a][0]);
//            System.out.println("ind" + a + ":" + ind[a][1]);
//            System.out.println("ind" + a + ":" + ind[a][2]);
//        }

////        for (int i=0;i<BF.length();i++){
////            if (BF.get(i) == true) System.out.println(i);
////        }
//        for(int a=0;a<Sym_Num;a++){
////            System.out.println("tau_" + a + ":" + Arrays.toString(tau[a]));
////            System.out.println(a + ":" + count(BF, func, new String(tau[a])));
//            long T_Count1 = System.currentTimeMillis();
//            int count = count(BF, func, new String(tau[a]));
//            long T_Count2 = System.currentTimeMillis();
//            System.out.println("T_Count");
//        }
        System.exit(0);
    }
}
