package com.fc.person.beans;

import java.io.Serializable;

public class TypeInfo implements Serializable {
	private int ID;
	private String NAME;
	private int CREATE_STAFF;
	private String CREATE_DATE;
	private int RecordCount;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
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

	public int getRecordCount() {
		return RecordCount;
	}

	public void setRecordCount(int recordCount) {
		RecordCount = recordCount;
	}
}
