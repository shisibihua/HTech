package com.honghe.sys.controller;


import com.honghe.exception.ParamException;
import com.honghe.permissions.bean.Permissions;
import com.honghe.permissions.bean.SysPermissions;
import com.honghe.sys.dao.Sys2PermissionsDao;
import com.honghe.sys.dao.SysDao;
import com.honghe.user.util.JsonUtil;
import com.honghe.user.util.StringUtil;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.Properties;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

/**
 * Created by hh on 2016/12/1.
 */
public class SysController {
    private static final Logger logger = Logger.getLogger(SysController.class);
    private SysDao sysDao = new SysDao();
    private Sys2PermissionsDao sys2PermissionsDao= new Sys2PermissionsDao();
    public List<Map<String,String>> sysSearch() throws Exception{
        return sysDao.find();
    }
    public Permissions sys2permissionsSearch(String token) throws Exception{
        if(token==null || "".equals(token)){
            throw new ParamException();
        }
        return sys2PermissionsDao.find(token);
    }
    public SysPermissions sysUserPermissionsSearch(String userId, String pId, String token) throws Exception{
        if (userId==null || "".equals(userId) || token==null) {
           throw new ParamException();
        }
        int parentId = -1;
        if(pId!=null && !"".equals(pId)){
            try{
                parentId = Integer.valueOf(pId);
            }catch (ClassCastException e){
                throw new ParamException();
            }
            if (parentId < 0) {
                throw new ParamException();
            }
        }
        if(parentId < 0){
            return sys2PermissionsDao.find(userId,token);
        }else{
            return sys2PermissionsDao.find(userId,token,parentId);
        }

    }
    public boolean sysSetModuleInfo(String moduleStr)
    {
        boolean reValue = false;
        JSONObject jsonObject = JSONObject.fromObject(URLDecoder.decode(moduleStr));
        JSONArray json = jsonObject.getJSONArray("moduleStr");
        List<Map<String, String>> moduleInfo = (List)JsonUtil.jsonToList(json);
        if (!moduleInfo.isEmpty()) {
            try
            {
                this.sysDao.setModuleInfo(moduleInfo);
                reValue = true;
                logger.debug("模块信息存储成功,mouduleStr为：" + moduleStr);
            }
            catch (Exception e)
            {
                logger.error("模块安装信息插入异常", e);
            }
        }
        return reValue;
    }

    public JSONArray sysGetAllEnableModule(String userId, String flag)
    {
        List<Map<String, String>> allModuleInfo = new ArrayList();
        List<Map<String, String>> moduleInfo = new ArrayList();
        String moduleName = "";
        try
        {
            Properties pro = new Properties();
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            InputStream inStr = cl.getResourceAsStream("modulePosition.properties");
            pro.load(new InputStreamReader(inStr, "UTF-8"));
            if ((this.sysDao.isExistSetting(userId, moduleName)) && (!"0".equals(flag)))
            {
                moduleInfo = this.sysDao.getModuleInfo(userId);
                logger.debug("数据库获取用户位置信息成功!位置信息为：" + moduleInfo.toString());
                for (Map moduleMap : moduleInfo)
                {
                    String name = moduleMap.get("name").toString();
                    Iterator it = pro.entrySet().iterator();
                    while (it.hasNext())
                    {
                        Map.Entry entry = (Map.Entry)it.next();
                        String key = (String)entry.getKey();
                        String mName = key.replaceAll("[^a-zA-Z]", "");
                        if (name.equals(mName))
                        {
                            String value = entry.getValue().toString();
                            String[] modules = value.split(",");
                            moduleMap.put("url", modules[5]);
                        }
                    }
                }
                logger.debug("获取配置文件中模块链接信息成功!信息为：" + moduleInfo.toString());
                allModuleInfo = getEnableModule(userId, moduleInfo);
            }
            else
            {
                Iterator it = pro.entrySet().iterator();
                while (it.hasNext())
                {
                    Map moduleMap = new HashMap();
                    Map.Entry entry = (Map.Entry)it.next();
                    String key = (String)entry.getKey();
                    String mName = key.replaceAll("[^a-zA-Z]", "");
                    String value = (String)entry.getValue();
                    String[] module = value.split(",");
                    moduleMap.put("name", mName);
                    moduleMap.put("rowIndex", module[0]);
                    moduleMap.put("colIndex", module[1]);
                    moduleMap.put("rowSize", module[2]);
                    moduleMap.put("colSize", module[3]);
                    moduleMap.put("mdesc", module[4]);
                    moduleMap.put("url", module[5]);
                    moduleInfo.add(moduleMap);
                }
                logger.debug("获取配置文件信息成功！配置文件信息为：" + moduleInfo.toString());
                allModuleInfo = getEnableModule(userId, moduleInfo);
                if (!"0".equals(flag))
                {
                    this.sysDao.setModulePositionInfo(moduleInfo, userId);
                    logger.debug("默认入口位置信息插入成功!插入的信息为：" + moduleInfo.toString());
                }
            }
        }
        catch (Exception e)
        {
            logger.error("获取入口位置信息失败", e);
        }
        return JSONArray.fromObject(allModuleInfo);
    }

