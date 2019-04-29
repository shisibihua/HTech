package com.honghe.area.dao.tree;

import com.honghe.ad.util.JdbcTemplateUtil;
import com.honghe.area.dao.areaDao.AreaDao;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by cnLock on 2017/3/9.
 * Tree解析工具类
 * @author Administrator
 * @version 1.0
 */
public class TreeNode {

    private List<Node> treeNodeList = new ArrayList<Node>();

    private List<Long> returnList = new ArrayList<Long>();
    private final static String SCHOOLZONE_NOTEXIST = "0";
    private final static String SCHOOLZONE_EXIST = "1";

    public TreeNode(List<Node> list){
        treeNodeList = list;
    }

    /**
     *获取当前级的信息
     * @param nodeId
     * @return
     */
    public Node getNodeById(int nodeId){
        Node treeNode = new Node();
        for (Node item : treeNodeList) {
            if (item.getId() == nodeId) {
                treeNode = item;
                break;
            }
        }
        return treeNode;
    }

    /**
     *获得子级的信息
     * @param nodeId
     * @return
     */
    public List<Node> getChildrenNodeById(int nodeId){
        List<Node> childrenTreeNode = new ArrayList<Node>();
        for (Node item : treeNodeList) {
            if(item.getpId() == nodeId){
                childrenTreeNode.add(item);
            }
        }
        return childrenTreeNode;
    }

    /**
     * 递归生成Tree结构数据
     * @param rootId
     * @return
     */
    public Node generateTreeNode(int rootId){
        //获得当前级信息
        Node root = this.getNodeById(rootId);
        //如果根节点是学校的话则添加校区标识信息
        if (root.getpId()!=null && root.getpId()==0){
            long zoneCount = JdbcTemplateUtil.getJdbcTemplate().count("select count(*) from ad_area aa,ad_school_zone asz where aa.zone_id=asz.id");
            //表示地点树中不存在校区
            String flag = SCHOOLZONE_NOTEXIST;
            if (zoneCount>0){
                //表示地点树中存在校区
                flag = SCHOOLZONE_EXIST;
            }
            root.setFlag(flag);
        }
        //获得子级的信息
        List<Node> childrenTreeNode = this.getChildrenNodeById(rootId);
        for (Node item : childrenTreeNode) {
            Node node = this.generateTreeNode(item.getId());
            root.getDirectories().add(node);
        }
        return root;
    }

}


