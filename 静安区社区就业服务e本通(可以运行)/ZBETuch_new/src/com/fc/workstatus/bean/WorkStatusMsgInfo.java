package com.fc.workstatus.bean;

import java.io.Serializable;

public class WorkStatusMsgInfo implements Serializable {
	private int ID;
	private int MASTER_ID;
	private String MSG;
	private String CREATE_TIME;
	private int CREATE_STAFF;
	private int RecordCount;
	private String StaffName;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getMASTER_ID() {
		return MASTER_ID;
	}

	public void setMASTER_ID(int mASTER_ID) {
		MASTER_ID = mASTER_ID;
	}

	public String getMSG() {
		return MSG;
	}

	public void setMSG(String mSG) {
		MSG = mSG;
	}

	public String getCREATE_TIME() {
		return CREATE_TIME;
	}

	public void setCREATE_TIME(String cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}

	public int getCREATE_STAFF() {
		return CREATE_STAFF;
	}

	public void setCREATE_STAFF(int cREATE_STAFF) {
		CREATE_STAFF = cREATE_STAFF;
	}

	public int getRecordCount() {
		return RecordCount;
	}

	public void setRecordCount(int recordCount) {
		RecordCount = recordCount;
	}

	public String getStaffName() {
		return StaffName;
	}

	public void setStaffName(String staffName) {
		StaffName = staffName;
	}

}
