package com.honghe.web.user.controller.campus;

import com.honghe.service.client.Result;
import com.honghe.web.user.service.campus.CampusService;
import jodd.joy.page.PageData;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by sunchao on 2016/9/27.
 */
@Controller
@RequestMapping("/Campus")
public class CampusController {
    @Autowired
    CampusService campusService;
    private String PROVINCE_TYPE_ID="1";
    private String CITY_TYPE_ID="2";
    private String AREA_TYPE_ID="3";
    private String SCHOOL_TYPE_ID="4";
    private String SCHOOL_BRANCH_TYPE_ID="5";
    private String ADMIN_ID="1";
    private String ROOT_CAMPUS_ID="1";

    /**
     * 删除组织机构成员
     * @param campusId 组织机构id
     * @param userId 用户成员id
     * @param pw
     */
        @RequestMapping("/deleteUser")
    public void deleteUser(String campusId,String userId,PrintWriter pw){
        JSONObject json = new JSONObject();
            //获取服务中返回的结果
        Result res = campusService.deleteUser(campusId,userId);
        if (res.getValue() != null) {
                json.put("value",res.getValue());
            }
        json.put("code",res.getCode());
        pw.write(json.toString());//将value和code传给前台
    }

    /**
     *
     * 添加组织机构
     * @param pId 组织机构id
     * @param name 组织机构名称
     * @param pw
     */
    @RequestMapping("/addCampus")
    public void addCampus(String provinceId, String cityId, String areaId, String name, String pId, String number, String typeId, String stagesId, String schoolYear, String remark,String campusIp,String campusPort,PrintWriter pw){
        JSONObject json = new JSONObject();
        campusIp= campusIp==null?"":campusIp;
        campusPort= campusPort==null?"":campusPort;
        List campusList = new ArrayList();
        if (!"".equals(campusIp)) {
            campusList = campusService.getCampusInfoByIp(campusIp);
        }
        Result result=null;
        if (campusList!=null&&campusList.size()>0){
            json.put("value","-3");
            json.put("code",0);
        }else{
            String url=campusIp+":"+campusPort;
//            Result res = campusService.addCampus(provinceId, cityId, areaId, name, pId,number,typeId,stagesId,schoolYear,remark,url);//获取服务中返回的结果
            //直接选择省与市，pId=null，typeId=2,则需要添加省与市
            if(CITY_TYPE_ID.equals(typeId) && pId==null){
                result=getCityResult(provinceId,cityId,number,stagesId,result);
            }
            //直接选择省、市、区，则一次添加省、市、区
            else if(AREA_TYPE_ID.equals(typeId) && pId==null){
                result=getAreaResult(provinceId,cityId,areaId,number,stagesId,result);
            }
            //直接选择省、市、区，添加学校
            else if((SCHOOL_TYPE_ID.equals(typeId) || SCHOOL_BRANCH_TYPE_ID.equals(typeId)) && pId==null){
                result=getSchoolResult(provinceId,cityId,areaId,name,number,typeId,stagesId,result);
            }
            //按省、市、区依次添加
            else{
               List<Map<String,String>> list=campusService.getAreaList(ADMIN_ID,ROOT_CAMPUS_ID);
               //首先判断根节点组织机构是否存在，如果存在，获取省id，与再次接收的根节点id与数据库id不一致，则提示
               if(list!=null && !list.isEmpty() && list.get(0)!=null && !list.get(0).isEmpty()){
                   String rootProvinceId=list.get(0).get("provinceID").toString();
                   if(rootProvinceId.equals(provinceId)){
                       //添加市区
                       if(typeId!=null && CITY_TYPE_ID.equals(typeId)){
                           Map<String,String> cityCampus= campusService.findCampusByHatId("city",cityId);
                           //已存在该市,则给出提示
                           if(cityCampus!=null && !cityCampus.isEmpty()) {
                               json.put("value","-5");
                               json.put("code","0");
                               pw.write(json.toString());
                               return;
                           }else {
                               result = campusService.addCampus(provinceId, cityId, areaId, name, pId, number, typeId, stagesId, schoolYear, remark, url);
                           }
                       }
                       //直接添加区县
                       else if (typeId!=null && AREA_TYPE_ID.equals(typeId)){
                            result=getAreaResult(provinceId,cityId,pId,name,areaId,number,typeId,stagesId,schoolYear,remark,url,json,pw,result);
                       }
                       //直接添加学校
                       else if(typeId!=null && (SCHOOL_TYPE_ID.equals(typeId) || SCHOOL_BRANCH_TYPE_ID.equals(typeId))){
                           //添加区/县
                           Result areaResult=getAreaResult(provinceId,cityId,pId,name,areaId,number,typeId,stagesId,schoolYear,remark,url,json,pw,result);
                           if(-1==areaResult.getCode()){
                               return;
                           }
                           if(areaResult.getValue()!=null && areaResult.getCode()==0) {
                               String pid=areaResult.getValue().toString();
                               result = campusService.addCampus(provinceId, cityId, areaId, name, pid, number, typeId, stagesId, schoolYear, remark, url);
                           }
                       }
                   }else{
                       json.put("value","-4");
                       json.put("code","0");
                       pw.write(json.toString());
                       return;
                   }
               }else {
                   result = campusService.addCampus(provinceId, cityId, areaId, name, pId, number, typeId, stagesId, schoolYear, remark, url);
               }
            }
            if (result.getValue() != null) {
                json.put("value",result.getValue());
            }else{
                json.put("value","");
            }
            json.put("code",result.getCode());
        }
        ////将value和code传给前台
        pw.write(json.toString());
    }

