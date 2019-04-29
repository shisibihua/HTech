package com.honghe.tech.service.impl;

import com.esotericsoftware.kryo.serializers.FieldSerializer;
import com.honghe.tech.dao.SchoolyearDao;
import com.honghe.tech.dao.TermDao;
import com.honghe.tech.service.CommentService;
import com.honghe.tech.service.TermService;
import com.honghe.tech.util.ConstantWord;
import com.honghe.tech.util.DateTimeUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.sound.midi.MidiDevice;
import java.util.*;

/**
 * Created by xinqinggang on 2018/3/1.
 * @author xinqinggang
 */
@Service
public class TermServiceImpl implements TermService{
    Logger logger=Logger.getLogger(TermServiceImpl.class);
    @Autowired
    private TermDao termDao;
    @Autowired
    private SchoolyearDao schoolyearDao;

    /**
     * 添加学期或假期信息
     * 1.学期最高管理员有添加、修改权限，其它管理员只有可见权限，非管理员不可见。
     * 2.学期名称与时间不与其它学期冲突。
     * @param info 学期或假期信息json字符串根式
     * @param userId 用户id
     * @param operType 操作类型 add:添加；update:修改
     * @return 0:添加修改成功；-1：没有权限；-2:学期名称冲突;-3:与其它学期时间冲突；-4:operType为update时，该学期不存在；-5:未知原因操作失败
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Object addOrUpdateInfo(String info,String operType,int userId)
    {
        JSONObject infoJson=JSONObject.fromObject(info);
        //类型（学期：1；假期：2）；
        int type=(int)infoJson.get("type");
        //学期开始日期：格式 yyyy-MM-dd
        String startDate=infoJson.get("startDate").toString();
        //学期结束日期：格式：yyyy-MM-dd
        String endDate=infoJson.get("endDate").toString();
        String schoolyearName=infoJson.get("schoolYearName").toString();
        Map<String,Object> schoolyear=schoolyearDao.selectSchoolYearByName(schoolyearName);
        if(schoolyear!=null &&!schoolyear.isEmpty())
        {
            String schoolyearId=schoolyear.get("schoolyearId").toString();
            infoJson.put("schoolyearId",schoolyearId);
            //检测学年下是否有学期名称冲突
            List<Map<String,String>> infoList=this.findInfoByParam("","","","",type,"",infoJson.get("name").toString(),schoolyearId);
            if(infoList!=null&&infoList.size()>0) {
                String addts= "add";
                if (addts.equals(operType)) {
                    logger.error("操作学期或假期信息失败，原因：该学年中学期名称已存在");
                    return -2;
                } else {
                    for (Map<String, String> infoMap1 : infoList) {
                        if ((int) ((Object) infoMap1.get("termId")) != (int) infoJson.get("termId")) {
                            logger.error("操作学期或假期信息失败，原因：该学年中学期名称已存在");
                            return -2;
                        }
                    }
                }
            }
        }
        //检测与其它学期是否存在学期冲突
        List<Map<String,String>> infoMap2=this.findInfoByParam((startDate+" 00:00:00"),(endDate+" 23:59:59"),"","",type,"","","");
        if(infoMap2!=null&&infoMap2.size()>0)
        {
            String addts= "add";
            if (addts.equals(operType)) {
                logger.error("操作学期或假期信息失败，原因：时间与其它学期有冲突");
                return -3;
            } else {
                for (Map<String, String> infoMap1 : infoMap2) {
                    if ((int) ((Object) infoMap1.get("termId")) != (int) infoJson.get("termId")) {
                        logger.error("操作学期或假期信息失败，原因：时间与其它学期有冲突");
                        return -3;
                    }
                }
            }
        }
        return this.saveOrUpdateInfo(infoJson,operType);
    }

    /**
     * 根据学期或假期信息id更改信息状态
     * 1.学期最高管理员有修改权限，其它管理员只有可见权限，非管理员不可见。
     * 2.学期允许同时启动多个
     * @param id 学期或假期信息id
     * @param status 0：禁用；1：启动；
     * @return 0：修改成功； -1：没有修改权限；-4：未知原因修改失败；-5：学期不存在；
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Object updateInfoStatus(int id,int status,int userId)
    {
        int flag;
        try {
            //根据学期id检测该学期是否存在
            Map<String, Object> term = termDao.findInfoById(id);
            if (term == null) {
                flag = ConstantWord.UN_EXIST;
            } else {
                //修改学期状态，允许启用多个学期，不做处理。
                Map params = new HashMap();
                params.put("id", id);
                params.put("status", status);
                termDao.updateInfoStatus(params);
                flag = ConstantWord.AC_SHOW;
            }
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("修改学期或假期信息状态异常", e);
            flag = ConstantWord.UN_KNOWN;
        }
        return flag;
    }

    /**
     * 根据信息状态查看学期或假期信息
     * @param type 学期(1)或假期信息(2)
     * @param status 状态(0/1)
     * @return 学期或假期信息
     */
    @Override
    public List<Map<String,String>> selectInfoByStatus(String status,int type)
    {
        return this.findInfoByParam("","","","",type,status,"","");
    }

