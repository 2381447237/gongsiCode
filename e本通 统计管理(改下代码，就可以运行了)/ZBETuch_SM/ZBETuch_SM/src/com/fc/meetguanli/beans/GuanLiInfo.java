package com.fc.meetguanli.beans;

import java.io.Serializable;

public class GuanLiInfo implements Serializable {
	private int id;
	private String title;
	private String doc;
	private String meeting_time;
	private int metting_add;
	private String creat_staff_name;
	private Object checks;
	
	public GuanLiInfo() {
		super();
	}

	public GuanLiInfo(int id, String title, String doc, String meeting_time,
			int metting_add, String creat_staff_name, Object checks) {
		super();
		this.id = id;
		this.title = title;
		this.doc = doc;
		this.meeting_time = meeting_time;
		this.metting_add = metting_add;
		this.creat_staff_name = creat_staff_name;
		this.checks = checks;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public String getMeeting_time() {
		return meeting_time;
	}

	public void setMeeting_time(String meeting_time) {
		this.meeting_time = meeting_time;
	}

	public int getMetting_add() {
		return metting_add;
	}

	public void setMetting_add(int metting_add) {
		this.metting_add = metting_add;
	}

	public String getCreat_staff_name() {
		return creat_staff_name;
	}

	public void setCreat_staff_name(String creat_staff_name) {
		this.creat_staff_name = creat_staff_name;
	}

	public Object getChecks() {
		return checks;
	}

	public void setChecks(Object checks) {
		this.checks = checks;
	}

	@Override
	public String toString() {
		return "GuanLiInfo [id=" + id + ", title=" + title + ", doc=" + doc
				+ ", meeting_time=" + meeting_time + ", metting_add="
				+ metting_add + ", creat_staff_name=" + creat_staff_name
				+ ", checks=" + checks + "]";
	}
	
}	