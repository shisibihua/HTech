package com.honghe.ad.util;

import java.util.*;

/**
 * Created by mz on 2018/3/5.
 */
    public class MultipleTree {

    private List<Long> returnList = new ArrayList<Long>();

    public static Set<Node> getTreeNode(List<Map<String, String>> dataList) {
        Set<Node> nodes = new HashSet<>();
        // 节点列表
        Map nodeList = new HashMap();
        // 根节点
        Node root = null;
        // 根据结果集构造节点列表
        if (dataList == null) {
            return Collections.EMPTY_SET;
        }

        for (Iterator it = dataList.iterator(); it.hasNext();) {
            Map<String, String> nodeMap = (Map<String, String>) it.next();
            Node node = new Node();
            node.setId(nodeMap.get("id"));
            node.setName(nodeMap.get("name"));
            node.setTypeId(nodeMap.get("typeId"));
            if (nodeMap.get("pId") == null) {
                node.setParentId("0");
            } else {
                node.setParentId(nodeMap.get("pId"));
            }
            node.setStageId(nodeMap.get("stagesId"));
            nodeList.put(node.getId(), node);
        }
        // 构造无序的多叉树
        Set entrySet = nodeList.entrySet();

        for (Iterator it = entrySet.iterator(); it.hasNext();) {
            Node node = (Node) ((Map.Entry) it.next()).getValue();
            if (node.getParentId() == null || "".equals(node.getParentId()) || "0".equals(node.getParentId())) {
                root = node;
                nodes.add(node);
            } else {
                ((Node) nodeList.get(node.getParentId())).addChild(node);
            }
        }
        return nodes;
    }

}
