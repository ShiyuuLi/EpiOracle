import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

import utils.JDBCTools;

public class SymptomGen {
    public String[] SymptomCode;

    public static void randomSet(int min, int max, int m, int n, HashSet<Integer> set) throws NoSuchProviderException, NoSuchAlgorithmException {
        if (n > (max - min + 1) || max < min) {
            return;
        }
        for (int i = 0; i < n; i++) {
            SecureRandom generator = SecureRandom.getInstance("SHA1PRNG");
            int random = generator.nextInt(max-min+1);
//            int num = Math.round(random * (max - min) + min);
//            System.out.println("random:" + random);
//            System.out.println("num:" + num);
            set.add(random+min);
        }
        int setSize = set.size();
        if (setSize < m) {
            randomSet(min, max, m, m - setSize, set);
        }
    }

    public static void main(String[] args) throws NoSuchProviderException, NoSuchAlgorithmException {
        int max = 2000;
        String[] SymptomCode = new String[max + 1];
        HashSet<Integer> DC_randomness = new HashSet<Integer>();
        HashSet<Integer> HD_randomness = new HashSet<Integer>();
        HashSet<Integer> Ch_randomness = new HashSet<Integer>();
        HashSet<Integer> Di_randomness = new HashSet<Integer>();
        HashSet<Integer> He_randomness = new HashSet<Integer>();
        HashSet<Integer> Fa_randomness = new HashSet<Integer>();
        HashSet<Integer> ST_randomness = new HashSet<Integer>();
        HashSet<Integer> CH_randomness = new HashSet<Integer>();
        HashSet<Integer> SP_randomness = new HashSet<Integer>();
        HashSet<Integer> Dy_randomness = new HashSet<Integer>();
        HashSet<Integer> My_randomness = new HashSet<Integer>();
        HashSet<Integer> Vo_randomness = new HashSet<Integer>();
        HashSet<Integer> Na_randomness = new HashSet<Integer>();
        HashSet<Integer> Fe_randomness = new HashSet<Integer>();

        HashSet<Integer> An_randomness = new HashSet<Integer>();
        HashSet<Integer> SA_randomness = new HashSet<Integer>();
        HashSet<Integer> DS_randomness = new HashSet<Integer>();
        HashSet<Integer> GH_randomness = new HashSet<Integer>();
        HashSet<Integer> Hy_randomness = new HashSet<Integer>();
        HashSet<Integer> PC_randomness = new HashSet<Integer>();

//        String Noise_String[] = new String[2000];
//        HashSet<Integer>[] Noise = new HashSet[2000];
//        for (int i=0;i<2000;i++){
//            Noise[i] = new HashSet<>();
//            randomSet(100000000, 999999999, 4, 4, Noise[i]);
//            String str = Noise[i].toString();
//            str = str.replace(" ", "");
//            str = str.substring(1, str.length()-1);
//            Noise_String[i] = str.replace(",", "");
//        }

        randomSet(1, max, (int) Math.round(0.677 * max), (int) Math.round(0.677 * max), DC_randomness);
        randomSet(1, max, (int) Math.round(0.136 * max), (int) Math.round(0.136 * max), HD_randomness);
        randomSet(1, max, (int) Math.round(0.114 * max), (int) Math.round(0.114 * max), Ch_randomness);
        randomSet(1, max, (int) Math.round(0.037 * max), (int) Math.round(0.037 * max), Di_randomness);
        randomSet(1, max, (int) Math.round(0.009 * max), (int) Math.round(0.009 * max), He_randomness);
        randomSet(1, max, (int) Math.round(0.381 * max), (int) Math.round(0.381 * max), Fa_randomness);
        randomSet(1, max, (int) Math.round(0.139 * max), (int) Math.round(0.139 * max), ST_randomness);
        randomSet(1, max, (int) Math.round(0.008 * max), (int) Math.round(0.008 * max), CH_randomness);
        randomSet(1, max, (int) Math.round(0.334 * max), (int) Math.round(0.334 * max), SP_randomness);
        randomSet(1, max, (int) Math.round(0.186 * max), (int) Math.round(0.186 * max), Dy_randomness);
        randomSet(1, max, (int) Math.round(0.148 * max), (int) Math.round(0.148 * max), My_randomness);
        randomSet(1, max, (int) Math.round(0.05 * max), (int) Math.round(0.05 * max), Vo_randomness);
        randomSet(1, max, (int) Math.round(0.048 * max), (int) Math.round(0.048 * max), Na_randomness);
        randomSet(1, max, (int) Math.round(0.879 * max), (int) Math.round(0.879 * max), Fe_randomness);

        randomSet(2001, 4000, (int) Math.round(0.879 * max), (int) Math.round(0.879 * max), Hy_randomness);
        randomSet(2001, 4000, (int) Math.round(0.334 * max), (int) Math.round(0.334 * max), SA_randomness);
        randomSet(2001, 4000, (int) Math.round(0.148 * max), (int) Math.round(0.348 * max), DS_randomness);
        randomSet(2001, 4000, (int) Math.round(0.381 * max), (int) Math.round(0.381 * max), GH_randomness);
        randomSet(2001, 4000, (int) Math.round(0.186 * max), (int) Math.round(0.386 * max), An_randomness);
        randomSet(2001, 4000, (int) Math.round(0.677 * max), (int) Math.round(0.677 * max), PC_randomness);
//        System.out.println(DC_randomness);
        Connection con;
        try {
            con = JDBCTools.getConnection();
            if (!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            else {
                System.out.println("Failure connection!");
            }
            Statement statement = con.createStatement();
            for (int i2 : HD_randomness) {
                String HD_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'Headache;'),Code= concat(Code,'022253000025064002') Where SymptomID = " + "'%s'";
                statement.executeUpdate(String.format(HD_sql, i2));
                SymptomCode[i2] = SymptomCode[i2] + "022253000025064002";
//                String HD_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'Headache;'),Code= concat(Code,'025064002') Where SymptomID = " + "'%s'";
//                statement.executeUpdate(String.format(HD_sql, i2));
//                SymptomCode[i2] = SymptomCode[i2] + "025064002";
            }
            for (int i11 : My_randomness) {
                String My_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'Myalgia;'),Code= concat(Code,'022253000404640003') Where SymptomID = " + "'%s'";
                statement.executeUpdate(String.format(My_sql, i11));
                SymptomCode[i11] = SymptomCode[i11] + "022253000404640003";
//                String My_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'Myalgia;'),Code= concat(Code,'404640003') Where SymptomID = " + "'%s'";
//                statement.executeUpdate(String.format(My_sql, i11));
//                SymptomCode[i11] = SymptomCode[i11] + "404640003";
            }
            for (int i5 : He_randomness) {
                String He_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'Hemoptysis;'),Code= concat(Code,'022253000066857006') Where SymptomID = " + "'%s'";
                statement.executeUpdate(String.format(He_sql, i5));
                SymptomCode[i5] = SymptomCode[i5] + "022253000066857006";
//                String He_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'Hemoptysis;'),Code= concat(Code,'066857006') Where SymptomID = " + "'%s'";
//                statement.executeUpdate(String.format(He_sql, i5));
//                SymptomCode[i5] = SymptomCode[i5] + "066857006";
            }
            for (int i1 : DC_randomness) {
                String DC_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'DryCough;'),Code= concat(Code,'049727002011833005') Where SymptomID = " + "'%s'";
                statement.executeUpdate(String.format(DC_sql, i1));
                SymptomCode[i1] = SymptomCode[i1] + "049727002011833005";
//                String DC_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'DryCough;'),Code= concat(Code,'011833005') Where SymptomID = " + "'%s'";
//                statement.executeUpdate(String.format(DC_sql, i1));
//                SymptomCode[i1] = SymptomCode[i1] + "011833005";
//                if(statement.executeUpdate(String.format(DC_sql, i))!=0)
//                    System.out.println("Update Succeeds");
//                else
//                    System.out.println("Update Fails");
            }
            for (int i6 : Fa_randomness) {
                String Fa_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'Fatigue;'),Code= concat(Code,'105721009084229001') Where SymptomID = " + "'%s'";
                statement.executeUpdate(String.format(Fa_sql, i6));
                SymptomCode[i6] = SymptomCode[i6] + "105721009084229001";
