package com.honghe.area.dao.tree;

import com.honghe.area.dao.areaDao.AreaDao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 获取所有子节点
 * @author lichunming
 */
public class StrNode {

    private List<Integer> returnList = new ArrayList<Integer>();

    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list   分类表
     * @param typeId 传入的父节点ID
     * @return String
     */
    public String getChildNodes(List<Node> list, Integer typeId) {
        if (list == null && typeId == null) {
            return "";
        }
        for (Iterator<Node> iterator = list.iterator(); iterator.hasNext(); ) {
            Node node = (Node) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            int cid = node.getId();
            int id = typeId.intValue();
            if (cid == id) {
                recursionFn(list, node);
            }
        }
        return returnList.toString();
    }

    private void recursionFn(List<Node> list, Node node) {
        // 得到子节点列表
        List<Node> childList = getChildList(list, node);
        // 判断是否有子节点
        if (hasChild(list, node)) {
            returnList.add(node.getId());
            Iterator<Node> it = childList.iterator();
            while (it.hasNext()) {
                Node n = (Node) it.next();
                recursionFn(list, n);
            }
        } else {
            returnList.add(node.getId());
        }
    }

    /**
     * 得到子节点列表
     * @param list
     * @param node
     * @return
     */
    private List<Node> getChildList(List<Node> list, Node node) {
        List<Node> nodeList = new ArrayList<Node>();
        Iterator<Node> it = list.iterator();
        while (it.hasNext()) {
            Node n = (Node) it.next();
            if (n.getpId().intValue() == node.getId().intValue()) {
                nodeList.add(n);
            }
        }
        return nodeList;
    }

    /**
     * 判断是否有子节点
     * @param list
     * @param node
     * @return
     */
    private boolean hasChild(List<Node> list, Node node) {
        return getChildList(list, node).size() > 0 ? true : false;
    }


    /**
     * 返回当前节点下面所有的子节点
     * @param id 当前节点ID
     * @return
     */
    public String strNode(Integer id) {
        List<Node> list = new ArrayList<Node>();
        AreaDao areaDao = new AreaDao();
        //从数据库获取出地点的数据
        List<Map<String, String>> maps = areaDao.list();
        Node menu;
        Node node;
        for (int i = 0; i < maps.size(); i++) {
            menu = new Node();
            Map<String, String> map = maps.get(i);
            //当前节点的id
            Integer cid = Integer.valueOf(map.get("id").toString());
            //当前节点的名称
            String cname = map.get("name").toString();
            //父节点的id
            Integer pid = Integer.valueOf(map.get("p_id").toString());
            node = new Node(cid, cname, pid);
            list.add(node);
        }
        //得到所有字节的id
        String strNode1 = this.getChildNodes(list, id);
        //对字符串形式进行整理
        String serNode2 = strNode1.replace("[", "(");
        String serNode3 = serNode2.replace("]", ")");
        return serNode3;
    }

}
