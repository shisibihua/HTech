package com.honghe.security;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghongbin
 * Date: 13-12-5
 * Time: 下午1:32
 * To change this template use File | Settings | File Templates.
 */
public interface DataSecurity {

    public String encrypt(String data);

    public String decrypt(String data);
}
