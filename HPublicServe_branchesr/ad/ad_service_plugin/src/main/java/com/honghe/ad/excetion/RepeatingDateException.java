package com.honghe.ad.excetion;/**
 * Created by lyx on 2016-11-28.
 */

/**
 * 数据重复错误
 *
 * @author lyx
 * @create 2016-11-28 16:10
 **/
public class RepeatingDateException extends Exception {
    public RepeatingDateException() {
        super("数据重复");
    }

    public RepeatingDateException(String str) {
        super("数据重复"+ str);
    }
}
