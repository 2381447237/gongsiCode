package com.fc.wenjuan.beans;

import java.util.List;

public class WenJuanType {

	private int ID;
	private String TITLE;
	private String TEXT;
	private String NO;
	private String CREATE_TIME;
	private int RecordCount;
	// private String Detils;
	private List<WenJuanInfo> juanInfos;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getTITLE() {
		return TITLE;
	}

	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}

	public String getTEXT() {
		return TEXT;
	}

	public void setTEXT(String tEXT) {
		TEXT = tEXT;
	}

	public String getNO() {
		return NO;
	}

	public void setNO(String nO) {
		NO = nO;
	}

	public String getCREATE_TIME() {
		return CREATE_TIME;
	}

	public void setCREATE_TIME(String cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}

	public int getRecordCount() {
		return RecordCount;
	}

	public void setRecordCount(int recordCount) {
		RecordCount = recordCount;
	}

	public List<WenJuanInfo> getJuanInfos() {
		return juanInfos;
	}

	public void setJuanInfos(List<WenJuanInfo> juanInfos) {
		this.juanInfos = juanInfos;
	}
}
