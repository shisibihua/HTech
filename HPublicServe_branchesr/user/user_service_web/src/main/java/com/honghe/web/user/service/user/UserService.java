package com.honghe.web.user.service.user;


import com.honghe.service.client.Result;
import com.honghe.web.user.util.AvatarUtil;
import com.honghe.web.user.util.ExcelTools;
import com.honghe.web.user.util.HttpServiceUtil;
import jodd.joy.page.PageData;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


/**
 * Created by sunsun on 2016/9/27.
 * 用户服务
 */
@Service
public class UserService {
    Logger logger = Logger.getLogger(UserService.class);

    /**
     * 添加用户的服务
     */
    public JSONObject userAdd(HttpServletRequest req,String userPwd, String userRealName, String userGender, String userBirthday, String userMobile, String userEmail, String userAddress, String userNum, String userType,String userAvatar) {

        String path = req.getRealPath("/headImage");
        File small = new File(path + File.separator + userAvatar);
        String fileName ="";
        if(small.exists()) {
            fileName = HttpServiceUtil.uploadFile(small);
        }
        JSONObject json = new JSONObject();
        Map<String, String> params = new HashMap<>();
        params.put("userPwd", userPwd);
        params.put("userRealName", userRealName);
        params.put("userGender", userGender);
        params.put("userBirthday", userBirthday);
        params.put("userMobile", userMobile);
        params.put("userEmail", userEmail);
        params.put("userAddress", userAddress);
        params.put("userNum", userNum);
        params.put("userType", userType);
        params.put("userAvatar", fileName);
        try {
            Result res = HttpServiceUtil.userService("userAdd", params);
            if (res.getValue() != null) {
                String result = res.getValue().toString();
                int code = res.getCode();
                json.put("result", result);
                json.put("code", code);
            }
        } catch (Exception e) {
            logger.error("添加用户失败：" + e);
        }
        return json;
    }

    /**
     * 修改用户的服务
     */
    public JSONObject userUpdate(HttpServletRequest req,String userId, String userRealName, String userGender, String userBirthday, String userType, String userNum, String userMobile, String userEmail, String userAddress,String userAvatar) {


        String path = req.getRealPath("/headImage");
        File small = new File(path + File.separator + userAvatar);
        String fileName ="";
        if(small.exists()) {
            fileName = HttpServiceUtil.uploadFile(small);
        }
        JSONObject json = new JSONObject();
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("userRealName", userRealName);
        params.put("userGender", userGender);
        params.put("userBirthday", userBirthday);
        params.put("userType", userType);
        params.put("userNum", userNum);
        params.put("userMobile", userMobile);
        params.put("userEmail", userEmail);
        params.put("userAddress", userAddress);
        params.put("userAvatar", fileName);
        try {
            Result res = HttpServiceUtil.userService("userUpdate", params);
            if (res.getValue() != null) {
                int code = (int) res.getValue();
                json.put("code", code);
            }
        } catch (Exception e) {
            logger.error("用户修改失败：" + e);
        }
        return json;
    }

    /**
     * 用户搜索的服务
     */
    public JSONObject userSearch(String userId) {
        JSONObject json = new JSONObject();
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        try {
            Result res = HttpServiceUtil.userService("userSearch", params);
            if (res.getValue() != null) {
                String result = res.getValue().toString();
                int code = res.getCode();
                json.put("code", code);
                json.put("result", result);
            }
        } catch (Exception e) {
            logger.error("搜索用户失败" + e);
        }
        return json;
    }

    /**
     * 修改用户状态服务
     */
    public JSONObject userUpdateStatus(String userId, String userStatus) {
        JSONObject json = new JSONObject();
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("userStatus", userStatus);
        try {
            Result res = HttpServiceUtil.userService("userUpdateStatus", params);
            if (res.getValue() != null) {
                String result = res.getValue().toString();
                int code = res.getCode();
                json.put("result", result);
                json.put("code", code);
            }
        } catch (Exception e) {
            logger.error("修改用户状态失败" + e);
        }
        return json;
    }

