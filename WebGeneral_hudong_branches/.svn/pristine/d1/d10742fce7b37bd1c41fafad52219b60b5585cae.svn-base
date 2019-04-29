package com.honghe.tech.service.impl;

import com.honghe.tech.form.UserForm;
import com.honghe.tech.httpservice.UserHttpService;
import com.honghe.tech.service.OperLogService;
import com.honghe.tech.service.UserCheckService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author caoqian
 */
@Service
public class UserCheckServiceImpl implements UserCheckService {
    Logger logger= Logger.getLogger(UserCheckServiceImpl.class);
    SimpleDateFormat sdfTimeOfMinute = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static String MODULE_NAME="登录";
    private final static String MODULE_CONTENT_SUCCESS="登录成功";
    private final static String LOG_TYPE="1";
    private final static String LOGIN_MODEL_PERMMSION="查看模块";
    private final static String USER_STATUS="1";
    @Autowired
    private UserHttpService userHttpService;
    @Autowired
    private OperLogService operLogService;

    @Override
    public UserForm userInfo(String loginName, String userPwd) {
        UserForm userForm=new UserForm();
        if (loginName == null || "".equals(loginName) || userPwd == null || "".equals(userPwd)) {
            logger.debug("用户登录失败，loginName="+loginName+",userPwd="+userPwd);
            throw new IllegalArgumentException();
        }else {
            //用户信息，验证登录
            Map<String,String> userCheck=userHttpService.checkUserLogin(loginName,userPwd);
            //用户验证成功且该用户已激活
            if(userCheck!=null && !userCheck.isEmpty() &&
                    userCheck.get("userStatus")!=null && USER_STATUS.equals(userCheck.get("userStatus"))){
                String userId=String.valueOf(userCheck.get("userId")==null?"":userCheck.get("userId"));
                //获取用户信息
                getUserInfo(userId,userForm,userCheck);
                //用户角色
                getUserRoleList(userId,userForm);
                //用户所在组织机构
                getCampusInfoByUser(userId,userForm);
                //角色权限
                getUserPermission(userId,userForm);
                //保存日志
                saveLog(userId);
            }else{
                UserForm userEmpty=new UserForm();
                return userEmpty;
            }
        }
        return userForm;
    }

    /**
     * 获取用户信息
     * @param userId     用户id
     * @param userForm   用户实体
     * @param userCheck  用户信息集合
     * @return
     */
    private UserForm getUserInfo(String userId,UserForm userForm, Map<String, String> userCheck) {
        String userRealName=userCheck.get("userRealName")==null?"":userCheck.get("userRealName").toString();
        String userName=userCheck.get("userName")==null?"":userCheck.get("userName").toString();
        String userType=userCheck.get("userType")==null?"":userCheck.get("userType").toString();
        userForm.setUserId(userId);
        userForm.setUserRealName(userRealName);
        userForm.setUserName(userName);
        userForm.setUserType(userType);
        return userForm;
    }

    /**
     * 获取用户角色
     * @param userId     用户id
     * @param userForm   用户
     * @return
     */
      private UserForm getUserRoleList(String userId,UserForm userForm){
          List<Map<String,String>> roleList=userHttpService.getUserRole(userId);
          if(roleList!=null && !roleList.isEmpty()){
              userForm.setRoleList(roleList);
          }else{
              userForm.setRoleList(null);
          }
          return userForm;
      }
    /**
     * 获取用户权限，如"系统设置,用户管理、设备管理"
     * @param userId     用户id
     * @param userForm   用户
     * @return
     */
   private UserForm getUserPermission(String userId,UserForm userForm){
       List<Map<String,String>> userPermission=userHttpService.getUserPermissionByUserId(userId);
       if(userPermission!=null && !userPermission.isEmpty()){
           userForm.setPermission(userPermission);
       }else{
           userForm.setPermission(null);
       }
       return userForm;
   }
    /**
     * 保存日志
     * @param userId 用户ID
     */
    private void saveLog(String userId){
        Map<String,Object> logParams=new HashMap<>();
        logParams.put("userId", userId);
        Date now=new Date();
        logParams.put("logTime", sdfTimeOfMinute.format(now));
        logParams.put("moduleName",MODULE_NAME);
        logParams.put("content",MODULE_CONTENT_SUCCESS);
        logParams.put("type", LOG_TYPE);
        operLogService.saveLogTable(logParams);
    }

    /**
     * 获取用户所在的组织机构信息
     * @param userId
     * @param userForm
     * @return
     */
    private UserForm getCampusInfoByUser(String userId, UserForm userForm){
        //用户所在组织机构
        Map<String,Object> userOrg=userHttpService.getAreaByUserId(userId);
        if(userOrg!=null && !userOrg.isEmpty()){
            userForm.setProvinceId(userOrg.get("provinceId")==null?"":String.valueOf(userOrg.get("provinceId")));
            userForm.setProvinceName(userOrg.get("provinceName")==null?"":String.valueOf(userOrg.get("provinceName")));
            userForm.setCityId(userOrg.get("cityId")==null?"":String.valueOf(userOrg.get("cityId")));
            userForm.setCityName(userOrg.get("cityName")==null?"":String.valueOf(userOrg.get("cityName")));
            userForm.setCityName(userOrg.get("cityName")==null?"":String.valueOf(userOrg.get("cityName")));
            userForm.setCountyId(userOrg.get("countyId")==null?"":String.valueOf(userOrg.get("countyId")));
            userForm.setCountyName(userOrg.get("countyName")==null?"":String.valueOf(userOrg.get("countyName")));
            userForm.setSchoolId(userOrg.get("schoolId")==null?"":String.valueOf(userOrg.get("schoolId")));
            userForm.setSchoolName(userOrg.get("schoolName")==null?"":String.valueOf(userOrg.get("schoolName")));
            userForm.setUserOrg(userOrg.get("childList")==null?null:(List<Map<String,String>>)userOrg.get("childList"));
        }else{
            userForm.setProvinceId("");
            userForm.setProvinceName("");
            userForm.setCityId("");
            userForm.setCityName("");
            userForm.setCityName("");
            userForm.setCountyId("");
            userForm.setCountyName("");
            userForm.setSchoolId("");
            userForm.setSchoolName("");
            userForm.setUserOrg(null);
        }
        return userForm;
    }

    @Override
    public UserForm getCampusInfo(String userId){
        UserForm userForm=new UserForm();
        if(userId!=null && !"".equals(userId)){
            Map<String,String> user=userHttpService.getUserById(Integer.parseInt(userId));
            if(user!=null && !user.isEmpty()){
                getUserInfo(userId,userForm,user);
                getUserPermission(userId,userForm);
                getCampusInfoByUser(userId,userForm);
            }
        }
        return userForm;
    }
}
