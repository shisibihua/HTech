package com.honghe.ad.campus.bean;
import java.util.HashMap;
import java.util.Map;

public enum CampusType {
	/**
	 * 对接展能的机构类型
	 */
	CAMPUS_TYPE_SCHOOL("-1"),//学校
	CAMPUS_TYPE_STAGE("0"),//学段
	CAMPUS_TYPE_GRADE("1"),//年级
	CAMPUS_TYPE_CLASSES("2"); //班级

	private String type;

	private static final Map<String, CampusType> map = new HashMap<String, CampusType>();

	static {
		for (CampusType campusType : CampusType.values()) {
			map.put(campusType.getType(), campusType);
		}
	}

	public static CampusType getCampusType(String type) {
		return map.get(type);
	}

	CampusType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}