    /**
     * 修改用户密码服务
     */
    public JSONObject pwdUpdate(String userId, String userPwd) {
        JSONObject json = new JSONObject();
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("userPwd", userPwd);
        try {
            Result res = HttpServiceUtil.userService("userUpdate", params);
            if (res.getValue() != null) {
                int code = res.getCode();
                String result = res.getValue().toString();
                json.put("code", code);
                json.put("result", result);
            }
        } catch (Exception e) {
            logger.error("用户修改失败：" + e);
        }
        return json;
    }
//    /**
//     * 刷新cache内容
//     * */
//    public JSONObject role2permissionCacheReload(){
//        //返回data
//        JSONObject json = new JSONObject();
//        Map<String,String> params = new HashMap<>();
//        try{
//            Result res = HttpServiceUtil.userService("role2permissionCacheReload",params);
//        }catch(Exception e){
//            logger.error("用户修改失败：" + e);
//        }
//        return json;
//    }

    /**
     * 导入-下载用户模板
     */
    public void userDownloadMould(HttpServletRequest req, HttpServletResponse resp) {
        String filePath = req.getRealPath("/") + "mould.xls";
        ExcelTools et = new ExcelTools();
        et.downExcel(filePath, true, resp);
    }

    /**
     * 用户服务-用户模板导出失败F
     */
    public void userDownloadFailed(HttpServletRequest req, HttpServletResponse resp) {
        String filePath = req.getParameter("filePath");
        ExcelTools et = new ExcelTools();
        et.downExcel(filePath, false, resp);
    }

