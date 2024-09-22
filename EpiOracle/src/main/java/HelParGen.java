import utils.JDBCTools;

//import java.Main;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class HelParGen {
    public static ArrayList<String> m_perm;
    public static void perm(String[] buf, int start, int end) {
        if (start == end) {
//            System.out.println(Arrays.toString(buf));
            String str = new String();
            for (int x = 0;x<buf.length;x++){
                str+=buf[x];
            }
            m_perm.add(str);
            return;
        }
        for (int x = start; x <= end; x++) {
            swap(buf, start, x);
            perm(buf, start + 1, end);
            swap(buf, start, x);
        }
    }
    static void swap (String[] buf, int a, int b){
        String temp = buf[a];
        buf[a] = buf[b];
        buf[b] = temp;
    }

    public static byte[] SimCheck(int l, int i, String m) throws NoSuchAlgorithmException {
        int NumOfSimCheck;
        long T_SimCheck1 = System.currentTimeMillis();
        long T_SimCheck2;
        String[] buf = new String[m.length() / 18];
        for (int a = 0; a < m.length() / 18; a++) {
            buf[a] = m.substring(a * 18, (a + 1) * 18);
        }
        m_perm = new ArrayList<>();
        perm(buf, 0, buf.length - 1);
//        byte[][] Sim_tau = new byte[Main.thres][32];
//        int count = 0;
        for (int j = 0; j < i; j++) {
            for (String ReOrderM : m_perm) {
                for (int k = 0; k < l; k++) {
                    String seed_jk = "";
//                    List<Integer> sortedList = new ArrayList<>(Main.ind[j][k]);
//                    Collections.sort(sortedList);
                    for (Integer a : Main.ind[j][k]) {
                        if (a >= ReOrderM.length()) {
                            seed_jk += "0";
                        } else {
                            seed_jk += ReOrderM.charAt(a);
                        }
                    }
                    byte[] key_jk = utils.Crypto.getHash("SHA-256", (Main.P.toString() + seed_jk).getBytes());
                    byte[] tmp = new byte[key_jk.length];
                    for (int a = 0; a < key_jk.length; a++) {
                        tmp[a] = (byte) (key_jk[a] ^ Main.c[j][k][1][a]);
                    }
                    Main.tau[i] = utils.Crypto.AESDecrypt(tmp, Main.c[j][k][0]);
                    if (Main.tau[i] != null) {
//                        if (k>1) System.out.println("l is useful!");
//                        Sim_tau[count] = Main.tau[i];
//                        count++;
//                        if (count==Main.thres){
//                            SecureRandom generator = SecureRandom.getInstance("SHA1PRNG");
//                            int random = generator.nextInt(Main.thres);
//                            Main.tau[i] = Sim_tau[random];
                        NumOfSimCheck = j+1;
                        T_SimCheck2 = System.currentTimeMillis();
                        long T_SimCheck = T_SimCheck2-T_SimCheck1;
//                        System.out.println("NumOfSimCheck:" + NumOfSimCheck + "T_SimCheck:" + T_SimCheck);
                            return Main.tau[i];
//                        }

                    }
                }
            }
        }

//            for (int k = 0; k < l; k++) {
//                String seed_jk = "";
//                List<Integer> sortedList = new ArrayList<>(Main.ind[j][k]);
//                Collections.sort(sortedList);
//                for (Integer a : sortedList) {
//                    if (a >= m.length()) {
//                        seed_jk += "0";
//                    } else {
//                        seed_jk += m.charAt(a);
//                    }
//                }
//                byte[] key_jk = utils.Crypto.getHash("SHA-256", (Main.P.toString() + seed_jk).getBytes());
//                byte[] tmp = new byte[key_jk.length];
//                for (int a = 0; a < key_jk.length; a++) {
//                    tmp[a] = (byte) (key_jk[a] ^ Main.c[j][k][1][a]);
//                }
//                Main.tau[i] = utils.Crypto.AESDecrypt(tmp, Main.c[j][k][0]);
//                if (Main.tau[i] != null){
//                    return Main.tau[i];
//                }
//            }
//        }
        SecureRandom r = new SecureRandom();
        Main.tau[i] = new byte[32];
        r.nextBytes(Main.tau[i]);
//        Main.Diff_tau.add(Main.tau[i]);
        NumOfSimCheck = i;
        T_SimCheck2 = System.currentTimeMillis();
        long T_SimCheck = T_SimCheck2-T_SimCheck1;
//        System.out.println("NumOfSimCheck:" + " " + NumOfSimCheck + "T_SimCheck:" + " " + T_SimCheck);
        return Main.tau[i];
    }

    public static void HelParGen(int l, int i, String m, byte[] tau) throws NoSuchProviderException, NoSuchAlgorithmException {
        long T_HelParGen1 = System.currentTimeMillis();
        for (int k = 0; k < l; k++) {
            String seed_ik = "";
            Main.ind[i][k] = new HashSet<>();
//            if (Main.alpha>=m.length()){
//                for (int a = 0; a<Main.alpha; a++){
//                    Main.ind[i][k].add(a);
//                }
//                seed_ik = m;
//                for (int a = 0;a<Main.alpha-m.length();a++){
//                    seed_ik += "0";
//                }
//            }
//            else {
//                SymptomGen.randomSet(0, m.length()-1, Main.alpha, Main.alpha, Main.ind[i][k]);
            SymptomGen.randomSet(0, m.length() - 1, (int) Math.round(Main.SimRatio * m.length()), (int) Math.round(Main.SimRatio * m.length()), Main.ind[i][k]);
//            List<Integer> sortedList = new ArrayList<>(Main.ind[i][k]);
//            Collections.sort(sortedList);
            for (Integer a : Main.ind[i][k]) {
                seed_ik += m.charAt(a);
            }
//            System.out.println("seed_" + i + k + ":" + seed_ik);
//            }

//            System.out.println("seed_ik:" + seed_ik);
            byte[] key_ik = utils.Crypto.getHash("SHA-256", (Main.P.toString() + seed_ik).getBytes());
//            System.out.println("key:" + i + k + ":" + Arrays.toString(key_ik));
            SecureRandom r = new SecureRandom();
            byte[] r_ik = new byte[32];
            r.nextBytes(r_ik);
//            System.out.println("r_0:" + r_ik);
            Main.c[i][k][0] = utils.Crypto.AESEncrypt(r_ik, tau);
//            byte[] c2Byte = new byte[key_ik.length];
//            System.out.println("Plaintext of" + "c" + i + k + ":" + Arrays.toString(tau));
//            System.out.println("c" + i + k + 0 + ":" + Arrays.toString(Main.c[i][k][0]));
            for (int a = 0; a < key_ik.length; a++) {
//                byte[] c2Byte = c[i][k][1].getBytes();
                Main.c[i][k][1][a] = (byte) (key_ik[a] ^ r_ik[a]);
            }
//            System.out.println("c" + i + k + 1 + ":" + Arrays.toString(Main.c[i][k][1]));
        }
        long T_HelParGen2 = System.currentTimeMillis();
        long T_HelParGen = T_HelParGen2 - T_HelParGen1;
//        System.out.println("ParGenTime:" + T_HelParGen);
    }



    public static void main(String args[]) throws Exception {
//        byte[][] tau = new byte[2][32];
        int l = 12;
        int i = 0;
        Main.tau = new byte[Main.Total_Num][32];
        Main.Diff_tau = new ArrayList<>();
        Main.ind = new HashSet[Main.Total_Num][l];
        Main.c = new byte[Main.Total_Num][l][2][32];
        Connection con;
        try {
            con = JDBCTools.getConnection();
            Statement statement = con.createStatement();
            while (i < Main.Sym_Num) {
                String sql = "SELECT Code FROM EpiOracle.Symptoms WHERE SymptomID = '%s'";
                ResultSet resultSet = statement.executeQuery(String.format(sql, i+1));
                if (resultSet.next()) {
                    String code = resultSet.getString("Code");
                    HelParGen(l, i, code, SimCheck(l, i, code));
                }
                i = i + 1;
            }
            i=Main.Sym_Num;
            while (i < Main.Total_Num) {
                String sql = "SELECT Code FROM EpiOracle.Symptoms WHERE SymptomID = '%s'";
                ResultSet resultSet = statement.executeQuery(String.format(sql, i+1));
                if (resultSet.next()){
                    String code = resultSet.getString("Code");
                    HelParGen.HelParGen(l, i, code, HelParGen.SimCheck(l, i, code));
                }
                i = i + 1;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


        ArrayList<Integer> count = new ArrayList<>();
        Map<String,Integer> map = new HashMap<>();
        for(int x = 0;x<=Main.tau.length-1;x++) {
            String str = utils.Crypto.byte2Hex(Main.tau[x]);
            if(!map.containsKey(str)) {
                map.put(str, 1);
            }
            else {
                map.put(str, map.get(str)+1);
            }
        }

        for (String s:map.keySet())
        System.out.print(map.get(s) + "\n");
    }

//    public static void main(String args[]) throws Exception {
////        byte[][] tau = new byte[2][32];
//        int l = 1;
//        Main.tau = new byte[Main.Sym_Num][32];
//        Main.Diff_tau = new ArrayList<>();
//        Main.ind = new HashSet[Main.Sym_Num][l];
//        Main.c = new byte[Main.Sym_Num][l][2][32];
//
//        String m1 = "123456781234567812";
//        String m2 = "987654323456789021123456781234567812";
//        byte[] tau1 = SimCheck(2, 0, m1);
//        HelParGen(2, 0, m1, tau1);
//        byte[] tau2 = SimCheck(2, 1, m2);
//        HelParGen(2, 0, m2, tau2);
//
////        ArrayList<Integer> count = new ArrayList<>();
//        Map<String,Integer> map = new HashMap<>();
//        for(int x = 0;x<=Main.tau.length-1;x++) {
//            String str = utils.Crypto.byte2Hex(Main.tau[x]);
//            if(!map.containsKey(str)) {
//                map.put(str, 1);
//            }
//            else {
//                map.put(str, map.get(str)+1);
//            }
//        }
//        for (String s:map.keySet())
//        System.out.print(map.get(s) + "\n");
//    }

}