    /**
     * 根据学期或假期信息id删除信息
     * @param id 学期或假期信息id
     * @return 是否删除成功
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Object deleteInfo(int id,int userId)
    {
        int flag;
        try{
            Map<String,Object> info=termDao.findInfoById(id);
            if(info!=null)
            {
                Object status = info.get("status");
                if(status!=null&&Integer.valueOf(status.toString())!=ConstantWord.OPEN)
                {
                    termDao.deleteInfoById(id);
                    flag = ConstantWord.SUCCESS;
                }else {
                    logger.error("学期正在启用，不能删除termMap:"+info.toString());
                    flag = ConstantWord.IS_USEING;
                }
            }else{
                logger.info("该学期不存在，termId="+id);
                flag = ConstantWord.UN_EXIST;
            }
        }catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("删除学期或假期信息异常",e);
            flag = ConstantWord.UN_KNOWN;
        }
        return flag;
    }

    /**
     * 分页查询所有学期或假期 信息
    * @param currentPage 当前页
     * @param pageSize 每页条数
     * @param type 1:学期信息；2：假期信息
     * @return 学期或假期分页信息
     */
    @Override
    public JSONObject findInfoByPage(String currentPage, String pageSize,int type)
    {
        JSONObject resultObject=new JSONObject();
        //分页查询学期信息
        List<Map<String,String>> infoList=this.findInfoByParam("","",currentPage, pageSize, type,"","","");
        //查询学期信息总数
        int totalCount=this.findInfoNumByParam(1);
        Integer size = Integer.parseInt(pageSize==null?"0":pageSize);
        Integer totalPage = 1;
        if(size!=0)
        {
            totalPage = totalCount / size;
            if (totalCount % size != 0) {
                totalPage++;
            }
        }
        resultObject.put("totalPage", totalPage);
        resultObject.put("totalCount", totalCount);
        resultObject.put("termList", infoList);
        return resultObject;
    }

    /**
     * 获取学年时间
     * @return
     */
    @Override
    public Map<String,String> getSchoolyearTime(String schoolyearId)
    {
        Map<String,String> termMap=new HashMap<>();
        List<Map<String,Object>>  termzList=termDao.findInfoBySchoolyearId(schoolyearId);
       if(termzList!=null&&!"".equals(termzList))
       {
           termMap.put("startDate",termzList.get(0).get("startDate").toString());
           termMap.put("endDate",termzList.get(termzList.size()-1).get("endDate").toString());
       }
        return termMap;
    }

    /**
     * 根据信息id查看学期信息
     * @param id 信息id
     * @return
     */
    @Override
    public Map<String,Object> selectInfoById(int id)
    {
        return termDao.findInfoById(id);
    }