    /**
     * 解析表格内容 并添加到数据库中
     * //TODO：导入方法之后可能会添加到用户接口中
     * @param file
     */
//    protected String importUsers(File file){
//        InputStream stream = null;
//        try {
//            // 文件流指向excel文件
//            stream = new FileInputStream(file);
//            //HSSF只能打开2003，XSSF只能打开2007，WorkbookFactory.create兼容以上两个版本
//            Workbook workbook = WorkbookFactory.create(stream);
//            Sheet sheet = workbook.getSheetAt(0);// 得到工作表
//            Row row = null;// 对应excel的行
//            int totalRow = sheet.getLastRowNum();// 得到excel的总记录条数
//            //逐行写入数据库
//            Result result;
//            String currentType = "";// 保存临时用户身份 用于减少数据库查找次数
//            String currentTypeId = "";// 保存临时用户身份ID
//            List<Map<String,String>> failedList = new LinkedList<>();
//            for (int i=2; i<=totalRow; i++) {
//                //获取单行数据
//                row = sheet.getRow(i);
//                //获取所有数据
//                Map param = new HashMap();
//                //需要判断每个单元格是否为空
//                param.put("cmd_op","userAdd");
//                param.put("userPwd","111111");//每个用户默认密码111111
//                param.put("userRealName",row.getCell(0)==null?"":row.getCell(0).toString());
//                param.put("userNum", row.getCell(1)==null?"":row.getCell(1).toString());
//                param.put("userMobile",row.getCell(2)==null?"":row.getCell(2).toString());
//                param.put("userEmail",row.getCell(3)==null?"":row.getCell(3).toString());
//                param.put("userGender",row.getCell(4)==null?"":row.getCell(4).toString());
//                param.put("userBirthday",row.getCell(5)==null?"":row.getCell(5).toString());
//                param.put("userAddress",row.getCell(6)==null?"":row.getCell(6).toString());
//                param.put("userType",row.getCell(7)==null?"":row.getCell(7).toString());
//                //TODO:暂时缺第八项逻辑方法 该项为机构编号
//                param.put("agencyNum",row.getCell(8)==null?"":row.getCell(8).toString());
//                param.put("userCard",row.getCell(9)==null?"":row.getCell(9).toString());
//                param.put("userCourse",row.getCell(10)==null?"":row.getCell(10).toString());
//                param.put("userInfo",row.getCell(11)==null?"":row.getCell(11).toString());
//                //此处判断若学号 手机 邮箱都没有时认为参数错误
//                if (row.getCell(1)==null&&row.getCell(2)==null&&row.getCell(3)==null){
//                    System.out.println("必须输入学号/手机/邮箱");
//                    failedList.add(param);
//                    continue;
//                }
//                //此处需要添加查询身份ID
//                String user_type = row.getCell(7)==null?"":row.getCell(7).toString();
//                if(!"".equals(user_type)) {
//                    if (user_type.equals(currentType)) {
//                        user_type = currentTypeId;
//                    } else {
//                        Map findUserType = new HashMap();
//                        findUserType.put("cmd_op", "userTypeSearch");
//                        findUserType.put("typeName", user_type);
//                        Result res = HttpServiceUtil.userService("userTypeSearch", findUserType);
//                        Map map = (Map) res.getValue();
//                        //判断若没有此身份时认为参数错误
//                        if (map.size() == 0) {
//                            System.out.println("没有这个身份");
//                            failedList.add(param);
//                            continue;
//                        }
//                        //将上一个导入的用户的身份信息保存 为了减少查询数据库的次数
//                        if (map.size() != 0) {
//                            currentType = user_type;
//                            user_type = map.get("typeId").toString();
//                            currentTypeId = map.get("typeId").toString();
//                        }
//                    }
//                }
//                param.put("userType",user_type);
//                //判断性别后转为数据库中代号 1男 2女 0未知
//                String sex = row.getCell(4)==null?"":row.getCell(4).toString();
//                if ("男".equals(sex)){
//                    sex="1";
//                }else if ("女".equals(sex)){
//                    sex="2";
//                }else {
//                    sex="0";
//                }
//                param.put("userGender",sex);
//                //执行添加操作
//                result =HttpServiceUtil.userService("userAdd", param);
//                if (result.getValue().toString().equals("-1")){
//                    //用户信息有重复，判断是否要修改，用编号判断，若并不是编号错误则返回到错误文档
//                    System.out.println("用户信息有重复");
//                    Map findByUserNum = new HashMap();
//                    findByUserNum.put("cmd_op","userSearch");
//                    findByUserNum.put("userNum",row.getCell(1)==null?"":row.getCell(1).toString());
//                    //按学号查找用户信息
//                    result =HttpServiceUtil.userService("userSearch", findByUserNum);
//                    //若不是学号相同的用户则放入失败列表
//                    if (result.getValue().toString().equals("")){
//                        //若学号不相同则把身份和性别还原后放入失败列表
//                        param.put("userGender",row.getCell(4)==null?"":row.getCell(4).toString());
//                        param.put("typeName",row.getCell(7)==null?"":row.getCell(7).toString());
//                        failedList.add(param);
//                        continue;
//                    }else {
//                        //修改user表属性
//                        param.put("cmd_op","userUpdate");
//                        param.put("userId",result.getValue().toString());
//                        Result result1 =HttpServiceUtil.userService("userUpdate", param);
//                        //修改失败
//                        if (result1.getValue().toString().equals("-1")){
//                            param.put("userGender",row.getCell(4)==null?"":row.getCell(4).toString());
//                            failedList.add(param);
//                            continue;
//                        }
//                        if(!"".equals(user_type)) {
//                            //添加新的用户角色关系表内容
//                            Map findRole = new HashMap();
//                            findRole.put("cmd_op", "userTypeSearch");
//                            findRole.put("typeId", user_type);
//                            Result res = HttpServiceUtil.userService("userTypeSearch", findRole);
//                            List<Map<String, String>> roleList = (List<Map<String, String>>) res.getValue();
//                            if (roleList.size() > 0) {
//                                String roleIds = "";
//                                for (Map map : roleList) {
//                                    roleIds += map.get("roleId") + ",";
//                                }
//                                Map addRole2user_type = new HashMap();
//                                addRole2user_type.put("cmd_op", "roleAllot");
//                                addRole2user_type.put("roleId", roleIds);
//                                addRole2user_type.put("userId", result.getValue().toString());
//                                HttpServiceUtil.userService("roleAllot", addRole2user_type);
//                            }
//                        }
//                    }
//                }else {
//                    //信息不重复时直接保存用户角色关系
//                    Map findRole = new HashMap();
//                    findRole.put("cmd_op", "userTypeSearch");
//                    findRole.put("typeId", user_type);
//                    Result res = HttpServiceUtil.userService("userTypeSearch", findRole);
//                    List<Map<String,String>> roleList = (List<Map<String,String>>)res.getValue();
//                    if (roleList.size()>0){
//                        String roleIds = "";
//                        for(Map map :roleList){
//                            roleIds+=map.get("roleId")+",";
//                        }
//                        Map addRole2user_type = new HashMap();
//                        addRole2user_type.put("cmd_op", "roleAllot");
//                        addRole2user_type.put("roleId", roleIds);
//                        addRole2user_type.put("userId", result.getValue().toString());
//                        HttpServiceUtil.userService("roleAllot", addRole2user_type);
//                    }
//                }
//            }
//            if (failedList.size()>0){
//                String filePath = exportExcel(failedList,null);
//                return filePath;
//            }
//        } catch (FileNotFoundException e) {
//            return "fileError";
//        } catch (Exception ex) {
//            return "fileError";
//        }
//        finally {
//            if (stream != null) {
//                try {
//                    stream.close();
//                } catch (IOException e) {
//                    return "fileError";
//                }
//            }
//        }
//        return "success";
//    }

