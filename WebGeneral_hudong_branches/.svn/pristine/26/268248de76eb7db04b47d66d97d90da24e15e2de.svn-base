package com.honghe.tech.service.impl;

import com.honghe.tech.dao.PeriodDao;
import com.honghe.tech.dao.SubjectDao;
import com.honghe.tech.entity.Subject;
import com.honghe.tech.service.SubjectService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xinqinggang on 2018/1/25.
 * @author
 */
@Service
public class SubjectServiceImpl implements SubjectService {
    Logger logger= Logger.getLogger(SubjectServiceImpl.class);
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private PeriodDao periodDao;

    /**
     * 查找学科
     * @param id 学科id
     * @return  返回学科
     */
    @Override
    public String selectSubNameById(int id)
    {
        String subName="";
        try {
            subName = subjectDao.selectSubNameById(id);
        } catch (Exception e) {
            logger.error("根据学科id查询学科名称异常", e);
        }
        return subName;
    }

    /**
     * 根据学段id查看所有学科信息
     * @param periodId 学段id
     * @return 学科集合
     */
    @Override
    public List<Subject> selectAllSubByPeriodId(int periodId)
    {
        List<Subject> list=new ArrayList();
        try {
            list = subjectDao.selectAllSubByPeriodId(periodId);
        } catch (Exception e) {
            logger.error("查询所有学科异常", e);
        }
        return list;
    }

    /**
     * 根据学科id查询学科信息
     * @param id 学科id
     * @return 学科信息
     */
    @Override
    public Map<String, Object> selectSubById(int id) {
        return subjectDao.selectSubById(id);
    }

    /**
     * 查询所有学段
     * @return 学段集合
     */
    @Override
    public List<Map<String,Object>> getAllPeriod()
    {
        return periodDao.selectAllPeriod();
    }

    /**
     * 根据学段保存学科信息
     * @param subName 学科名称
     * @param periodId 学段id
     * @param userId 用户id
     * @return true/false
     */
    @Override
    public Object saveSubjectByPeriodId(String subName,int periodId,int userId)
    {
        //检测学科是否存在
        Subject sub=subjectDao.selectSubByPeriodIdAndName(periodId, subName);
        if(sub!=null)
        {
            return -2;
        }else {
            return this.saveSub(subName,periodId);
        }
    }


    /**
     * 更改学科信息
     * @param subName 学科名称
     * @param periodId 学段id
     * @param subId 学科id
     * @param userId 用户id
     * @return
     */
    @Override
    public Object updateSubject(String subName,int periodId,int subId,int userId)
    {
        //根据学科id查询学科名，如果为空，则该学科不存在
        String name=subjectDao.selectSubNameById(subId);
        if(name==null)
        {
            return -5;
        }else {
            //根据学段及学科名称查询，如果为空，则该学科不存在
            Subject sub=subjectDao.selectSubByPeriodIdAndName(periodId, subName);
            if(sub!=null)
            {
                return -2;
            }else {
                return this.updateSub(subName,periodId,subId);
            }
        }
    }

    /**
     * 根据学科id删除课程
     * @param subId
     * @param userId
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Object delSub(int subId,int userId)
    {
        //增加flag判断值
        int flag;
        try {
            //根据学科id查询学科名，如果为空，则该学科不存在
            String name=subjectDao.selectSubNameById(subId);
            if(name==null)
            {
                flag= -5;
            }else {
                subjectDao.deleteSubjectBySubjectId(subId);
                flag= 0;
            }
        }catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("根据学科删除课程失败,学科id="+subId,e);
            flag= -4;
        }
        return  flag;
    }

    /**
     * 保存学科
     * @param subName 学科名称
     * @param periodId 学段id
     * @return true/false
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Object saveSub(String subName, int periodId)
    {
        try{
            Map subMap=new HashMap();
            subMap.put("name",subName);
            subMap.put("periodId",periodId);
            subjectDao.saveSubject(subMap);
            return  0;
        }catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("添加学科异常");
            return -4;
        }
    }

    /**
     * 编辑学科
     * @param subName 学科名称
     * @param periodId 学段id
     * @return
     */
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public Object updateSub(String subName,int periodId,int subId)
    {
        try{
            Map subMap=new HashMap();
            subMap.put("name",subName);
            subMap.put("periodId",periodId);
            subMap.put("subId",subId);
            subjectDao.updateSubject(subMap);
            return 0;
        }catch (Exception e)
        {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            logger.error("修改学科异常");
            return -4;
        }
    }
}
