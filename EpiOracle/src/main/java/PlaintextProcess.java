import utils.JDBCTools;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class PlaintextProcess {
    static int Sym_Num = 2000;
    static int[] freq = new int[Sym_Num];
//    static boolean[] flag = new boolean[Sym_Num];
    static double SimRatio = 0.6;

    private static Set<String> divideIntoBlocks(String str, int blockSize) {
        Set<String> blocks = new HashSet<>();
        for (int i = 0; i < str.length(); i += blockSize) {
            blocks.add(str.substring(i, i + blockSize));
        }
        return blocks;
    }

    public static int computeSHD(String str1, String str2) {
        // Step 1: Divide each string into blocks of 18 bytes
        Set<String> set1 = divideIntoBlocks(str1, 18);
        Set<String> set2 = divideIntoBlocks(str2, 18);

        // Step 2: Compute the set difference (set1 - set2)
        set1.removeAll(set2);
        return set1.size()*18;
    }

    public static void main(String[] args) throws SQLException {
        for (int i=0; i<Sym_Num; i++) {
            freq[i] = 1;
        }
//        int i=0;
        int l = 1;
        long startTime = System.currentTimeMillis();
        Connection con;
        try {
            con = JDBCTools.getConnection();
            if (!con.isClosed())
                System.out.println("Successfully connected to the Database!");
            else {
                System.out.println("Failure connection!");
            }
            Statement statement = con.createStatement();

            for (int i=0; i < Sym_Num; i++) {
//                if (flag[i]) continue;
                String sql = "SELECT Code FROM EpiOracle.Symptoms WHERE SymptomID = '%s'";
                ResultSet resultSet = statement.executeQuery(String.format(sql, i + 1));
                if (resultSet.next()) {
                    String code = resultSet.getString("Code");
                    int t = (int) Math.round((1 - SimRatio) * code.length());
//                    if (code.isEmpty()) continue;
                    if (!code.isEmpty()){
                        for (int j = i+1; j < Sym_Num; j++) {
//                            if (j == i) continue;
                            ResultSet resultSet2 = statement.executeQuery(String.format(sql, j + 1));
                            if (resultSet2.next()) {
                                String code2 = resultSet2.getString("Code");
                                if (code2.isEmpty()) continue;
                                int HD = computeSHD(code, code2);
                                if (HD <= t) {
                                    freq[i]++;
                                    freq[j]++;
                                }
                            }
                        }
                    }
                }
//                System.out.println(freq[i]);
            }
            long endTime = System.currentTimeMillis();
            System.out.println("AverageTime:" + (endTime - startTime)/2000);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}