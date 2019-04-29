package com.honghe.web.user.service.campus;

import com.honghe.service.client.Result;
import jodd.joy.page.PageData;
import org.junit.Test;

import java.util.Map;

public class CampusServiceTest {

//    CampusService campusService =new CampusService();
//    @Test
//    public void testDeleteUser() throws Exception {
//        String campusId = "1";
//        String userId = "1";
//        Result result = campusService.deleteUser(campusId, userId);
//        System.out.println(result.getCode()+" "+result.getValue());//code 0为正确,-1参数错误,-2没有此方法 result true为正确 false为错误
//
//    }
//
//    @Test
//    public void testAddCampus() throws Exception {
//        String pId="1";
//        String name="123";
//        Result result = campusService.addCampus(pId,name);
//        System.out.println(result.getCode()+" "+result.getValue());//code 0为正确,-1参数错误,-2没有此方法 result true为正确 false为错误
//    }
//
//    @Test
//    public void testUpdateCampus() throws Exception {
//        String id="1";
//        String name="123";
//        Result result = campusService.updateCampus(id,name);
//        System.out.println(result.getCode()+" "+result.getValue());//code 0为正确,-1参数错误,-2没有此方法 result true为正确 false为错误
//    }
//
//    @Test
//    public void testDeleteCampus() throws Exception {
//        String id="1";
//        Result result = campusService.deleteCampus(id);
//        System.out.println(result.getCode()+" "+result.getValue());//code 0为正确,-1参数错误,-2没有此方法 result true为正确 false为错误
//
//    }
//
//    @Test
//    public void testAllocateUser() throws Exception {
//        String campusId = "1";
//        String userId ="5";
//        Result result = campusService.allocateUser(campusId,userId);
//        System.out.println(result.getCode()+" "+result.getValue());//code 0为正确,-1参数错误,-2没有此方法 result true为正确 false为错误
//
//    }
//
//    @Test
//    public void testLoadUsers() throws Exception {
//        String userId = "1";
//        Map map =campusService.loadUsers(userId);
//        System.out.println(map);//在结果json格式 1 参数为userId或loginName返回结果为 userId(用户id) userName(用户名) userType(用户身份) userRealName(用户真实姓名) userPath(用户头像地址) userAvatar(用户头像图片名称) userGender(用户性别 1代表男 2代表女 0 未知) userBirthday(用户出生日期) userMobile(手机号) userEmail(邮箱) userAddress(家庭住址) userNum(学籍号) userRegdate(注册时间) userStatus(用户状态 0-用户注册未激活 1-用户正常使用 2-用户被禁用 3-用户未激活被禁用) agencyId(机构id) agencyName(结构名称) 2 参数为userMobile或userEmail返回结果为 userId(用户id)
//
//    }
//
//    @Test
//    public void testLoadCampus() throws Exception {
//        String page = "1";
//        String pageSize = "16";
//        String searchWord = "";
//        PageData pageData = campusService.loadCampus(page,pageSize,searchWord);
//        System.out.println(pageData);//结果中显示正确的分页结果
//    }
//
//    @Test
//    public void testSearchUsers() throws Exception {
//        String page = "1";
//        String pageSize = "16";
//        String campusId = "1";
//        String loginName = "";
//        PageData pageData = campusService.searchUsers(page, pageSize, campusId, loginName);
//        System.out.println(pageData);//结果中显示正确的分页结果
//    }
//
//    @Test
//    public void testSearchCampus() throws Exception {
//        String string =campusService.searchCampus();
//        System.out.println(string);//正确则显示拼接的字符串
//    }
}