    /**
     * 保存市、区县
     * @param provinceId
     * @param cityId
     * @param pId
     * @param name
     * @param areaId
     * @param number
     * @param typeId
     * @param stagesId
     * @param schoolYear
     * @param remark
     * @param url
     * @param json
     * @param pw
     * @param result
     * @return
     */
    private Result getAreaResult(String provinceId,String cityId,String pId,String name,String areaId,String number,
                      String typeId,String stagesId,String schoolYear,String remark,String url,JSONObject json,
                             PrintWriter pw,Result result){
    //市id不为空，首先判断组织机构中是否存在该市，不存在则先添加市,存在则进行提示
    if(cityId!=null && !"".equals(cityId)){
        Map<String,String> cityCampus= campusService.findCampusByHatId("city",cityId);
        Result city=null;
        //已存在该市，正常添加区县
        if(cityCampus!=null && !cityCampus.isEmpty()){
            //判断区县是否存在，存在则不再重复添加，否则添加新的区县
            Map<String,String> areaCampus= campusService.findCampusByHatId("area",areaId);
            if(areaCampus==null || areaCampus.isEmpty()) {
                String pid = cityCampus.get("id");
                //添加区县
                if((SCHOOL_TYPE_ID.equals(typeId) || SCHOOL_BRANCH_TYPE_ID.equals(typeId))){
                    name=campusService.findHatNameById("area",areaId);
                }
                result = campusService.addCampus(provinceId, cityId, areaId, name, pid, number, AREA_TYPE_ID, stagesId, schoolYear, remark, url);
            }else{
                Result areaResult=new Result("",0,areaCampus.get("id"));
                result=areaResult;
            }
        }
        //不存在该市，则县添加市组织机构，再添加区县
        else{
            String cityName= campusService.findHatNameById("city",cityId);
            city=campusService.addCampus(provinceId, cityId, "0", cityName,pId,number,CITY_TYPE_ID,stagesId,"0","","");
            if(city.getValue()!=null && city.getCode()==0){
                String pid = city.getValue().toString();
                //保存区县
                if((SCHOOL_TYPE_ID.equals(typeId) || SCHOOL_BRANCH_TYPE_ID.equals(typeId))){
                    name=campusService.findHatNameById("area",areaId);
                }
                result=campusService.addCampus(provinceId, cityId, areaId, name, pid,number,AREA_TYPE_ID,stagesId,schoolYear,remark,url);
            }
        }
    }else{
        json.put("value","");
        json.put("code","-1");
        Result errorResult=new Result("",-1,"");
        result=errorResult;
        pw.write(json.toString());
    }
    return result;
}
    /**
     * 获取市区添加结果
     * @param provinceId   省id
     * @param cityId       市id
     * @param number       预计人数
     * @param stagesId     学段id
     * @param result
     * @return
     */
    private Result getCityResult(String provinceId,String cityId,String number,String stagesId,Result result){
        //先添加省
        String provinceName=campusService.findHatNameById("province",provinceId);
        if(!"".equals(provinceName)) {
            Result resPro = campusService.addCampus(provinceId, "0", "0", provinceName, "0", number, PROVINCE_TYPE_ID, stagesId, "0", "", "");//获取服务中返回的结果
            if (resPro.getValue() != null && resPro.getCode() == 0) {
                String pId = resPro.getValue().toString();
                //添加市
                String cityName=campusService.findHatNameById("city",cityId);
                if(!"".equals(cityName)) {
                    Result resCity = campusService.addCampus(provinceId, cityId, "0", cityName, pId, number, CITY_TYPE_ID, stagesId, "0", "", "");//获取服务中返回的结果
                    result = resCity;
                }
            }
        }
        return result;
    }

