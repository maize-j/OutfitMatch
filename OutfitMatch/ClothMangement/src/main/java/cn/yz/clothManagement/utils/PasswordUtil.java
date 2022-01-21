package cn.yz.clothManagement.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.Key;

/**
 * TODO
 *
 * @author 苞谷洁子
 * @ClassName PasswordUtil
 * @date 2022/1/12 16:31
 */
public class PasswordUtil {

    /**
     * 定义使用的算法为:PBEWITHMD5andDES算法
     */
    public static final String ALGORITHM = "PBEWithMD5AndDES";//加密算法

    /**
     * 定义迭代次数为1000次
     */
    private static final int ITERATIONCOUNT = 1000;

    /**
     * 根据PBE密码生成一把密钥
     *
     * @param password
     *            生成密钥时所使用的密码
     * @return Key PBE算法密钥
     * */
    private static Key getPBEKey(String password) {

        // 实例化使用的算法
        SecretKeyFactory keyFactory;
        SecretKey secretKey = null;
        try {
            keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            // 设置PBE密钥参数
            PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
            // 生成密钥
            secretKey = keyFactory.generateSecret(keySpec);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return secretKey;
    }

    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param src
     *            字节数组
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    /**
     * 加密明文字符串
     *
     * @param plaintext
     *            待加密的明文字符串
     * @param password
     *            生成密钥时所使用的密码
     * @param salt
     *            盐值
     * @return 加密后的密文字符串
     * @throws Exception
     */
    public static String encrypt(String plaintext, String password, String salt) {

        Key key = getPBEKey(password);
        byte[] encipheredData = null;
        PBEParameterSpec parameterSpec = new PBEParameterSpec(salt.getBytes(), ITERATIONCOUNT);
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);

            cipher.init(Cipher.ENCRYPT_MODE, key, parameterSpec);
            //update-begin-author:sccott date:20180815 for:中文作为用户名时，加密的密码windows和linux会得到不同的结果 gitee/issues/IZUD7
            encipheredData = cipher.doFinal(plaintext.getBytes("utf-8"));
            //update-end-author:sccott date:20180815 for:中文作为用户名时，加密的密码windows和linux会得到不同的结果 gitee/issues/IZUD7
        } catch (Exception e) {
        }
        return bytesToHexString(encipheredData);
    }

}
