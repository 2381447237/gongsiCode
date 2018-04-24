package com.fc.recritmentmanager.bean;

public class ERecordInfo {
	
	private int ID;
	private int STAFF;
	private String CREATE_DATE;
	private String NAME;
	private String TYPE;
	private int RecordCount;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getSTAFF() {
		return STAFF;
	}
	public void setSTAFF(int sTAFF) {
		STAFF = sTAFF;
	}
	public String getCREATE_DATE() {
		return CREATE_DATE;
	}
	public void setCREATE_DATE(String cREATE_DATE) {
		CREATE_DATE = cREATE_DATE;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public int getRecordCount() {
		return RecordCount;
	}
	public void setRecordCount(int recordCount) {
		RecordCount = recordCount;
	}
	
}
