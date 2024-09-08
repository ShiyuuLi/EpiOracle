package utils;

import java.util.ArrayList;
import java.util.Arrays;

public class Permutation {
    public static ArrayList<String> m_perm;
    public static void perm(String[] buf, int start, int end) {
        if (start == end) {
//            m_perm.add(new String[buf.length]);
//            System.out.println(Arrays.toString(buf));
            String str = new String();
            for (int x = 0;x<buf.length;x++){
                str+=buf[x];
            }
            m_perm.add(str);
            return;
//            for (int x = 0; x <= end; x++) {
//                m_perm.get(c)[x] = buf[x];
////                m_perm.set(c, buf[i]);
////                System.out.print(buf[i]);
//            }
//            String ReOrderM = "";
//            for (int b = 0; b < m_perm.get(c).length; b++) {
//                ReOrderM += m_perm.get(c)[b];
//            }
//            c++;
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

    public static void main(String[] args){
        m_perm = new ArrayList<>();
        String[] m = {"11111111111111111;","22222222222222222;","33333333333333333;"};
        perm(m, 0, m.length-1);
        for (String i:m_perm){
            System.out.println(i);
        }
    }
}
