package com.honghe.organization.agency.controller;

import com.honghe.dao.PageData;
import com.honghe.exception.ParamException;
import com.honghe.organization.agency.dao.Agency;
import com.honghe.organization.agency.dao.AgencyDao;
import com.honghe.user.dao.CampusDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hh on 2016/12/2.
 */
public class AgencyController {
    private AgencyDao agencyDao = AgencyDao.INSTANCE;
    private CampusDao campusDao = CampusDao.INSTANCE;

    /**
     * 1.根据id获取机构信息
     * 2.根据机构名称获取机构下的用户信息
     * 3.根据用户ID获取机构信息和用户信息
     * 4.获取所有机构信息
     *
     * @param agencyId 机构ID
     * @param agencyName 机构名称
     * @param userId 用户ID
     * @return
     * @throws Exception
     */
    public Object agencySearchGetUserInfo(Integer agencyId,String agencyName,Integer userId) throws Exception{
        if(agencyId!=null){
            return agencyDao.findAgencyById(agencyId);
        }else if(agencyName!=null && !"".equals(agencyName)){
            return agencyDao.findAgencyByName(agencyName);
        }else if(userId!=null){
            return agencyDao.findByUserId(userId);
        }else {
            return agencyDao.findAgency();
        }
    }

    /**
     * 根据机构ID或机构名称查询机构信息(机构表更换为campus)
     * @param agencyId 机构ID
     * @param agencyName 机构名称
     * @return
     * @throws Exception
     */
    public Object agencySearch(String agencyId,String agencyName)throws Exception{
        if(agencyId!=null&&!"".equals(agencyId)){
            return campusDao.findAgencyById(agencyId);
        }else if(agencyName!=null && !"".equals(agencyName)){
            return campusDao.getAgencyByName(agencyName);
        }else {
            return campusDao.findAgency();
        }
    }

    /**
     * 根据机构ID获取子机构信息
     * @param agencyId 机构ID
     * @return
     * @throws Exception
     */
    public Agency agencySonSearch(Integer agencyId) throws Exception{
        if(agencyId==null){
            throw new ParamException();
        }
        return campusDao.findSonAgency(agencyId);
    }

    /**
     * 根据机构名称分页查询机构下的用户信息
     * @param page 页数
     * @param pageSize 每页数据数
     * @param agencyName 机构名称
     * @return
     * @throws Exception
     */
    public PageData agencySearchByPageGetUserInfo(Integer page, Integer pageSize, String agencyName)throws Exception{
        if(page==null || pageSize == null){
            throw new ParamException();
        }
        String name = "";
        if(agencyName != null){
            name = agencyName;
        }
        return agencyDao.findAgencyByName(page,pageSize,name);
    }
    /**
     * 根据机构名称分页查询机构信息(模糊查询)
     * @param page 页数
     * @param pageSize 每页数据数
     * @param agencyName 机构名称
     * @return
     * @throws Exception
     */
    public PageData agencySearchByPage(Integer page,Integer pageSize,String agencyName) throws Exception{
        if(page==null || pageSize == null){
            throw new ParamException();
        }
        String name = "";
        if(agencyName != null){
            name = agencyName;
        }
        return agencyDao.getAgencyByName1(page,pageSize,name);
    }

    /**
     *  删除机构
     * @param agencyId 机构ID
     * @return
     * @throws Exception
     */
    public boolean agencyDelete(Integer agencyId)throws Exception{
        if(agencyId==null || "".equals(agencyId)){
            throw new ParamException();
        }
        return agencyDao.deleteAgencyById(agencyId);
    }

    /**
     * 删除机构下的用户
     * 1.根据用户ID，机构ID删除
     * 2.根据用户ID删除
     * @param userId 用户ID
     * @param agencyId 机构ID
     * @return
     * @throws Exception
     */
    public boolean agencyUserRelationDelete(String userId,Integer agencyId)throws Exception{
        if(userId==null || "".equals(userId)){
            throw new ParamException();
        }
        int userId_int = 0;
        try {
            userId_int = Integer.parseInt(userId);
        }catch (ClassCastException e){
            throw new ParamException();
        }
        if(agencyId!=null){
            return agencyDao.deleteUserAgencyRelation(agencyId,userId_int);
        }else {
            return agencyDao.deleteAgencyRelation(userId);
        }
    }