    /**
     * 获取区/县添加结果
     * @param provinceId   省id
     * @param cityId       市id
     * @param areaId       区id
     * @param number       预计人数
     * @param stagesId     学段id
     * @param result
     * @return
     */
    private Result getAreaResult(String provinceId,String cityId,String areaId,String number,String stagesId,Result result){
        Result resCity=null;
        resCity=getCityResult(provinceId,cityId,number,stagesId,resCity);
        if(resCity.getValue()!=null && resCity.getCode()==0){
            String pId=resCity.getValue().toString();
            String areaName=campusService.findHatNameById("area",areaId);
            if(!"".equals(areaName)) {
                Result resArea = campusService.addCampus(provinceId, cityId, areaId, areaName , pId, number, AREA_TYPE_ID, stagesId, "", "", "");//获取服务中返回的结果
                result = resArea;
            }
        }
        return result;
    }

    /**
     * 获取添加学校返回结果
     * @param provinceId   省id
     * @param cityId       市id
     * @param areaId       区id
     * @param name         机构名称
     * @param number       预计人数
     * @param typeId       机构类型
     * @param stagesId     学段id
     * @param result
     * @return
     */
    private Result getSchoolResult(String provinceId,String cityId,String areaId,String name,String number,String typeId,String stagesId,Result result){
        Result resArea=null;
        resArea=getAreaResult(provinceId,cityId,areaId,number,stagesId,resArea);
        //获取区/县信息
        if(resArea.getValue()!=null && resArea.getCode()==0){
            String pId=resArea.getValue().toString();
            Result resSch = campusService.addCampus(provinceId, cityId, areaId , name, pId,number,typeId,stagesId,"","","");//获取服务中返回的结果
            result=resSch;
        }
        return result;
    }
    /**
     * 修改组织机构
     * @param id 组织机构id
     * @param name 组织机构名称
     * @param pw
     */
    @RequestMapping("/updateCampus")
    public void updateCampus(String id,String name, String pId, String number, String typeId, String stagesId, String schoolYear, String remark,String campusIp,String campusPort,PrintWriter pw){
        campusIp= campusIp==null?"":campusIp;
        campusPort= campusPort==null?"":campusPort;
        String url=campusIp+":"+campusPort;
        JSONObject json = new JSONObject();
        Result res = campusService.updateCampus(id, name, pId,number,typeId,stagesId,schoolYear,remark,url);//获取服务中返回的结果
        if (res.getValue() != null) {
            json.put("value",res.getValue());
        }
        json.put("code",res.getCode());
        pw.write(json.toString());//将value和code传给前台
    }

