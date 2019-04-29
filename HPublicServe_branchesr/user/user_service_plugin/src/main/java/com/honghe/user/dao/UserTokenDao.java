package com.honghe.user.dao;


import com.honghe.user.util.JdbcTemplateUtil;
import com.honghe.dao.PageData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: 北京鸿合盛视数字媒体技术有限公司</p>
 *
 * @author zhanghongbin
 * @date 2016/4/8
 */
@Deprecated
public class UserTokenDao {


//    public boolean add(long userId, String token) {
//        return JdbcTemplateUtil.getJdbcTemplate().add("insert into user_token(user_id,token) values(?,?)", new Object[]{userId, token});
//    }
//
//    public boolean add(String[] userId, String token) {
//        List<String> sqlList = new ArrayList<>();
//        for (String _userId : userId) {
//            sqlList.add("insert into user_token(user_id,token) values(" + _userId +",'" + token +"')");
//        }
//        return JdbcTemplateUtil.getJdbcTemplate().execute(sqlList.toArray(new String[userId.length]));
//
//    }
//
//    public PageData findByNotToken(int page, int pageSize, String token) {
//        int count = (int) JdbcTemplateUtil.getJdbcTemplate().count("select count(*) from user as u,(select user_id,group_concat(token) from user_token  where user_id not in(select user_id from user_token where token='" + token + "') group by user_id) as u_token where u.user_id =u_token.user_id and u.user_status=1");
//        int start = PageData.calcFirstItemIndexOfPage(page, pageSize, count);
//        String sql = "select u.user_id as userId, user_name as userName," +
//                " user_type as userType,user_realname as userRealName,user_path as userPath,user_avatar as userAvatar," +
//                " user_gender as userGender,user_birthday as userBirthday,user_mobile as userMobile,user_email as userEmail," +
//                " user_address as userAddress,user_num as userNum,user_regdate as userRegdate,user_status as userStatus,u_token.token as token," +
//                " u.user_card as userCard,u.user_course as userCourse,u.user_info as userInfo" +
//                " from user as u,(select  user_id,group_concat(token) as token from user_token  where user_id not in(select user_id from user_token where token='" + token + "') group by user_id) as u_token where u.user_id =u_token.user_id and u.user_status=1 order by u.user_regdate desc limit " + start + "," + pageSize;
//        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
//        PageData pageData = new PageData(page, count, pageSize, result);
//        return pageData;
//    }
//
//    public PageData findByToken(int page, int pageSize, String token) {
//        int count = (int) JdbcTemplateUtil.getJdbcTemplate().count("select count(*) from user as u,user_token as u_token where u.user_id =u_token.user_id and u_token.token='" + token +"' and u.user_status=1");
//        int start = PageData.calcFirstItemIndexOfPage(page, pageSize, count);
//        String sql = "select u.user_id as userId, user_name as userName," +
//                " user_type as userType,user_realname as userRealName,user_path as userPath,user_avatar as userAvatar," +
//                " user_gender as userGender,user_birthday as userBirthday,user_mobile as userMobile,user_email as userEmail," +
//                " user_address as userAddress,user_num as userNum,user_regdate as userRegdate,user_status as userStatus,u_token.token as token," +
//                " u.user_card as userCard,u.user_course as userCourse,u.user_info as userInfo" +
//                " from user as u,user_token as u_token where u.user_id =u_token.user_id and u_token.token='" + token +"' and u.user_status=1 order by u.user_regdate desc limit " + start + "," + pageSize;
//        List<Map<String, String>> result = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
//        PageData pageData = new PageData(page, count, pageSize, result);
//        return pageData;
//    }
//
//    public static void main(String[] args) {
//        JdbcTemplateUtil.setConnectionInfo("jdbc:mysql://192.168.16.170:3306/service?user=root&password=admin&useUnicode=true&characterEncoding=utf8");
//        UserTokenDao userTokenDao = new UserTokenDao();
//       // System.out.println(userTokenDao.find(1, 10, "hhtic").getItems().size());
////        PageData pd = userTokenDao.findByToken(1, 10, "reapp");
//        PageData pd = userTokenDao.findByNotToken(1,10,"reapp");
//        System.out.println();
//    }
}
