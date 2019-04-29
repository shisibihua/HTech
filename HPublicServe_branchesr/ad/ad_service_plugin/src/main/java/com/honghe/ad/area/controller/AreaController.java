package com.honghe.ad.area.controller;

import com.honghe.ad.area.dao.Area2DeviceDao;
import com.honghe.ad.area.dao.AreaDao;
import com.honghe.ad.excetion.ParamException;
import com.honghe.ad.excetion.RepeatingDateException;
import com.honghe.dao.PageData;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 地点的逻辑处理类
 *
 * @author lyx
 * @create 2016-11-28 13:04
 **/
public class AreaController {
    Logger logger=Logger.getLogger(Area2DeviceDao.class);
    private AreaDao areaDao = AreaDao.INSTANCE;
    private Area2DeviceDao area2DeviceDao = Area2DeviceDao.INSTANCE;


    /**
     * 根据地点ID，查询地点信息
     *
     * @param areaId 地点id (适用于多个地点id 用,分隔)
     * @return 地点信息map
     * @throws ParamException
     */
    public Object areaInfoSearch(String areaId) throws ParamException {
        Object reValue = new Object();

        if (areaId != null) {
            reValue = areaDao.findByAreaId(areaId);
        }  else {
            throw new ParamException();
        }
        return reValue;
    }

    /**
     * 1.设备ID不为空，根据设备ID，获取地点信息
     * 2.type=0，根据设备名称，获取地点信息
     * 3.type=1，获取所有地点信息
     *
     * @param deviceId
     * @param type
     * @param dtypeName
     *
     * @return 地点信息
     *
     */
    public Object areaSearch(String deviceId, String type, String dtypeName,String areaType) throws ParamException {
        Object reValue = new Object();

        if (deviceId != null) {
            reValue = areaDao.findByDeviceId(deviceId);
        } else if (type != null) {
            int typeFlag=0;
            try{
                typeFlag = Integer.parseInt(type);
            }catch (Exception e){
                e.printStackTrace();
            }
            if (typeFlag == 1) {
                reValue = AreaDao.INSTANCE.find(dtypeName);
            } else if (typeFlag == 0) {
                reValue = areaDao.find();
            }
        }
        else if (areaType != null) {
            reValue = areaDao.findType(areaType);
        }

        else {
             throw new ParamException();
        }
        return reValue;
    }

    /**
     * 增加地点结构
     *
     * @param name 地点名称
     * @param pId  上级id
     *
     * @return 添加成功返回添加数据的ID
     * @throws ParamException
     */
    public long areaAdd(String pId, String name, String areaType,String campus,String number,String isSelect,String remark) throws ParamException {
        long reValue;
        if (name == null || pId == null) {
            throw new ParamException();
        }
        Map<String, Object> pram = new HashMap<>();
        pram.put("name", name);
        pram.put("pId", pId);
        pram.put("typeId", areaType);
        pram.put("campus", campus);
        pram.put("number", number);
        pram.put("isSelect", isSelect);
        pram.put("remark", remark);

        //判断同级目录是否重复
        if (!areaDao.check(pram)) {
            reValue = -2;
        } else {
            reValue = areaDao.add(pram);
        }
        return reValue;
    }

    /**
     * 删除地点结构
     *
     * @param id 多个id用,分隔
     *
     * @return 是否删除成功
     *
     * @throws ParamException
     */
    public boolean areaDelete(String id) throws ParamException {
        boolean reValue = false;
        if (id == null) {
            throw new ParamException();
        }
        reValue = areaDao.delete(id);
        return reValue;
    }

    /**
     * 修改地点结构
     *
     * @param id   地点id
     * @param name 名称
     *
     * @return 是否修改成功
     */
    public boolean areaUpdate(String id,String pId, String name, String areaType,String campus,String number,String isSelect,String remark) throws RepeatingDateException, ParamException {
        boolean reValue = false;
        if (id == null || name == null) {
            throw new ParamException();
        }
        Map<String, Object> pram = new HashMap<>();
        pram.put("id", id);
        pram.put("name", name);
        pram.put("pId", pId);
        pram.put("areaType", areaType);
        pram.put("campus", campus);
        pram.put("number", number);
        pram.put("isSelect", isSelect);
        pram.put("remark", remark);
        //判断同级目录是否重复
        if (!areaDao.check(pram)) {
            throw new RepeatingDateException();
        }
        reValue = areaDao.update(pram);
        return reValue;
    }

    /**
     * 根据地点id分页获取所有设备
     *
     * @param page       页数
     * @param pageSize   每页记录数
     * @param areaId     地点id
     * @param deviceName 设备名称  可以为null
     * @return
     * @throws ParamException
     */
    public PageData area2DeviceSearchByPage(Integer page, Integer pageSize, String areaId, String deviceName) throws ParamException {
        PageData pageData = new PageData();
        if (page == null || pageSize == null) {
            throw new ParamException();
        }
        pageData = area2DeviceDao.find(page, pageSize, areaId, deviceName);
        return pageData;
    }

    /**
     * 在地点中增加设备
     *
     * @param areaId   地点id
     * @param deviceId 设备id 多个用,分隔
     *
     * @return
     * @throws ParamException
     */
    public boolean area2DeviceAdd(String areaId, String deviceId) throws ParamException {
        boolean reValue = false;
        if (areaId == null || deviceId == null) {
            throw new ParamException();
        }
        reValue = area2DeviceDao.add(areaId, deviceId.split(","));
        return reValue;
    }

    /**
     * 在地点中增加设备
     * 1 根据id直接删除
     * 2 根据areaId和deviceId (多个用,分隔)删除
     * 3 根据deviceId (多个用,分隔)进行删除
     *
     * @param id       设备id
     * @param areaId   地点Id
     * @param deviceId 设备Id(多个用,分隔)
     * @return
     * @throws ParamException
     */
    public boolean area2DeviceDelete(String id, String areaId, String deviceId) throws ParamException {
        boolean reValue = false;
        if (id != null) {
            reValue = area2DeviceDao.delete(id.split(","));
        } else {
            if (areaId == null && deviceId != null) {
                reValue = area2DeviceDao.deleteByDeviceId(deviceId.split(","));
            } else if (areaId != null && deviceId != null) {
                reValue = area2DeviceDao.delete(areaId, deviceId.split(","));
            } else {
                throw new ParamException();
            }
        }
        return reValue;
    }

    /**
     * 查询设备是否分配到地点下
     * @param hostId
     * @return
     */
    public boolean area2DeviceSearchByHostId(String hostId){
        logger.error(this.getClass().getSimpleName()+":hostId"+hostId);
        boolean re_value = false;
        if(hostId!=null && !"".equals(hostId)){
            re_value=Area2DeviceDao.INSTANCE.findAreaIdByHostId(hostId);
        }
        return re_value;
    }
}
