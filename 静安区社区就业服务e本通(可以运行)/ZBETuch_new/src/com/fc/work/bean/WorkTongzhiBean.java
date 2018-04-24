package com.fc.work.bean;

import java.io.Serializable;

public class WorkTongzhiBean implements Serializable {

	private int ID;
	private String TITLE;
	private String DOC;
	private String NOTICE_TIME;
	private int CREATE_STAFF;
	private String CREATE_DATE;
	private int UPDATE_STAFF;
	private String UPDATE_DATE;
	private int RecordCount;
	private String CREATE_STAFF_NAME;

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

	public String getDOC() {
		return DOC;
	}

	public void setDOC(String dOC) {
		DOC = dOC;
	}

	public String getNOTICE_TIME() {
		return NOTICE_TIME;
	}

	public void setNOTICE_TIME(String nOTICE_TIME) {
		NOTICE_TIME = nOTICE_TIME;
	}

	public int getCREATE_STAFF() {
		return CREATE_STAFF;
	}

	public void setCREATE_STAFF(int cREATE_STAFF) {
		CREATE_STAFF = cREATE_STAFF;
	}

	public String getCREATE_DATE() {
		return CREATE_DATE;
	}

	public void setCREATE_DATE(String cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}

	public int getUPDATE_STAFF() {
		return UPDATE_STAFF;
	}

	public void setUPDATE_STAFF(int uPDATE_STAFF) {
		UPDATE_STAFF = uPDATE_STAFF;
	}

	public String getUPDATE_DATE() {
		return UPDATE_DATE;
	}

	public void setUPDATE_DATE(String uPDATE_DATE) {
		UPDATE_DATE = uPDATE_DATE;
	}

	public int getRecordCount() {
		return RecordCount;
	}

	public void setRecordCount(int recordCount) {
		RecordCount = recordCount;
	}

	public String getCREATE_STAFF_NAME() {
		return CREATE_STAFF_NAME;
	}

	public void setCREATE_STAFF_NAME(String cREATE_STAFF_NAME) {
		CREATE_STAFF_NAME = cREATE_STAFF_NAME;
	}
}
