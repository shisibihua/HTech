package com.honghe.security;

import org.jasypt.util.text.BasicTextEncryptor;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghongbin
 * Date: 13-12-5
 * Time: 下午1:48
 * To change this template use File | Settings | File Templates.
 */
public final class Jasypt implements DataSecurity {

    @Override
    public String encrypt(String data) {
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(DataSecurity.class.getSimpleName());
        try {
            return basicTextEncryptor.encrypt(data);
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public String decrypt(String data) {
        try {
            BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
            basicTextEncryptor.setPassword(DataSecurity.class.getSimpleName());
            return basicTextEncryptor.decrypt(data);
        } catch (Exception e) {
            return null;
        }
    }

    public static void main(String[] args) {
        Jasypt jh = new Jasypt();
        System.out.println(jh.encrypt("2015-12-22_08-43-18"));

    }
}