//                String Fa_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'Fatigue;'),Code= concat(Code,'084229001') Where SymptomID = " + "'%s'";
//                statement.executeUpdate(String.format(Fa_sql, i6));
//                SymptomCode[i6] = SymptomCode[i6] + "084229001";
            }
            for (int i14 : Fe_randomness) {
                String Fe_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'Fever;'),Code= concat(Code,'123979008386661006') Where SymptomID = " + "'%s'";
                statement.executeUpdate(String.format(Fe_sql, i14));
                SymptomCode[i14] = SymptomCode[i14] + "123979008386661006";
//                String Fe_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'Fever;'),Code= concat(Code,'386661006') Where SymptomID = " + "'%s'";
//                statement.executeUpdate(String.format(Fe_sql, i14));
//                SymptomCode[i14] = SymptomCode[i14] + "386661006";
            }
            for (int i10 : Dy_randomness) {
                String Dy_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'Dyspnea;'),Code= concat(Code,'230145002267036007') Where SymptomID = " + "'%s'";
                statement.executeUpdate(String.format(Dy_sql, i10));
                SymptomCode[i10] = SymptomCode[i10] + "230145002267036007";
//                String Dy_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'ShortnessOfBreath;'),Code= concat(Code,'267036007') Where SymptomID = " + "'%s'";
//                statement.executeUpdate(String.format(Dy_sql, i10));
//                SymptomCode[i10] = SymptomCode[i10] + "267036007";
            }
            for (int i3 : Ch_randomness) {
                String Ch_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'Chill;'),Code= concat(Code,'248456009043724002') Where SymptomID = " + "'%s'";
                statement.executeUpdate(String.format(Ch_sql, i3));
                SymptomCode[i3] = SymptomCode[i3] + "248456009043724002";