    /**
     *  添加机构
     * @param agencyName
     * @param pId
     * @param agencyLevel
     * @return
     * @throws Exception
     */
    public long agencyAdd(String agencyName,String pId ,String agencyLevel)throws Exception{
        if(agencyName==null || "".equals(agencyName) || pId==null || "".equals(pId)){
            throw new ParamException();
        }
        Map<String,Object> map = new HashMap<>();
        map.put("agencyName",agencyName);
        map.put("pId",pId);
        map.put("agencyLevel",agencyLevel);
        return agencyDao.addAgency(map);
    }

    /**
     * 机构用户关联关系添加
     * @param userId
     * @param agencyId
     * @return
     * @throws Exception
     */
    public long agencyUserRelationAdd(String userId,String agencyId)throws Exception{
        if(userId==null || "".equals(userId) || agencyId==null || "".equals(agencyId)){
            throw new ParamException();
        }
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("agencyId",agencyId);
        return agencyDao.addAgencyRelation(map);
    }

    /**
     * 更新机构用户关联关系
     * @param userId
     * @param agencyId
     * @return
     * @throws Exception
     */
    public boolean agencyUserRelationUpdate(String userId,String agencyId)throws Exception{
        if(userId==null || "".equals(userId) || agencyId==null || "".equals(agencyId)){
            throw new ParamException();
        }
        return agencyDao.updateAgencyRelation(userId,agencyId);
    }

    /**
     * 更新机构信息
     * @param agencyName
     * @param pId
     * @param agencyLevel
     * @param agencyId
     * @return
     * @throws Exception
     */
    public boolean agencyUpdate(String agencyName,String pId ,String agencyLevel,Integer agencyId)throws Exception{
        if(agencyId == null){
            throw new ParamException();
        }
        if(agencyName==null && pId==null && agencyLevel==null){
            throw new ParamException();
        }
        Map<String,Object> map = new HashMap<>();
        map.put("agencyId",agencyId);
        if(agencyName!=null){
            map.put("agencyName",agencyName);
        }
        if(pId!=null){
            map.put("pId",pId);
        }
        if(agencyLevel!=null){
            map.put("agencyLevel",agencyLevel);
        }
        return campusDao.update(map);
    }
    // TODO
    public Map<String, String> agencyIdGetManager(Integer agencyId) throws Exception{
        if(agencyId == null){
            throw new ParamException();
        }
        return agencyDao.findAdminByAgencyId(agencyId);
    }

    /**
     * 根据机构ID获取子一级机构信息
     * @param agencyId
     * @return
     * @throws Exception
     */
    public List<Map<String, String>> agencyGetSon(Integer agencyId) throws Exception{
        if(agencyId == null){
            throw new ParamException();
        }
        return agencyDao.findSonLowerLevelAgency(agencyId);
    }

    /**
     * 根据多个机构ID，获取机构信息(机构表更换为campus)
     * @param agencyIds
     * @return
     * @throws Exception
     */
    public List<Map<String, String>> agencyGetMany(String agencyIds) throws Exception{
        if(agencyIds == null){
            throw new ParamException();
        }
        return campusDao.getAgencys(agencyIds);
    }

    /**
     * 根据机构ID，获取父级机构信息
     * @param agencyId
     * @return
     * @throws Exception
     */
    public Map<String, String> agencyParent(String agencyId) throws Exception{
        if(agencyId == null){
            throw new ParamException();
        }
        return campusDao.getParentAgency(agencyId);
    }

    public Map<String, String> agencyFirstAdmin(Integer agencyId) throws Exception{
        return agencyDao.getFirstAdmin();
    }

    /**
     * 获取机构级别
     * @param agencyId 机构ID
     * @return
     * @throws Exception
     */
    public Integer agencyGetLevel(Integer agencyId) throws Exception{
        return campusDao.getAgencyLevel(agencyId,0);
    }

    /**
     * 通过机构对应的ip获取机构信息
     * @param ip
     * @return
     */
    public List<Map<String,String>> agencyGetInfoByIp(String ip){
        return campusDao.getCampusInfoByIp(ip);
    }
}