    /**
     * 根据条件查询学期或假期信息
     * @param currentPage 当前页
     * @param pageSize 每页条数
     * @param type 1：学期信息；2：假期信息
     * @return
     */
    private List<Map<String,String>> findInfoByParam(String startDate,String endDate,String currentPage,String pageSize,int type,String status,String name,String schoolyearId)
    {
        logger.debug("currentpage="+currentPage+";pageSize="+pageSize+";type="+type);
        List<Map<String,String>> termList=new ArrayList<>();
        try {
            Map params=new HashMap();
            params.put("type",type);
            if(name!=null&&!"".equals(name))
            {
                params.put("name",name);
            }
            if(schoolyearId!=null&&!"".equals(schoolyearId))
            {
                params.put("schoolyearId",schoolyearId);
            }
            if(status!=null&&!"".equals(status))
            {
                params.put("status",Integer.valueOf(status));
            }
            if(startDate!=null&&!"".equals(startDate))
            {
                params.put("startDate",startDate);
            }
            if(endDate!=null&&!"".equals(endDate))
            {
                params.put("endDate",endDate);
            }
            if(currentPage!=null&&pageSize!=null&&!"".equals(currentPage)&&!"".equals(pageSize))
            {
                int beginNum=(Integer.parseInt(currentPage) - 1) * Integer.parseInt(pageSize);
                params.put("beginNum",beginNum);
                params.put("size",Integer.parseInt(pageSize));
            }
            termList=termDao.findInfoByParam(params);
        }catch (Exception e)
        {
            logger.error("根据条件查询学期或假期信息异常",e);
        }
        return termList;
    }

    /**
     * 根据条件查询信息数量
     * @param type 1：学期信息；2：假期信息
     * @return 0：操作成功；1：
     */
    private int findInfoNumByParam(int type)
    {
        int count=0;
        try {
            Map params=new HashMap();
            params.put("type",type);
            count=termDao.findInfoNumByParam(params);
        }catch (Exception e)
        {
            logger.error("根据条件查询学期或假期信息数量异常",e);
        }
        return count;
    }

