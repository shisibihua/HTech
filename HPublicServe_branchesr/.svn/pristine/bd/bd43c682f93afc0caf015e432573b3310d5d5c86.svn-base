package com.honghe.campus.user2sip.dao;


import com.honghe.campus.user2sip.cache.User2SipCacheDao;
import com.honghe.campus.user2sip.util.db.JDBCConnUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 不用JdbcTemplateUtil,改用JDBCConnUtil
 * Created by 赵健宇
 */
public class User2SipDao {


    public boolean add(String userId, String sipId) {
        //    return JdbcTemplateUtil.getJdbcTemplate().add("insert into campus_user2sip(user_id,sip_id) values(?,?)", new Object[]{userId, sipId});
        return JDBCConnUtil.getJdbctemplate().add("insert into campus_user2sip(user_id,sip_id) values(?,?)", new Object[]{userId, sipId});
    }

    public void delete(String userId) {
        throw new UnsupportedOperationException();
    }

    public Set<String> find() {
        throw new UnsupportedOperationException();
    }

    public Map<String,String> findBySipId(String sipId){
        return JDBCConnUtil.getJdbctemplate().find("select  u.user_id as userId, u.user_name as userName,"+
                "u.user_type as userType,u.user_realname as userRealName,u.user_path as userPath,u.user_avatar as userAvatar," +
                "u.user_gender as userGender,u.user_birthday as userBirthday,u.user_mobile as userMobile,u.user_email as userEmail," +
                "u.user_address as userAddress,u.user_num as userNum,u.user_regdate as userRegdate,u.user_status as userStatus," +
                "u.user_card as userCard,u.user_course as userCourse,u.user_info as userInfo,cus.sip_id as sipId,'0' as status  from campus_user2sip as cus,user u where cus.sip_id='" + sipId +"'");
//        return JdbcTemplateUtil.getJdbcTemplate().find("select  u.user_id as userId, u.user_name as userName,"+
//                "u.user_type as userType,u.user_realname as userRealName,u.user_path as userPath,u.user_avatar as userAvatar," +
//                "u.user_gender as userGender,u.user_birthday as userBirthday,u.user_mobile as userMobile,u.user_email as userEmail," +
//                "u.user_address as userAddress,u.user_num as userNum,u.user_regdate as userRegdate,u.user_status as userStatus," +
//                "u.user_card as userCard,u.user_course as userCourse,u.user_info as userInfo,cus.sip_id as sipId,'0' as status  from campus_user2sip as cus,user u where cus.sip_id='" + sipId +"'");
        }

        public List<Map<String, String>> findByUserId(String userId) {
            String sql = "select  u.user_id as userId, u.user_name as userName,"+
                    "u.user_type as userType,u.user_realname as userRealName,u.user_path as userPath,u.user_avatar as userAvatar," +
                    "u.user_gender as userGender,u.user_birthday as userBirthday,u.user_mobile as userMobile,u.user_email as userEmail," +
                    "u.user_address as userAddress,u.user_num as userNum,u.user_regdate as userRegdate,u.user_status as userStatus," +
                    "u.user_card as userCard,u.user_course as userCourse,u.user_info as userInfo, u2s.sip_id as sipId,'0' as status from user u left join campus_user2sip u2s on u.user_id=u2s.user_id where u.user_id=" + userId;
            return JDBCConnUtil.getJdbctemplate().findList(sql);
        }

        public List<Map<String, String>> findByOrgId(String orgId){
            String sql = "select a.*,u2s.sip_id as sipId, '0' as status from (" +
                    "select u.user_id as userId, u.user_name as userName,u.user_type as userType,u.user_realname as userRealName,u.user_path as userPath,u.user_avatar as userAvatar,u.user_gender as userGender,u.user_birthday as userBirthday,u.user_mobile as userMobile,u.user_email as userEmail,u.user_num as userNum,u.user_address as userAddress,u.user_regdate as userRegdate,u.user_status as userStatus,u.user_card as userCard,u.user_course as userCourse,u.user_info as userInfo from user as u,(SELECT user_id FROM ad_campus2user where campus_id='" + orgId +"') as b where u.user_id=b.user_id and u.user_status=1 ) a " +
                    "left join campus_user2sip u2s on a.userId=u2s.user_id";
            return JDBCConnUtil.getJdbctemplate().findList(sql);
        }

        public boolean has(String userId, String sipId) {
            long n = JDBCConnUtil.getJdbctemplate().
                    count("select count(*) from campus_user2sip where user_id=" + userId + " and sip_id='" + sipId + "'");
            if (n > 0) {
                return true;
            }
            return false;
        }


        public static void main(String[] args) {
//        JdbcTemplateUtil.setConnectionInfo("jdbc:mysql://192.168.16.170:3306/service?user=root&password=bhjRjxwC8EBqaJC7&useUnicode=true&characterEncoding=utf8");
//        JdbcTemplateUtil.setConnectionInfo("jdbc:mysql://192.168.21.42:3306/service?user=root&password=bhjRjxwC8EBqaJC7&useUnicode=true&characterEncoding=utf8");

       //     System.out.println(new User2SipDao().toString()+new User2SipDao().has("1","093c98d7ee59a58b66cd27d74fe70340"));
       //     System.out.println(new User2SipDao().toString()+new User2SipDao().findByUserId("1"));
       //     System.out.println(User2SipCacheDao.INSTANCE.findByUserId("1"));
        }


    }
