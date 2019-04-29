package com.honghe.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghongbin
 * Date: 13-12-5
 * Time: 下午1:33
 * To change this template use File | Settings | File Templates.
 */
public final class AES implements DataSecurity {
    final static String IV = "AAAAAAAAAAAAAAAA";
    final static String encryptionKey = "0123456789abcdef";

    @Override
    public String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] raw = encryptionKey.getBytes();
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            byte[] encrypted = cipher.doFinal(data.getBytes("utf-8"));
            return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码。
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public String decrypt(String data) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(encryptionKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(IV.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(data);//先用base64解密
            byte[] original = cipher.doFinal(encrypted1);
            return new String(original, "utf-8");
        } catch (Exception e) {

        }
        return null;
    }

    public static void main(String[] args) {
        AES a = new AES();
        String w = a.encrypt("w2rwer");
        System.out.println(w);
        System.out.println(a.decrypt(w));
    }

}
