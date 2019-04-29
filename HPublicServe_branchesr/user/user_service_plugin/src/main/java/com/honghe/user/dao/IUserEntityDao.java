package com.honghe.user.dao;/**
 * Created by lyx on 2017-01-11 0011.
 */

import com.honghe.user.dao.entity.Parent;
import com.honghe.user.dao.entity.User;
import org.apache.poi.ss.formula.functions.T;

/**
 * 用户
 *
 * @author liuyanxue
 * @create 2017-01-11 14:52
 **/
public interface IUserEntityDao {

    /**
     * 依据系统编号返回用户Id
     *
     * @param code 系统编号
     * @return
     */
    public User getIdByCode(String code);

    public Boolean isHas(String userName, String userMobile, String userEmail, String userNum, String updateCode);
    public User checkUser(String userName, String userMobile, String userEmail, String userNum, String updateCode);
//    public Object getEntityByCode(final String code, T obj, final String key);
//    public  <T> T  getEntityByCode(final String code,  class<T>, final String key) ;

    }