    public boolean sysSetModulePositionInfo(String moduleStr, String userId)
    {
        boolean flag = false;
        try
        {
            if ((!StringUtil.isEmpty(userId)) && (!StringUtil.isEmpty(moduleStr)))
            {
                JSONArray json = JSONArray.fromObject(URLDecoder.decode(moduleStr, "utf-8"));
                List<Map<String, String>> moduleInfo = (List) JsonUtil.jsonToList(json);
                this.sysDao.setModulePositionInfo(moduleInfo, userId);
                flag = true;
                logger.debug("设置用户入口位置信息成功" + moduleStr);
            }
        }
        catch (Exception e)
        {
            logger.debug("设置用户入口位置信息失败" + moduleStr, e);
        }
        return flag;
    }

    private List getEnableModule(String userId, List moduleInfo)
    {
        List<Map<String, String>> allModuleInfo = new ArrayList();
        try
        {
            List permName = this.sysDao.getPermissionedModule(userId);

            List instName = this.sysDao.getInstalledModule();
            for (Object module : moduleInfo)
            {
                Map moduleMap = (Map)module;
                String name = moduleMap.get("name").toString();
                if ("1".equals(userId)) {
                    moduleMap.put("isPermission", Boolean.valueOf(true));
                } else {
                    moduleMap.put("isPermission", Boolean.valueOf(getModulePermissed(permName, name)));
                }
                moduleMap.put("isInstall", Boolean.valueOf(getModuleInstalled(instName, name)));
                allModuleInfo.add(moduleMap);
            }
            logger.debug("模块安装信息处理成功，返回查询结果");
        }
        catch (Exception e)
        {
            logger.error("获取模块安装信息异常", e);
        }
        return allModuleInfo;
    }

    private boolean getModulePermissed(List permName, String name)
    {
        boolean isPermission = false;
        List permNameList = new ArrayList();
        for (Object pname : permName)
        {
            Map nameMap = (Map)pname;
            permNameList.add(nameMap.get("sys_name"));
        }
        if (permNameList.contains(name)) {
            isPermission = true;
        }
        return isPermission;
    }

    private boolean getModuleInstalled(List instName, String name)
    {
        boolean isInstall = false;
        List installNameList = new ArrayList();
        for (Object iname : instName)
        {
            Map nameMap = (Map)iname;
            installNameList.add(nameMap.get("m_name"));
        }
        if (installNameList.contains(name)) {
            isInstall = true;
        }
        return isInstall;
    }

    public Map<String, String> sysServiceState()
    {
        Map<String, String> map = new HashMap();
        try
        {
            map.put("RunningState", "1");
            map.put("CpuUsage", "");
            map.put("MemoryUsage", "");
        }
        catch (Exception e)
        {
            logger.debug("用户服务未运行！", e);
        }
        return map;
    }
}
