package com.fc.person.beans;

import java.io.Serializable;

public class FuXuanXiangBean implements Serializable {

	private int ID;
	private String SFZ;
	private int STAFF;
	private String SERVICE_TIME;
	private int TYPE;
	private String MARK;
	private String STAFF_NAME;
	private String TYPE_NAME;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getSFZ() {
		return SFZ;
	}

	public void setSFZ(String sFZ) {
		SFZ = sFZ;
	}

	public int getSTAFF() {
		return STAFF;
	}

	public void setSTAFF(int sTAFF) {
		STAFF = sTAFF;
	}

	public String getSERVICE_TIME() {
		return SERVICE_TIME;
	}

	public void setSERVICE_TIME(String sERVICE_TIME) {
		SERVICE_TIME = sERVICE_TIME;
	}

	public int getTYPE() {
		return TYPE;
	}

	public void setTYPE(int tYPE) {
		TYPE = tYPE;
	}

	public String getMARK() {
		return MARK;
	}

	public void setMARK(String mARK) {
		MARK = mARK;
	}

	public String getSTAFF_NAME() {
		return STAFF_NAME;
	}

	public void setSTAFF_NAME(String sTAFF_NAME) {
		STAFF_NAME = sTAFF_NAME;
	}

	public String getTYPE_NAME() {
		return TYPE_NAME;
	}

	public void setTYPE_NAME(String tYPE_NAME) {
		TYPE_NAME = tYPE_NAME;
	}

}
