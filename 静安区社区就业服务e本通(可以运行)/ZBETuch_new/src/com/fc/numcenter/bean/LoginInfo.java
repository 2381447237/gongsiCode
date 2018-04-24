package com.fc.numcenter.bean;

public class LoginInfo {
	private int ID;
	private int STAFF;
	private String LOGIN_TIME;
	private String NAME;
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

	public String getLOGIN_TIME() {
		return LOGIN_TIME;
	}

	public void setLOGIN_TIME(String lOGIN_TIME) {
		LOGIN_TIME = lOGIN_TIME;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public int getRecordCount() {
		return RecordCount;
	}

	public void setRecordCount(int recordCount) {
		RecordCount = recordCount;
	}

}
