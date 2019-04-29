package com.honghe.campus.loadbalance;

import java.util.List;

/**
 * Created by zhanghongbin on 2016/9/30.
 */
public final class RandomLoadBanlance implements LoadBalance {

    private List<String> ipList;

    public RandomLoadBanlance(List<String> ipList) {
        this.ipList = ipList;
    }

    @Override
    public String get() {
        java.util.Random random = new java.util.Random();
        int randomPos = random.nextInt(ipList.size());
        return ipList.get(randomPos);
    }

}