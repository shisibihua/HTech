package com.honghe.tech.httpservice.impl;

import com.honghe.service.proxy.Result;
import com.honghe.tech.htechexception.HtechParamException;
import com.honghe.tech.httpservice.AdHttpService;
import com.honghe.tech.util.HttpServerUtil;
import com.honghe.tech.util.ParamUtil;
import com.honghe.tech.util.exceptionutil.HttpServerException;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * Created by caoqian
 *
 * @author HOUJT
 */
@Service
public class AdHttpServiceImpl implements AdHttpService {
    private Logger logger = Logger.getLogger(AreaHttpServiceImpl.class);

    /**
     * 返回数据解析
     * {
     * "rootCampusId": "A0000001",       根目录组织机构id
     * "rootCampusName": "保定市",       根目录组织机构名称
     * "typeId": "city",                 根目录组织机构类型：province-省   city-市  area-地区/县
     * "areaId": "10",                   保定市对应的hat_city表中的id
     * }
     *
     * @return map
     * @author caoqian
     */
    @Override
    public Map<String, String> getCampusRoot() throws HttpServerException, HtechParamException {
        Map<String, String> resultMap = new HashMap<>();
        Map<String, String> map = this.getResultMap();
        String campusTypeName = null;
        String areaId = "";
        String typeId = map.get("typeId");
        String rootCampusId = map.get("rootCampusId");
        String rootCampusName = map.get("rootCampusName");
        switch (typeId) {
            //机构类型
            case "1":
                campusTypeName = "province";
                areaId = map.get("provinceId");
                break;
            case "2":
                campusTypeName = "city";
                areaId = map.get("cityId");
                break;
            case "3":
                campusTypeName = "area";
                areaId = map.get("areaId");
                break;
            default:
                break;
        }
        if (ParamUtil.paramNull(typeId, rootCampusId, rootCampusName, areaId)) {
            throw new HtechParamException("数据异常！");
        }
        resultMap.put("typeId", campusTypeName);
        resultMap.put("areaId", areaId);
        resultMap.put("rootCampusId", rootCampusId);
        resultMap.put("rootCampusName", rootCampusName);
        return resultMap;
    }

    private Map<String, String> getResultMap() throws HttpServerException {
        Map<String, String> map = null;
        Map<String, String> params = new HashMap<>();
        Result result = HttpServerUtil.adService("campusFindCampusRoot", params);
        if (result.getCode() == 0 && result.getValue() != null) {
            map = (Map<String, String>) result.getValue();
        }
        return map;
    }
}