//                String Ch_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'Chill;'),Code= concat(Code,'043724002') Where SymptomID = " + "'%s'";
//                statement.executeUpdate(String.format(Ch_sql, i3));
//                SymptomCode[i3] = SymptomCode[i3] + "043724002";
            }
            for (int i8 : CH_randomness) {
                String CH_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'ConjunctivalHyperemia;'),Code= concat(Code,'246875002193894004') Where SymptomID = " + "'%s'";
                statement.executeUpdate(String.format(CH_sql, i8));
                SymptomCode[i8] = SymptomCode[i8] + "246875002193894004";
//                String CH_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'ConjunctivalHyperemia;'),Code= concat(Code,'193894004') Where SymptomID = " + "'%s'";
//                statement.executeUpdate(String.format(CH_sql, i8));
//                SymptomCode[i8] = SymptomCode[i8] + "193894004";
            }
            for (int i12 : Vo_randomness) {
                String Vo_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'Vomiting;'),Code= concat(Code,'300359004422400008') Where SymptomID = " + "'%s'";
                statement.executeUpdate(String.format(Vo_sql, i12));
                SymptomCode[i12] = SymptomCode[i12] + "300359004422400008";
//                String Vo_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'Vomiting;'),Code= concat(Code,'422400008') Where SymptomID = " + "'%s'";
//                statement.executeUpdate(String.format(Vo_sql, i12));
//                SymptomCode[i12] = SymptomCode[i12] + "422400008";
            }
            for (int i4 : Di_randomness) {
                String Di_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'Diarrhea;'),Code= concat(Code,'300373008062315008') Where SymptomID = " + "'%s'";
                statement.executeUpdate(String.format(Di_sql, i4));
                SymptomCode[i4] = SymptomCode[i4] + "300373008062315008";
//                String Di_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'Diarrhea;'),Code= concat(Code,'062315008') Where SymptomID = " + "'%s'";
//                statement.executeUpdate(String.format(Di_sql, i4));
//                SymptomCode[i4] = SymptomCode[i4] + "062315008";
            }
            for (int i7 : ST_randomness) {
                String ST_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'SoreThroat;'),Code= concat(Code,'301365009162397003') Where SymptomID = " + "'%s'";
                statement.executeUpdate(String.format(ST_sql, i7));
                SymptomCode[i7] = SymptomCode[i7] + "301365009162397003";
//                String ST_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'SoreThroat;'),Code= concat(Code,'162397003') Where SymptomID = " + "'%s'";
//                statement.executeUpdate(String.format(ST_sql, i7));
//                SymptomCode[i7] = SymptomCode[i7] + "162397003";
            }
            for (int i13 : Na_randomness) {
                String Na_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'Nausea;'),Code= concat(Code,'386617003422587007') Where SymptomID = " + "'%s'";
                statement.executeUpdate(String.format(Na_sql, i13));
                SymptomCode[i13] = SymptomCode[i13] + "386617003422587007";