    /**
     * 生成excel文件，response不为空时，提供下载
     *
     * @param userList 用户列表
     */
    public String exportExcel(List<Map<String, String>> userList, HttpServletResponse response) {
        ExcelTools excel = new ExcelTools();
        // 生成表头
        String[] headers = {"姓名", "编号*", "手机*", "邮箱*", "性别", "生日", "地址", "身份", "机构编号", "卡号", "学科", "简介"};
        List<String[]> excelList = new ArrayList<>();
        // 插入数据
        for (int i = 0; i < userList.size(); i++) {
            String[] strList = new String[12];
            strList[0] = userList.get(i).get("userRealName") == null ? "" : userList.get(i).get("userRealName");
            strList[1] = userList.get(i).get("userNum") == null ? "" : userList.get(i).get("userNum");
            strList[2] = userList.get(i).get("userMobile") == null ? "" : userList.get(i).get("userMobile");
            strList[3] = userList.get(i).get("userEmail") == null ? "" : userList.get(i).get("userEmail");
            String sex = userList.get(i).get("userGender") == null ? "" : userList.get(i).get("userGender");
            if (sex.equals("1")) {
                sex = "男";
            } else if (sex.equals("2")) {
                sex = "女";
            } else {
                sex = "";
            }
            strList[4] = sex;
            strList[5] = userList.get(i).get("userBirthday") == null ? "" : userList.get(i).get("userBirthday");
            strList[6] = userList.get(i).get("userAddress") == null ? "" : userList.get(i).get("userAddress");
            //TODO：用户身份名称与导入时用户身份名称不统一
            strList[7] = userList.get(i).get("typeName") == null ? "" : userList.get(i).get("typeName");
            strList[8] = userList.get(i).get("agencyNum") == null ? "" : userList.get(i).get("angencyNum");
            strList[9] = userList.get(i).get("userCard") == null ? "" : userList.get(i).get("userCard");
            strList[10] = userList.get(i).get("userCourse") == null ? "" : userList.get(i).get("userCourse");
            strList[11] = userList.get(i).get("userInfo") == null ? "" : userList.get(i).get("userInfo");
            excelList.add(strList);
        }
        String filePath = "";
        try {
            filePath = excel.exportTableByFile("用户列表", headers, excelList, "./", "tempDownExcel")[0];
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 如果response为空，只生成excel文件
        // response不为空时，提供下载
        if (response == null) {
            return filePath;
        }
        ExcelTools et = new ExcelTools();
        et.downExcel(filePath, false, response);
        return null;
    }

    /**
     * 用户导入
     */
    public JSONObject userUpload(HttpServletRequest req, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        ExcelTools et = new ExcelTools();
        String msg = et.uploadFile("user", req, response);
        json.put("result", msg);
        return json;
    }

    /**
     * 图片导入
     */
    public JSONObject imageUpload(HttpServletRequest req, int x, int y, int w, int h,String extension) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");
        Date date = new Date();
        String time = sdf.format(date);
        JSONObject json = new JSONObject();
        AvatarUtil ava = new AvatarUtil();
        File file = ava.uploadImage(req);
        String path = req.getRealPath("/headImage");
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdir();
        }
        File small = new File(path + File.separator + time + "."+extension+"");

