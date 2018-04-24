package com.example.ppu_infusion;

import java.io.Serializable;

public class AdminInfo implements Serializable{
	private int STAFFID;
	private String STAFFNO;
	private String PWD;
	private String STAFFNAME;
	private String STATUS;
	
	public int getSTAFFID() {
		return STAFFID;
	}
	public void setSTAFFID(int sTAFFID) {
		STAFFID = sTAFFID;
	}
	public String getSTAFFNO() {
		return STAFFNO;
	}
	public void setSTAFFNO(String sTAFFNO) {
		STAFFNO = sTAFFNO;
	}
	public String getPWD() {
		return PWD;
	}
	public void setPWD(String pWD) {
		PWD = pWD;
	}
	public String getSTAFFNAME() {
		return STAFFNAME;
	}
	public void setSTAFFNAME(String sTAFFNAME) {
		STAFFNAME = sTAFFNAME;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String sTATUS) {
		STATUS = sTATUS;
	}
	public AdminInfo(int staffId, String staffNo, String password,
			String staffName, String Status) {
		super();
		this.STAFFID = staffId;
		this.STAFFNO = staffNo;
		this.PWD = password;
		this.STAFFNAME = staffName;
		this.STATUS=Status;
	}
	public AdminInfo(){
		super();
	}
	@Override
	public String toString() {
		return "AdminInfo [StaffId=" + STAFFID +", StaffNo=" + STAFFNO +", Pwd=" + PWD 
				 +", StaffName=" + STAFFNAME +", Status=" + STATUS +"]";
	}
}
