package com.huobanplus.erpservice.common.util; /**
 * Created by Administrator on 2015/6/24.
 */

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

public class DxDESCipher {
    public static final String DEFAULT_KEY = "69a23e06215920c5fa8108cf218f3d6a";
    private static final String ALGORITHM = "DES";
    private Cipher encryptCipher = null;
    private Cipher decryptCipher = null;

    public DxDESCipher() throws Exception {
        this(DEFAULT_KEY);
    }

    public DxDESCipher(String strKey) throws Exception {
        Key key = getKey(strKey.getBytes());
        encryptCipher = Cipher.getInstance(ALGORITHM);
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        decryptCipher = Cipher.getInstance(ALGORITHM);
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    private Key getKey(byte[] bytes) {
        byte[] bytesKey = new byte[8];
        for (int i = 0; i < bytes.length && i < bytesKey.length; i++) {
            bytesKey[i] = bytes[i];
        }
        return new SecretKeySpec(bytesKey, ALGORITHM);
    }

    public static String byteArrayToHexString(byte[] bytes) throws Exception {
        int length = bytes.length;
        StringBuffer sb = new StringBuffer(length * 2);
        for (int i = 0; i < length; i++) {
            int tempData = bytes[i];
            while (tempData < 0) {
                tempData += 256;
            }
            if (tempData < 16) {
                sb.append("0");
            }
            sb.append(Integer.toString(tempData, 16));
        }
        return sb.toString().toUpperCase();
    }

    public static byte[] hexStringToByteArray(String str) throws Exception {
        byte[] bytes = str.getBytes();
        int length = bytes.length;
        byte[] resultArray = new byte[length / 2];
        for (int i = 0; i < length; i = i + 2) {
            String strTmp = new String(bytes, i, 2);
            resultArray[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return resultArray;
    }

    public byte[] encryptDES(byte[] bytes) throws Exception {
        return encryptCipher.doFinal(bytes);
    }

    public String encryptDES(String str) throws Exception {
        return byteArrayToHexString(encryptDES(str.getBytes()));
    }

    public byte[] decryptDES(byte[] bytes) throws Exception {
        return decryptCipher.doFinal(bytes);
    }

    public String decryptDES(String str) throws Exception {
        return new String(decryptDES(hexStringToByteArray(str)));
    }

    public static String encrypt(String plaintext, String key) {
        try {
            DxDESCipher des = new DxDESCipher(key);
            return des.encryptDES(plaintext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String ciphertext, String key) {
        try {
            DxDESCipher des = new DxDESCipher(key);
            return des.decryptDES(ciphertext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String encrypt(String plaintext) {
        try {
            DxDESCipher des = new DxDESCipher();
            return des.encryptDES(plaintext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String ciphertext) {
        try {
            DxDESCipher des = new DxDESCipher();
            return des.decryptDES(ciphertext);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(DxDESCipher.encrypt("123456", DxDESCipher.DEFAULT_KEY));
        System.out.println(DxDESCipher.encrypt("123Duijie/", DxDESCipher.DEFAULT_KEY));
        //  System.out.println(DxDESCipher.decrypt("98CE28B3D3287B72"));
    }
}