        try {
            // 上传的原图
            Image image = ImageIO.read(file);
            BufferedImage bf = (BufferedImage)image;
            double height =  bf.getHeight();
            double width = bf.getWidth();
            // 页面展示图片的大小
            double webHeigth = 300;
            double webWidth = 300;
            // 根据比例计算截取图片的坐标，宽，高
            x = (int)(x * (width / webWidth));
            y = (int)(y * (height / webHeigth));
            w = (int)(w * (width / webWidth));
            h = (int)(h * (height / webHeigth));
            // 截图图片
            Iterator iterator = ImageIO.getImageReadersByFormatName(""+extension+"");

            ImageReader reader = (ImageReader) iterator.next();
            InputStream in = new FileInputStream(file);
            ImageInputStream iis = ImageIO.createImageInputStream(in);
            reader.setInput(iis, true);

            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rect = new Rectangle(x,y,w,h);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            // 写出图片
            ImageIO.write(bi, ""+extension+"", small);
            file.delete();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        json.put("result", "success");
        json.put("filename", time);
        return json;
    }

    /**
     * 用户详情
     */
    public JSONObject userDetails(String userId) {

        JSONObject json = new JSONObject();
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        try {
            Result ures = HttpServiceUtil.userService("userSearch", params);
            Result rres = HttpServiceUtil.userService("roleSearch", params);
            if (ures.getValue() != null) {
                Map userMap = (Map) ures.getValue();
                json.put("userInfo", userMap);
            }
            if (rres.getValue() != null) {
                Map roleMap = (Map) rres.getValue();
                json.put("roleInfo", roleMap);
            }
        } catch (Exception e) {
            logger.error("用户详情查询失败：" + e);
        }
        return json;
    }

    /**
     * 用户详情-组织机构
     */
    public JSONObject campusSearch(String userId) {
        JSONObject json = new JSONObject();
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        try {
            Result res = HttpServiceUtil.adService("campusSearch", params);
            if (res.getValue() != null) {
                String campusName = "";
                if (!"null".equals(res.getValue().toString()) && !"".equals(res.getValue().toString())) {
                    List resList = (List) res.getValue();
                    StringBuffer str =new StringBuffer();
                    for(int i=0;i<resList.size();i++){
                        JSONObject campus = (JSONObject) resList.get(i);
                        str.append(campus.get("name")).append(",");
                    }
                    campusName = str.toString();
                    if(null!=campusName&&!"".equals(campusName)) {
                        campusName = campusName.substring(0, campusName.length() - 1);
                    }
                }
                json.put("result", campusName);
            }
        } catch (Exception e) {
            logger.error("用户详情-组织机构查询失败：" + e);
        }
        return json;
    }

