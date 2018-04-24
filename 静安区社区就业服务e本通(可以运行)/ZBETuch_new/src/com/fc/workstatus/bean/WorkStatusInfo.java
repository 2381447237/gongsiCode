package com.fc.workstatus.bean;

import java.io.Serializable;

public class WorkStatusInfo implements Serializable {

	private int ID;
	private String TITLE;
	private String DOC;
	private String NEWS_TIME;
	private int CREATE_STAFF;
	private String CREATE_DATE;
	private int UPDATE_STAFF;
	private String UPDATE_DATE;
	private int TYPE;
	private int RecordCount;
	private String CREATE_STAFF_NAME;
	private String TYPE_NAME;

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

	public String getNEWS_TIME() {
		return NEWS_TIME;
	}

	public void setNEWS_TIME(String nEWS_TIME) {
		NEWS_TIME = nEWS_TIME;
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

	public int getTYPE() {
		return TYPE;
	}

	public void setTYPE(int tYPE) {
		TYPE = tYPE;
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

	public String getTYPE_NAME() {
		return TYPE_NAME;
	}

	public void setTYPE_NAME(String tYPE_NAME) {
		TYPE_NAME = tYPE_NAME;
	}

}
