package com.honghe.web.user.util;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by mz on 2018/3/5.
 */
public class Node {

    private String id;

    private String name;

    private String parentId;

    private String typeId;

    private String stageId;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    private Set<Node> nodes = new LinkedHashSet<>();

    public Set<Node> getNodes() {
        return nodes;
    }

    //    private Children children = new Children();

//    public void sortChildren() {
//        if (children != null && children.getSize() != 0) {
//            children.sortChildren();
//        }
//    }
//
    public void addChild(Node node) {
        this.nodes.add(node);
    }

    public String getStageId() {
        return stageId;
    }

    public void setStageId(String stageId) {
        this.stageId = stageId;
    }

    //
//
////    static class Children {
////        private List list = new ArrayList();
////
////        public int getSize() {
////            return list.size();
////        }
////
////        public void addChild(Node node) {
////            list.add(node);
////        }
////
////        public void sortChildren() {
////            // 对本层节点进行排序
////            // 可根据不同的排序属性，传入不同的比较器，这里传入ID比较器
////            Collections.sort(list, new NodeIDComparator());
////            // 对每个节点的下一层节点进行排序
////            for (Iterator it = list.iterator(); it.hasNext();) {
////                ((Node) it.next()).sortChildren();
////            }
////        }
////    }
//
//    static class NodeIDComparator implements Comparator {
//        public int compare(Object o1, Object o2) {
//            int j1 = Integer.parseInt(((Node) o1).id);
//            int j2 = Integer.parseInt(((Node) o2).id);
//            return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));
//        }
//    }
}