    /**
     * 用户分配角色
     */
    public JSONObject roleAllot(String user_ids, String roles_ids) {
        JSONObject json = new JSONObject();
        Map<String, String> params = new HashMap<>();
        //拆分user_ids并遍历
        String[] userId = user_ids.split(",");
        for (String uid : userId) {
            params.put("userId", uid);
            params.put("roleId", roles_ids);
            try {
                Result rres = HttpServiceUtil.userService("roleAllot", params);
                String result = rres.getValue().toString();
                json.put("result ", result);
            } catch (Exception e) {
                logger.error("用户分配角色失败：" + e);
            }
        }
        return json;
    }

    /**
     * 用户身份查询
     */
    public String userTypeSearch() {
        Map<String, String> params = new HashMap<>();
        String result = "";
        try {
            Result res = HttpServiceUtil.userService("userTypeSearch", params);
            if (res.getValue() != null) {
                List<Map<String, String>> recordList = (List<Map<String, String>>) res.getValue();
                result = loadUserType(recordList);
            }
        } catch (Exception e) {
            logger.error("用户身份查询失败" + e);
            result = "";
        }
        return result;
    }

    /**
     * 加载用户身份
     */
    private String loadUserType(List<Map<String, String>> recordList) {
        StringBuilder sb = new StringBuilder();
        sb.append(" <select id=\"details-identity\" name=\"details-identity\">");
        for (Map<String, String> record : recordList) {
            sb.append("<option value='" + record.get("typeId") + "'>" + record.get("typeName") + "</option>");
        }
        sb.append("</select>");
        return sb.toString();
    }

    /**
     * 用户模板导出
     */
    public void userExportExcel(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String> params = new HashMap<>();
        String searchWord = req.getParameter("searchWord");
        String userStatus = req.getParameter("userStatus");
        params.put("loginName", searchWord);
        params.put("userStatus", userStatus);
        try {
            Result res = HttpServiceUtil.userService("userExportExcel", params);
            if (res.getValue() != null) {
                List<Map<String, String>> exportList = (List<Map<String, String>>) res.getValue();
                exportExcel(exportList, resp);
            }
        } catch (Exception e) {
            logger.error("用户模板导出失败" + e);
        }
    }

    /**
     * 获取用户列表（分页）
     */
    public String userSearchByPage(String page, String pageSize, String searchWord, String userStatus,String userType,String campusId,String userId, HttpServletRequest req) {
        String res = "";
        Map<String,String> param = new HashMap<>();
        String isAdmin = "";
        param.put("userId",userId);
        try {
            Result userRes = HttpServiceUtil.userService("userGetIsAdmin",param);
            if (userRes.getValue()!=null&&!"".equals(userRes.getValue())){
                Map<String,Object> value = (Map<String, Object>) userRes.getValue();
                if (value!=null){
                    Map<String,Object> userMap = new HashMap<>();
                    userMap = (Map<String, Object>) value.get("userInfo");
                    isAdmin = (String) userMap.get("userIsAdmin");
                }
            }
        } catch (Exception e) {
            logger.error("获取用户信息异常",e);
        }
        Map<String, String> params = new HashMap<>();
        params.put("page", page);
        params.put("pageSize", pageSize);
        params.put("loginName", searchWord);
        params.put("userStatus", userStatus);
        params.put("userType", userType);
        params.put("campusId", campusId);
        try {
            Result result = HttpServiceUtil.userService("userSearchByPage", params);
            Map map = new HashMap();
            if (result.getValue() != null) {
                map = (Map) result.getValue();
            }
            List items = (List) map.get("items");
            int pagesize = (int) map.get("pageSize");
            int currentPage = (int) map.get("currentPage");
            int size = (int) map.get("totalItems");
            PageData pageData = new PageData(currentPage, size, pagesize, items);//分页
            res = loadUserInfo(req, pageData,isAdmin);
        } catch (Exception e) {
            logger.error("获取用户列表失败" + e);
        }
        return res;
    }
    public String findUserByPage(String page,String pageSize,String searchWord,String campusId,String userType){
        Map<String,String> params = new HashMap<>();
        params.put("page",page);
        params.put("pageSize",pageSize);
        params.put("searchWord",searchWord);
        params.put("campusId",campusId);
        params.put("userType",userType);
        try {
            Result result = HttpServiceUtil.userService("findUserByPage",params);
            Map map = new HashMap();
            if(result!=null&&result.getCode()==0){
                map = (Map) result.getValue();
            }
        } catch (Exception e) {
            logger.error("findUerByPage 异常",e);
        }
        return "";
    }