    /**
     * 删除组织机构
     * @param id 组织机构id
     * @return
     */
    @RequestMapping("/deleteCampus")
    public void deleteCampus(String id,PrintWriter pw){
        JSONObject json = new JSONObject();
        Result res = campusService.deleteCampus(id);
        if (res.getValue() != null) {
            json.put("value",res.getValue());
        }
        json.put("code",res.getCode());
        pw.write(json.toString());
    }

    /**
     * 分配人员到组织机构
     * @param campusId 组织机构id
     * @param userId 用户id
     * @param pw
     */
    @RequestMapping("/allocateCampus")
    public void allocateUser(String campusId,String userId,PrintWriter pw){
        JSONObject json = new JSONObject();
        Result res = campusService.allocateUser(campusId,userId);//获取服务中返回的结果
        if (res.getValue() != null) {
            json.put("value",res.getValue());
        }
        json.put("code",res.getCode());
        pw.write(json.toString());//将value和code传给前台
    }

    /**
     * 获取组织结构左侧树（组织机构界面）
     * @param pw
     */
    @RequestMapping("/searchCampus")
    public void searchCampus(String userId,PrintWriter pw){
        String res = campusService.searchCampus(userId);
        if (res!= null) {
            pw.write(res);//拼接的字符串
        }
    }

    /**
     * 获取组织机构左侧树（用户管理界面）
     * @param pw
     */
    @RequestMapping("/campusTree")
    public void campusTree(String userId,PrintWriter pw){
        pw.write(campusService.campusTree(userId));
    }

    /**
     * 获取用户列表
     * @param page 当前页
     * @param pageSize 总页数
     * @param campusId 组织机构id
     * @param searchWord 搜索词
     * @param pw
     */
    @RequestMapping("/searchUsers")
    public void searchUsers(String page, String pageSize,String campusId,String searchWord,PrintWriter pw) {
        PageData pageData = campusService.searchUsers(page, pageSize, campusId, searchWord);
        JSONObject json = new JSONObject();
        List<Map<String,String>> list=pageData.getItems();
        int totalCount =pageData.getTotalItems();
        json.put("list",list);
        json.put("totalCount",totalCount);
        if (pageData != null) {
            pw.write(json.toString());
        }
    }


    /**
     * 获取用户详细列表
     * @param page 当前页
     * @param pageSize 总页数
     * @param searchName  姓名关键词
     * @param campusType 选择的机构类型
     * @param tsFlag 学生 教师标识 (TE:教师；ST:学生)
     * @return
     */
    @RequestMapping("/loadCampus")
    public void loadCampus(String page,String pageSize,String searchName,String campusType,String tsFlag,String campusId, String stageId,PrintWriter pw) {
        PageData pageData = campusService.loadCampus(page, pageSize, searchName,campusType,tsFlag,campusId,stageId);
        JSONObject json = new JSONObject();
        List<Map<String,String>> list=pageData.getItems();
        int totalCount =pageData.getTotalItems();
        json.put("list",list);
        json.put("totalCount",totalCount);
        if (pageData != null) {
            pw.write(json.toString());//拼接的字符串
        }
    }

    /**
     * 获取用户信息
     * @param userId 用户id
     * @param pw
     */
    @RequestMapping("/loadUsers")
    public void loadUsers(String userId,PrintWriter pw) {
        Map res = campusService.loadUsers(userId);
        JSONObject json = new JSONObject();
        json.put("userinfo",res);
        if (res != null) {
            pw.write(json.toString()); //拼接的字符串
        }
    }

    /**
     * 导入组织机构
     *
     * @param pw 写入数据
     */
    @RequestMapping("/campusUpload")
    public void userUpload(PrintWriter pw,HttpServletRequest req,HttpServletResponse response) {
        JSONObject obj = campusService.campusUpload(req, response);
        pw.write(obj.toString());
    }


