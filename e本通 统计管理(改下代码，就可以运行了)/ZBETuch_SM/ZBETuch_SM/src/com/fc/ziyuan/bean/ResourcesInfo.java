package com.fc.ziyuan.bean;

import java.io.Serializable;

public class ResourcesInfo implements Serializable{
	
	private int ID;
	private String TYPE;
	private String SET_TIME;
	private int CREATE_STAFF;
	private String CREATE_DATE;
	private int MASTER_ID;
	private int XUCHA;
	private int YICHA;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public String getSET_TIME() {
		return SET_TIME;
	}
	public void setSET_TIME(String sET_TIME) {
		SET_TIME = sET_TIME;
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
	public int getMASTER_ID() {
		return MASTER_ID;
	}
	public void setMASTER_ID(int mASTER_ID) {
		MASTER_ID = mASTER_ID;
	}
	public int getXUCHA() {
		return XUCHA;
	}
	public void setXUCHA(int xUCHA) {
		XUCHA = xUCHA;
	}
	public int getYICHA() {
		return YICHA;
	}
	public void setYICHA(int yICHA) {
		YICHA = yICHA;
	}
	
	

}