    /**
     * 加载用户信息的服务
     */
    private String loadUserInfo(HttpServletRequest req, PageData pageData,String isAdimn) {
        StringBuilder sb = new StringBuilder();
        int totalCount = pageData.getTotalItems();
        if (totalCount != 0) {
            List<Map<String, String>> userList = pageData.getItems();
            for (Map<String, String> userInfo : userList) {
                String userPath = "";
                userPath = userInfo.get("userPath");
                    if (userPath.equals("")) {
                        //暂时设置为默认头像
                        userPath = req.getContextPath() + "/images/userimg.png";
                    } else {
                        userPath = "http://" + HttpServiceUtil.getStorageIp() + ":" + HttpServiceUtil.getStoragePort() + userPath.substring(userPath.indexOf(":") + 1, userPath.length());
                    }

                String sex = userInfo.get("userGender");
                if (sex.equals("1")) {
                    sex = "男";
                } else if (sex.equals("2")) {
                    sex = "女";
                } else {
                    sex = "";
                }
                String status = userInfo.get("userStatus");
                String statusDesc = "";
                if (status.equals("0")) {
                    statusDesc = "未启用";
                } else if (status.equals("1")) {
                    statusDesc = "启用";
                } else if (status.equals("2")) {
                    statusDesc = "禁用";
                }
                String isUserAdmin = "";
                if ("1".equals(userInfo.get("userIsAdmin"))){
                    isUserAdmin = "管理员";
                }
                sb.append("  <div class=\"u-item\">" +
                        "                <div class=\"u-check\" id='" + userInfo.get("userId") + "' status='" + status + "'>" +
                        "                    <i class=\"m-check\" style=\"margin: 31px 0 0 11px\"></i>" +
                        "                </div>" +
                        "                <div class=\"u-name\" title=\"" + userInfo.get("userRealName") + "\">" +
                        "                    <div class=\"user-head\">" +
                        "                        <img src='" + userPath + "'>" +
                        "                        <div class=\"user-con\">" + userInfo.get("userRealName") +
                        "                           <span id=\"user-admin\">"+isUserAdmin+"</span></div>" +
                        "                        <div style=\"clear:both;line-height:0; height:0;\"></div>" +
                        "                    </div>" +
                        "                </div>" +
                        "                <div class=\"u-number\" title=\"" + userInfo.get("userNum") + "\">" + userInfo.get("userNum") + "</div>" +
                        "                <div class=\"u-sex\">" + sex + "</div>\n" +
                        "                <div class=\"u-tel\" title=\"" + userInfo.get("userMobile") + "\">" + userInfo.get("userMobile") + "</div>" +
                        "                <div class=\"u-email\" title=\"" + userInfo.get("userEmail") + "\">" + userInfo.get("userEmail") + "</div>" +
                        "                <div class=\"u-identity\">" + userInfo.get("typeName") + "</div>" +
                        "                <div class=\"u-status\">" + statusDesc + "</div>" +
                        "                <div class=\"u-operation\">" +
                        "                    <a href=\"javascript:void(0)\" style=\"margin: 30px 0 0 20px\" class=\"a-btn a-btn-details btn-details\" title='用户详情'></a>" +
                        "                    <a href=\"javascript:void(0)\" class=\"a-btn a-btn-pwd\" title='修改密码'></a>" +
//                        "                    <a href=\"javascript:void(0)\" class=\"a-btn a-btn-edit btn-edit-users\" title='用户编辑'></a>" +
                        "                </div>" +
                        "            </div>");
            }
        }
        return String.valueOf(totalCount) + "@_@" + sb.toString();
    }

