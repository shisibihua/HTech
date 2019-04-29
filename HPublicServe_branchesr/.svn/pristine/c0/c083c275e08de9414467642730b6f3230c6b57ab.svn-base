package com.honghe.user.dao.impl;

import com.honghe.user.util.JdbcTemplateUtil;
import com.honghe.security.MD5;
import com.honghe.user.dao.BaseUserDao;
import com.honghe.user.dao.IUserV2Dao;

import java.util.*;

/**
 * 用户
 *
 * @author zhaowj
 * @create 2017-01-13 14:54
 **/
public class UserV2UserDao extends BaseUserDao implements IUserV2Dao {

    public final static UserV2UserDao INSTANCE = new UserV2UserDao();
    public final static String SQL = "select  u.user_id as userId, u.user_name as userName," +
            "u.user_type as userType,u.user_realname as userRealName,u.user_realname as name,u.user_path as userPath,u.user_avatar as userAvatar," +
            "u.user_gender as userGender,u.user_birthday as userBirthday,u.user_mobile as userMobile,u.user_email as userEmail," +
            "u.user_address as userAddress,u.user_num as userNum,u.user_regdate as userRegdate,u.user_status as userStatus," +
            "u.user_card as userCard,u.user_course as userCourse,u.user_info as userInfo," +
            "a.agency_id as agencyId, a.agency_name as agencyName,a.p_id as pId,a.agency_level as agencyLevel " +
            "from user u left join user2agency on u.user_id=user2agency.user_id left join agency a on user2agency.agency_id=a.agency_id ";


    /**
     * @param agencyId
     * @return
     */
    @Override
    public Map<String, String> userAdminSearchByAgency(String agencyId) {
        String sql = "select u.user_id as userId,u.user_realname as userRealName,u2.campus_id AS agencyId from user as u,(select u2a.user_id,u2a.campus_id from ad_campus2user as u2a where u2a.campus_id=(select ag1.p_id from ad_campus as ag1 where ag1.id= '" + agencyId + "' )) as u2 where u.user_id =u2.user_id and ( u.user_type = 1 or u.user_type=2)";
        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        if (result.isEmpty()) {
            return new HashMap<>();
        }
        return result.get(0);
    }

    /**
     * �����û����������ȡ�û���Ϣ
     *
     * @param name
     * @param password
     * @return
     */
    @Override
    public final Map<String, String> find(String name, String password) {
        String sql = "select  user_id as userId, user_name as userName,user_pwd as userPwd,user_salt as userSalt," +
                "user_type as userType,user_realname as userRealName,user_path as userPath,user_avatar as userAvatar," +
                "user_gender as userGender,user_birthday as userBirthday,user_mobile as userMobile,user_email as userEmail," +
                "user_address as userAddress,user_num as userNum,user_regdate as userRegdate,user_status as userStatus, " +
                "user_card as userCard,user_course as userCourse,user_info as userInfo from user ";
        Map<String, String> result = JdbcTemplateUtil.getJdbcTemplate().find(sql + "where (user_name='" + name + "' or user_email='" + name + "' or user_mobile='" + name + "' or user_num='" + name + "') and (user_status=1 or user_status=0)");
        if (result.isEmpty()) {
            return Collections.emptyMap();
        }
        String userSalt = result.get("userSalt").toString();
        password = new MD5().encrypt(password + userSalt);
        if (result.get("userPwd").equals(password)) {
            result.remove("userPwd");
            result.remove("userSalt");
            return result;
        }
        return Collections.emptyMap();
    }

    @Override
    public final Map<String, String> find(int userId) {
        Map result = JdbcTemplateUtil.getJdbcTemplate().find(SQL + " where u.user_id=" + userId);
        return result;

    }
}
