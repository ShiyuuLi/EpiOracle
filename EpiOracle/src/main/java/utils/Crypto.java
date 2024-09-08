package utils;

//import it.unisa.dia.gas.jpbc.Element;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.*;
import java.util.Random;

public class Crypto {

    public static byte[] getHash(String mode, byte[] m) {
        byte[] hash_value = null;

        try {
            MessageDigest md = MessageDigest.getInstance(mode);
            hash_value = md.digest(m);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return hash_value;
    }

    public static String byte2Hex(byte[] bytes) throws Exception{
        StringBuffer stringBuffer = new StringBuffer();
        for (int i=0;i<bytes.length;i++){
            String temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length()==1){
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }


    public static byte[] AESEncrypt(byte[] key, byte[] data) {
        String key_algorithm = "AES";
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES");
            Key key1 = initKeyForAES(new String(key));
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key1.getEncoded(), key_algorithm));
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException | InvalidKeyException |
                NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static byte[] AESDecrypt(byte[] key, byte[] data) {
        String key_algorithm = "AES";
        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES");
            Key key1 = initKeyForAES(new String(key));
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key1.getEncoded(), key_algorithm));
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            return null;
        }
    }

    private static Key initKeyForAES(String key) throws NoSuchAlgorithmException {
        if (null == key || key.length() == 0) {
            throw new NullPointerException("key not is null");
        }
        SecretKeySpec key2;
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(key.getBytes());
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            key2 = new SecretKeySpec(enCodeFormat, "AES");
        } catch (NoSuchAlgorithmException ex) {
            throw new NoSuchAlgorithmException();
        }
        return key2;
    }
}
