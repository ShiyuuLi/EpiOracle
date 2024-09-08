import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashSet;

public class TestRandom {
    public static void main(String[] args) throws NoSuchProviderException, NoSuchAlgorithmException {
        HashSet<Integer> set = new HashSet<>();
        for (int j=0;j<1000;j++){
            SymptomGen.randomSet(0, 35, 17, 17, set);
            for (Integer i : set){
                System.out.println(i + "\n");
            }
            System.out.println("Over!!!" + "\n\n");
        }
    }

}
