package com.honghe.ad.campus.bean;

import java.util.List;
import java.util.Map;

/**
 * Created by hh on 2016/12/7.
 */
public class CampusUserCount {
    private String total;
    private List<Map<String,String>> campusInfo;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Map<String, String>> getCampusInfo() {
        return campusInfo;
    }

    public void setCampusInfo(List<Map<String, String>> campusInfo) {
        this.campusInfo = campusInfo;
    }
}