    /**
     * 下载组织结构模板
     * @params pw 写入数据
     */
    @RequestMapping("/campusDownloadMould")
    public void campusDownloadMould(HttpServletRequest req,HttpServletResponse resp) {
        campusService.campusDownloadMould(req,resp);
    }

    /**
     * 获取学段列表，添加机构时用
     * @param pw
     */
    @RequestMapping("/getStages")
    public void getStages(PrintWriter pw){
        JSONObject json = new JSONObject();
        Result res = campusService.getStages();
        if (res.getValue()!=null){
            json.put("value",res.getValue());
        }
        json.put("code",res.getCode());
        pw.write(json.toString());
    }

    /**
     * 获取组织机构类型，添加机构时用
     * @param level 组织机构的级别
     * @param pw
     */
    @RequestMapping("/getCampusType")
    public void getCampusType(String level,PrintWriter pw){
        JSONObject json = new JSONObject();
        Result res = campusService.getCampusType(level);
        if (res.getValue()!=null){
            json.put("value",res.getValue());
        }
        json.put("code",res.getCode());
        pw.write(json.toString());
    }

    /**
     * 获取组织机构类型，添加机构时用
     * @param campusId 组织机构的级别
     * @param pw
     */
    @RequestMapping("/getCampusInfo")
    public void getCampusInfo(String campusId,PrintWriter pw){
        JSONObject json = new JSONObject();
        Result res = campusService.getCampusInfo(campusId);
        if (res.getValue()!=null){
            json.put("value",res.getValue());
        }
        json.put("code",res.getCode());
        pw.write(json.toString());
    }

    @RequestMapping("/getSchoolList")
    public void getSchoolList (PrintWriter pw){
        JSONObject json = new JSONObject();
        Result res = campusService.getSchoolList();
    }

    // TODO: 2018/3/2 区域化公共服务新增 add mz *****************************start*************************************************
    /**
     * 根据用户id获取所属的省/市/区
     * admin用户不属于任何省/市/区
     * @param
     * @param
     */
    @RequestMapping("/getAreaList")
    public void getAreaList (String userId, PrintWriter pw){
        JSONObject json = new JSONObject();
        json.put("value", campusService.getAreaList(userId));
        pw.write(json.toString());
    }

    @RequestMapping("/getAreaListByCampusId")
    public void getAreaList (String userId, String campusId, PrintWriter pw){
        JSONObject json = new JSONObject();
        json.put("value", campusService.getAreaList(userId, campusId));
        pw.write(json.toString());
    }


    /**
     * 根据省id获取所对应的城市列表
     * @param provinceId 省id
     * @param pw
     */
    @RequestMapping("/getCityList")
    public void getCityList (String provinceId, PrintWriter pw){
        JSONObject json = new JSONObject();
        json.put("value", campusService.getCityList(provinceId));
        pw.write(json.toString());
    }

    /**
     * 根据市id获取所对应的县/市列表
     * @param cityId 省id
     * @param pw
     */
    @RequestMapping("/getCountyList")
    public void getCountyList (String cityId, PrintWriter pw){
        JSONObject json = new JSONObject();
        json.put("value", campusService.getCountyList(cityId));
        pw.write(json.toString());
    }

    /**
     * 获取组织结构左侧树（组织机构界面）
     * @param pw
     */
    @RequestMapping("/campusSearchByUserId")
    public void campusSearchByUserId(String userId,String type, PrintWriter pw){
        String campusStr= campusService.campusSearchByUserId(userId, type);
        if (campusStr!= null) {
            pw.write(campusStr);//拼接的字符串
        }
    }

    @RequestMapping("/findChildCampusById")
    public void findChildCampusById(String id, PrintWriter pw){
        JSONObject json = new JSONObject();
        List campus= campusService.findChildCampusById(id);
        if (campus.isEmpty()) {
            json.put("value", 0);
        } else {
            json.put("value", campus.size());
        }
        pw.write(json.toString());
    }
}
