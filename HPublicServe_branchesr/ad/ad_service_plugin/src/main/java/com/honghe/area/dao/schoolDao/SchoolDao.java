package com.honghe.area.dao.schoolDao;

import com.honghe.ad.util.JdbcTemplateUtil;
import com.honghe.area.dao.BasicDao;
import com.honghe.area.dao.picture.Picture;
import com.honghe.area.reflect.ParameterValue;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author LC
 * @date 2017/3/13
 */
public class SchoolDao extends BasicDao{
    /**
     * 获取学校的信息
     * @return
     */
    public List<Map<String,String>> list(){
        String sql = "SELECT name as cn_name,english_name as en_name,address as location, post_code as postcode,telephone as phone,fax_code as fax," +
                " email as mail,url as website,city as region FROM ad_school";
        return JdbcTemplateUtil.getJdbcTemplate().findList(sql);
    }

    /**
     * 添加（编辑）学校的信息（只有一个）
     * @param cnName    学校的中文名称
     * @param enName    学校的中英文名称
     * @param region      地区（城市）
     * @param location    地址（详细地址）
     * @param phone        电话/手机
     * @param postcode    邮编
     * @param mail         邮箱
     * @param website     网址
     * @return    返回一个long值，根据long值判断是否成功
     */
    public boolean todo(@ParameterValue("cn_name")String cnName,@ParameterValue("en_name")String enName,@ParameterValue("region")String region,
                        @ParameterValue("location")String location,@ParameterValue("phone")String phone,@ParameterValue("fax")String fax,@ParameterValue("postcode")String postcode,
                        @ParameterValue("mail")String mail,@ParameterValue("website")String website){
        String sql;
        //删除展能ad_area表外键
        JdbcTemplateUtil.getJdbcTemplate().execute("alter table ad_area drop foreign key FK_h52oxwy6c9j8sd5hk8wg8n86d");
        if(this.list().size()>0){
            //大于0证明有数据，在操作的时候属于修改
            sql = "UPDATE ad_school SET name='"+cnName+"',english_name='"+enName+"',city='"+region+"',address='"+location+"'" +
                    ",telephone='"+phone+"',fax_code='"+fax+"',post_code='"+postcode+"',email='"+mail+"',url='"+website+"'";
            boolean bl = JdbcTemplateUtil.getJdbcTemplate().update(sql);
            //修改学校的信息
            if(bl){
                //学校信息修改成功之后，同时在地点表中进行修改
                String sql2 = "UPDATE ad_area SET name='"+cnName+"',type_id =1  WHERE p_id=0";
                return JdbcTemplateUtil.getJdbcTemplate().update(sql2);
            }else {
                return false;
            }
        }else {
            //证明没有数据，在操作的时候属于添加
            sql = "INSERT INTO ad_school(name,english_name,city,address,telephone,fax_code,post_code,email,url) VALUES('"+cnName+"','" +
                    enName+"','"+region+"','"+location+"','"+phone+"','"+fax+"','"+postcode+"','"+mail+"','"+website+"')";
            long lg = JdbcTemplateUtil.getJdbcTemplate().add(sql);
            //添加学校的信息
            if(lg>0){
                //学校信息添加成功之后，同时在地点那添加此信息，作为地点的根节点
                String sql2 = "INSERT INTO ad_area(id,name,p_id,type_id)VALUES(1,'"+cnName+"',"+"0"+",1)";
                long lg2 = JdbcTemplateUtil.getJdbcTemplate().add(sql2);
                if(lg2>0){
                    //判断地点是否添加
                    return true;
                }else {
                    //如果没填加成功，将学校信息也删除
                    JdbcTemplateUtil.getJdbcTemplate().delete("DELETE FROM ad_school");
                    return false;
                }
            }else {
                return false;
            }
        }
    }

    /**
     * 学校图片的操作(上传，编辑)
     * @param imgName   图片字符串
     * @return
     */
    public boolean todopic(@ParameterValue("img_name")String imgName){
        String imgUrl = "";
        //通过存储服务获取图片的地址
        imgUrl = new Picture().imagePath(imgName);
        //获取学校在学校表里面所对应的数据
        List<Map<String, String>> schoolList = JdbcTemplateUtil.getJdbcTemplate().findList("SELECT id FROM ad_school");
        //取出学校id
        String schoolId = schoolList.get(0).get("id");
        String sql = "SELECT * FROM ad_area2picture WHERE area_id = "+schoolId;
        //查看数据库中是否设置过图片
        List<Map<String,String>> list = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        if(list.size()>0){
            //如果设置过，则操作为修改操作
            String sql2 = "UPDATE ad_area2picture SET pic_url = '"+imgUrl+"' WHERE area_id = "+schoolId;
            boolean bl = JdbcTemplateUtil.getJdbcTemplate().update(sql2);
            if(bl){
                return true;
            }else {
                return false;
            }
        }else {
            //如果没有，则为添加操作
            String sql2 = "INSERT INTO ad_area2picture(area_id,pic_url) VALUES("+schoolId+",'"+imgUrl+"')";
            long lg = JdbcTemplateUtil.getJdbcTemplate().add(sql2);
            if(lg>0) {
                return true;
            }else {
                return false;
            }
        }
    }

    /**
     * 获取图片地址
     * @return
     */
    public String picurl(){
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        String ip = "localhost:8181";
        try {
            ip = InetAddress.getLocalHost().getHostAddress()+":8181";
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //获取学校在学校表里面所对应的数据
        List<Map<String, String>> schoolList = JdbcTemplateUtil.getJdbcTemplate().findList("SELECT id FROM ad_school");
        //取出学校id
        String schoolId = schoolList.get(0).get("id");
        String sql = "SELECT pic_url FROM ad_area2picture WHERE area_id = "+schoolId;
        List<Map<String,String>>  list = JdbcTemplateUtil.getJdbcTemplate().findList(sql);
        jsonObject.put("ip",ip);
        jsonObject.put("urls",list);
        jsonArray.add(jsonObject);
        return jsonArray.toString();
    }
}
