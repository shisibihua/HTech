package com.honghe.ad;

import com.honghe.ad.campus.bean.UserInfo;
import jodd.util.collection.SortedArrayList;

import java.text.CollationKey;
import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghongbin on 2016/7/13.
 */
public final class Directory {

    private String id = "";
    private String name = "";
    private String number = "";
    private String typeId = "";
    private String typeName = "";
    private String stagesId = "";
    private String schoolYear = "";
    private String remark = "";
    private String level="";
    private String pId="";
    private String userCount="";
    private List<UserInfo> userList;
    private List<Directory> directories;

    private List<Map<String, String>> data = new ArrayList<>();

    public List<Map<String, String>> getData() {
        return data;
    }

    public Directory() {
        this("", "", "", "", "", "", "","","","","");
    }

    public Directory(String id, String name,String number,String typeId,String typeName,String stagesId,String schoolYear,String remark,String level,String pId,String userCount) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.typeId = typeId;
        this.typeName = typeName;
        this.stagesId = stagesId;
        this.schoolYear = schoolYear;
        this.remark = remark;
        this.level = level;
        this.pId = pId;
        this.userCount = userCount;
        this.userList = new ArrayList<UserInfo>();
        this.directories = new SortedArrayList<>(new Comparator<Directory>() {
            private RuleBasedCollator collator = (RuleBasedCollator) Collator
                    .getInstance(java.util.Locale.CHINA);

            @Override
            public int compare(Directory o1, Directory o2) {
                CollationKey c1 = collator.getCollationKey(o1.getName().
                        replaceAll("一", "1").replaceAll("二", "2").replaceAll("三", "3")
                        .replaceAll("四", "4").replaceAll("五", "5").replaceAll("六", "6")
                        .replaceAll("七", "7").replaceAll("八", "8").replaceAll("九", "9"));
                CollationKey c2 = collator.getCollationKey(o2.getName().
                        replaceAll("一", "1").replaceAll("二", "2").replaceAll("三", "3")
                        .replaceAll("四", "4").replaceAll("五", "5").replaceAll("六", "6")
                        .replaceAll("七", "7").replaceAll("八", "8").replaceAll("九", "9")
                );
                return collator.compare(c1.getSourceString(), c2.getSourceString());
            }
        });
    }
    public Directory(String id, String name) {
        this.id = id;
        this.name = name;
        this.directories = new SortedArrayList<>(new Comparator<Directory>() {
            private RuleBasedCollator collator = (RuleBasedCollator) Collator
                    .getInstance(java.util.Locale.CHINA);

            @Override
            public int compare(Directory o1, Directory o2) {
                CollationKey c1 = collator.getCollationKey(o1.getName().
                        replaceAll("一", "1").replaceAll("二", "2").replaceAll("三", "3")
                        .replaceAll("四", "4").replaceAll("五", "5").replaceAll("六", "6")
                        .replaceAll("七", "7").replaceAll("八", "8").replaceAll("九", "9"));
                CollationKey c2 = collator.getCollationKey(o2.getName().
                                replaceAll("一", "1").replaceAll("二", "2").replaceAll("三", "3")
                                .replaceAll("四", "4").replaceAll("五", "5").replaceAll("六", "6")
                                .replaceAll("七", "7").replaceAll("八", "8").replaceAll("九", "9")
                );
                return collator.compare(c1.getSourceString(), c2.getSourceString());
            }
        });
    }
    public void addUser(UserInfo u){
        userList.add(u);
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getStagesId() {
        return stagesId;
    }

    public void setStagesId(String stagesId) {
        this.stagesId = stagesId;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Directory> getDirectories() {
        return directories;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public void setDirectories(List<Directory> directories) {
        this.directories = directories;
    }

    public List<UserInfo> getUserList() {
        return userList;
    }

    public void setUserList(List<UserInfo> userList) {
        this.userList = userList;
    }

    public String getUserCount() {
        return userCount;
    }

    public void setUserCount(String userCount) {
        this.userCount = userCount;
    }
}