//                String Na_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'Nausea;'),Code= concat(Code,'422587007') Where SymptomID = " + "'%s'";
//                statement.executeUpdate(String.format(Na_sql, i13));
//                SymptomCode[i13] = SymptomCode[i13] + "422587007";
            }
            for (int i9 : SP_randomness) {
                String SP_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'SputumProduction;'),Code= concat(Code,'404684003248599002') Where SymptomID = " + "'%s'";
                statement.executeUpdate(String.format(SP_sql, i9));
                SymptomCode[i9] = SymptomCode[i9] + "404684003248599002";
//                String SP_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'SputumProduction;'),Code= concat(Code,'248599002') Where SymptomID = " + "'%s'";
//                statement.executeUpdate(String.format(SP_sql, i9));
//                SymptomCode[i9] = SymptomCode[i9] + "248599002";
            }

            for (int j1 : SA_randomness) {
//                String SP_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'SputumProduction;'),Code= concat(Code,'404684003248599002') Where SymptomID = " + "'%s'";
//                statement.executeUpdate(String.format(SP_sql, i9));
//                SymptomCode[i9] = SymptomCode[i9] + "404684003248599002";
                String SA_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'StomachAche;'),Code= concat(Code, '022253000271681002') Where SymptomID = " + "'%s'";
                statement.executeUpdate(String.format(SA_sql, j1));
            }
            for (int j2 : DS_randomness) {
//                String SP_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'SputumProduction;'),Code= concat(Code,'404684003248599002') Where SymptomID = " + "'%s'";
//                statement.executeUpdate(String.format(SP_sql, i9));
//                SymptomCode[i9] = SymptomCode[i9] + "404684003248599002";
                String DS_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'DirtySputum;'),Code= concat(Code, '404684003248605009') Where SymptomID = " + "'%s'";
                statement.executeUpdate(String.format(DS_sql, j2));
            }
            for (int j3 : An_randomness) {
//                String SP_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'SputumProduction;'),Code= concat(Code,'404684003248599002') Where SymptomID = " + "'%s'";
//                statement.executeUpdate(String.format(SP_sql, i9));
//                SymptomCode[i9] = SymptomCode[i9] + "404684003248599002";
                String An_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'Angina;'),Code= concat(Code,'022253000194828000') Where SymptomID = " + "'%s'";
                statement.executeUpdate(String.format(An_sql, j3));
            }
            for (int j4 : GH_randomness) {
//                String SP_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'SputumProduction;'),Code= concat(Code,'404684003248599002') Where SymptomID = " + "'%s'";
//                statement.executeUpdate(String.format(SP_sql, i9));
//                SymptomCode[i9] = SymptomCode[i9] + "404684003248599002";
                String GH_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'GenusHerpestes;'),Code= concat(Code, '388768001388771009') Where SymptomID = " + "'%s'";
                statement.executeUpdate(String.format(GH_sql, j4));
            }
            for (int j5 : Hy_randomness) {
//                String SP_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'SputumProduction;'),Code= concat(Code,'404684003248599002') Where SymptomID = " + "'%s'";
//                statement.executeUpdate(String.format(SP_sql, i9));
//                SymptomCode[i9] = SymptomCode[i9] + "404684003248599002";
                String Hy_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'Hypothermia;'),Code= concat(Code, '123979008386689009') Where SymptomID = " + "'%s'";
                statement.executeUpdate(String.format(Hy_sql, j5));
            }
            for (int j6 : PC_randomness) {
//                String SP_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'SputumProduction;'),Code= concat(Code,'404684003248599002') Where SymptomID = " + "'%s'";
//                statement.executeUpdate(String.format(SP_sql, i9));
//                SymptomCode[i9] = SymptomCode[i9] + "404684003248599002";
                String PC_sql = "UPDATE EpiOracle.Symptoms SET Symptom= concat(Symptom,'ProductiveCough ;'),Code= concat(Code, '049727002028743005') Where SymptomID = " + "'%s'";
                statement.executeUpdate(String.format(PC_sql, j6));
            }
            JDBCTools.close(statement);
            JDBCTools.close(con);
        }catch(Exception e){
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}