    /**
     * 保存活动或假期信息
     * @param infoJson 活动或假期信息数据
     * @param operType 操作类型 add:添加；update:修改
     * @return 0：操作成功；-4：未知原因操作失败；-5：operType为update时，学期不存在
     */
    @Transactional(rollbackFor = RuntimeException.class)
    public Object saveOrUpdateInfo(JSONObject infoJson,String operType)
    {
        logger.debug(infoJson.toString());
        try{
            Map params=new HashMap();
            //学期名称
            params.put("name",infoJson.get("name"));
            params.put("startDate",(infoJson.get("startDate")+" 00:00:00"));
            params.put("endDate",(infoJson.get("endDate")+" 23:59:59"));
            //类型（1：学期信息；2：假期信息）
            params.put("type",infoJson.get("type"));
            Object schoolYearID = infoJson.get("schoolyearId");
            if(schoolYearID!=null&&!"".equals(schoolYearID))
            {
                params.put("schoolyearId",infoJson.get("schoolyearId"));
            }else{
                //保存学年信息
                this.saveSchoolyear(infoJson.get("schoolYearName").toString());
                Map<String,Object> schoolyear=schoolyearDao.selectSchoolYearByName(infoJson.get("schoolYearName").toString());
                params.put("schoolyearId",schoolyear.get("schoolyearId"));
            }
            switch (operType)
            {
                case "add":
                    params.put("userId",infoJson.get("userId"));
                    termDao.saveInfo(params);
                    break;
                case "update":
                    params.put("status",infoJson.get("status"));
                    //检测该学期是否存在
                    Map<String,Object> termMap= termDao.findInfoById((int)infoJson.get("termId"));
                    if(termMap!=null)
                    {
                        Object status =termMap.get("status");
                        if(status!=null&&Integer.valueOf(status.toString())!=ConstantWord.OPEN)
                        {
                            params.put("termId",infoJson.get("termId"));
                            termDao.updateInfo(params);
                        }else {
                            logger.error("学期正在启用，不能编辑termMap:"+termMap.toString());
                            return ConstantWord.IS_USEING;
                        }
                    }else {
                        return -5;
                    }
                    break;
                default:
                    logger.error("保存或修改活动信息异常,出现未知操作类型"+operType);
                    return -4;
            }
            return 0;
        }catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("保存或修改学期或假期信息异常",e);
            return -4;
        }
    }

    /**
     * 保存学年信息
     * @param name
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
     void  saveSchoolyear(String name)
    {
        try {
            Map params=new HashMap();
            params.put("name",name);

            schoolyearDao.saveSchoolYear(params);
        }catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("保存学年信息异常",e);
        }
    }

    /**
     * 查询所有学年信息
     * @return 学年集合
     */
    @Override
    public List<Map<String ,Object>> getAllSchoolyear()
    {
        //此处学年需要判断学年下是否存在学期

        return schoolyearDao.findSchoolYearAll();
    }

    /**
     * 查询所有学年信息
     * @return 学年集合
     */
    @Override
    public List<Map<String ,Object>> getTermBySchoolyearId(String schoolyearId)
    {
        return termDao.findInfoBySchoolyearId(schoolyearId);
    }

    /**
     * 根據学年及学期信息和参数类型获取时间范围
     * @param schoolyearId 学年id
     * @param termId 学期id
     * @param type done:已完成课时；plan:计划课时
     */
    @Override
    public Map<String,String> getTimeRange(String schoolyearId,String termId,String type)
    {
        Map<String, String> map = new HashMap<>();
        String done= "done";
        try {
            if (schoolyearId != null && !"".equals(schoolyearId)) {
                if (termId == null || "".equals(termId)) {
                    //没有学期，初始化学年时间
                    Map<String, String> schoolyearMap = this.getSchoolyearTime(schoolyearId);
                    if (schoolyearMap != null && !schoolyearMap.isEmpty()) {
                        map.put("startTime", (schoolyearMap.get("startDate")));
                        if (done.equals(type)) {
                            String endTime = this.getSmallTime((schoolyearMap.get("endDate")), DateTimeUtil.formatDateTime(new Date()));
                            map.put("endTime", endTime);
                        } else {
                            map.put("endTime", (schoolyearMap.get("endDate")));
                        }
                    } else {
                        return null;
                    }
                } else {
                    //初始化学期时间
                    Map<String, Object> termMap = this.selectInfoById(Integer.valueOf(termId));
                    if (termMap != null && !termMap.isEmpty()) {
                        map.put("startTime", (termMap.get("startDate").toString()));
                        if (done.equals(type)) {
                            String endTime = this.getSmallTime((termMap.get("endDate").toString()), DateTimeUtil.formatDateTime(new Date()));
                            map.put("endTime", endTime);
                        } else {
                            map.put("endTime", (termMap.get("endDate").toString()));
                        }
                    } else {
                        return null;
                    }
                }
            } else {
                //没有学年、学期信息情况下，已完成课时截止日期
                if (done.equals(type)) {
                    map.put("endTime", DateTimeUtil.formatDateTime(new Date()));
                }
            }
        }catch (Exception e)
        {
            logger.error("根據学年及学期信息和参数类型获取时间范围异常",e);
        }
        return map;
    }

    /**
     * 获取较小时间
     * @param time1
     * @param time2
     * @return
     */
    private String getSmallTime(String time1,String time2)
    {
        try
        {
            long time11=DateTimeUtil.parseDateTime(time1).getTime();
            long time22=DateTimeUtil.parseDateTime(time2).getTime();
            if(time11<time22)
            {
               return time1;
            }else{
               return time2;
            }
        }catch (Exception e)
        {
            logger.error("获取较小时间时，转换时间异常",e);
            return time1;
        }
    }

}