    /**
     * 机构分配管理员
     * @param userId 用户id
     * @param campusIds
     * @return
     */
    public JSONObject editUserAdmin(String userId,String campusIds){
        JSONObject json = new JSONObject();
        Map<String, String> params = new HashMap<>();
        params.put("userId",userId);
        params.put("campusIds",campusIds);
        try {
            Result res = HttpServiceUtil.userService("userEditAdmin", params);
            if (res.getValue() != null) {
                boolean result = (boolean)res.getValue();
                int code = res.getCode();
                json.put("result", result);
                json.put("code", code);
            }
        } catch (Exception e) {
            logger.error("机构分配管理员失败" + e);
        }
        return json;
    }

    /**
     * 取消某用户的管理员身份
     * @param userId
     * @return
     */
    public JSONObject deleteUserAdmin(String userId) {
        JSONObject json = new JSONObject();
        Map<String, String> params = new HashMap<>();
        params.put("userId",userId);
        try {
            Result res = HttpServiceUtil.userService("userClearAdmin", params);
            if (res.getValue() != null) {
                boolean result = (boolean)res.getValue();
                int code = res.getCode();
                json.put("result", result);
                json.put("code", code);
            }
        } catch (Exception e) {
            logger.error("用户取消管理员身份失败" + e);
        }
        return json;
    }

    /**
     * 通过用户id获取用户信息
     * @param userId
     * @return
     */
    public JSONObject getUserInfo(String userId){
        JSONObject json = new JSONObject();
        Map<String, String> params = new HashMap<>();
        Map<String,Object> result = new HashMap<>();
        params.put("userId",userId);
        try {
            Result res = HttpServiceUtil.userService("userGetIsAdmin", params);
            if (res.getValue() != null) {
                Map<String,Object> value = (Map<String, Object>) res.getValue();
                List campusList = (List) value.get("adminCampus");
                result = (Map<String, Object>) value.get("userInfo");
                result.put("adminCampusId",campusList);
                json.put("result", result);
            }
        } catch (Exception e) {
            logger.error("用户取消管理员身份失败" + e);
        }
        return json;
    }

    /**
     * 查询用户所有设备
     * @param userId
     * @return
     */
    public List searchDevice(String userId,String deviceType,String searchWord){
        List list =new ArrayList();
        Map<String, String> params = new HashMap<>();
        params.put("userId",userId);
        params.put("dtypeName",deviceType);
        params.put("searchWord",searchWord);
        Result result = new Result("",0,"");//初始化result
        try {
            result = HttpServiceUtil.adService("user2DeviceSearch", params);
        }
        catch (Exception e) {
            logger.error("查找用户所有设备失败：" + e);
        }
        if (result.getCode() == 0 && result.getValue()!=null){
            list = (List)result.getValue();
        }
        return list;
    }
    /**
     * 给选择的人员分配设备
     * @param userId
     * @param deviceId
     * @return
     */
    public String allotUser2Device(String userId,String deviceId){
        String res="false";
        Map<String, String> params = new HashMap<>();
        params.put("userId",userId);
        Result result = new Result("",0,"");//初始化result
        try {
            result = HttpServiceUtil.adService("user2DeviceDelete", params);
            if (result!=null){
                res=result.getValue().toString()==""?"false":result.getValue().toString();
            }
        }
        catch (Exception e) {
            logger.error("角色用户关系删除失败：" + e);
            return res;
        }
        if (deviceId==null||"".equals(deviceId)){
            return res;
        }
        if (result.getCode() == 0 && Boolean.parseBoolean(result.getValue().toString())==true){
            params.put("deviceId",deviceId);
            try {
                result = HttpServiceUtil.adService("user2DeviceAdd", params);
            }
            catch (Exception e) {
                logger.error("角色用户关系添加失败：" + e);
            }
            if (result.getCode() == 0 && Boolean.parseBoolean(result.getValue().toString())==true){
                res="true";
            }
        }
        return res;
    }
}
