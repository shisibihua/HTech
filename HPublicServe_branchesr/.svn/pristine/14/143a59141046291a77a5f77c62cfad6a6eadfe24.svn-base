package com.honghe.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by zhanghongbin on 2016/12/13.
 */
public final class ProcessUtil {


    private ProcessUtil() {

    }


    public final static boolean execute(String command) {
        BufferedReader bufferedReader = null;
        try {
            Process pro = Runtime.getRuntime().exec(command);
            bufferedReader = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            while (bufferedReader.readLine() != null) ;
            try {
                pro.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            pro.exitValue();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                bufferedReader.close();
            } catch (Exception e) {

            }
        }
        return true;
    }
}
