package com.honghe.security;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghongbin
 * Date: 13-12-5
 * Time: 下午1:43
 * To change this template use File | Settings | File Templates.
 */
public final class Base64 implements DataSecurity {
    @Override
    public String encrypt(String data) {
        if (data == null) return null;
        try {
            return (new sun.misc.BASE64Encoder()).encode(data.getBytes());
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public String decrypt(String data) {
        return null;
    }
}
