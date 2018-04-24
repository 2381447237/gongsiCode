package com.example.Seats.beans;

import java.io.Serializable;

public class InfusionInfo implements Serializable{
	private String HISORDERID;
	private String CARDNO;
	private String PATIENTNAME;
	private String GENDER;
	private String BIRTHDATE;
	private String ORDERDATE;
	private String ORDERDOCTORNAME;
	private String ORDERDEPTNAME;
	private String DIAGINFO;
	private String HISCLINICNO;
	
	
	public String getHISORDERID() {
		return HISORDERID;
	}
	public void setHISORDERID(String hISORDERID) {
		HISORDERID = hISORDERID;
	}
	public String getCARDNO() {
		return CARDNO;
	}
	public void setCARDNO(String cARDNO) {
		CARDNO = cARDNO;
	}
	public String getPATIENTNAME() {
		return PATIENTNAME;
	}
	public void setPATIENTNAME(String pATIENTNAME) {
		PATIENTNAME = pATIENTNAME;
	}
	public String getGENDER() {
		return GENDER;
	}
	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}
	public String getBIRTHDATE() {
		return BIRTHDATE;
	}
	public void setBIRTHDATE(String bIRTHDATE) {
		BIRTHDATE = bIRTHDATE;
	}
	public String getORDERDATE() {
		return ORDERDATE;
	}
	public void setORDERDATE(String oRDERDATE) {
		ORDERDATE = oRDERDATE;
	}
	public String getORDERDOCTORNAME() {
		return ORDERDOCTORNAME;
	}
	public void setORDERDOCTORNAME(String oRDERDOCTORNAME) {
		ORDERDOCTORNAME = oRDERDOCTORNAME;
	}
	public String getORDERDEPTNAME() {
		return ORDERDEPTNAME;
	}
	public void setORDERDEPTNAME(String oRDERDEPTNAME) {
		ORDERDEPTNAME = oRDERDEPTNAME;
	}
	public String getDIAGINFO() {
		return DIAGINFO;
	}
	public void setDIAGINFO(String dIAGINFO) {
		DIAGINFO = dIAGINFO;
	}
	public String getHISCLINICNO() {
		return HISCLINICNO;
	}
	public void setHISCLINICNO(String hISCLINICNO) {
		HISCLINICNO = hISCLINICNO;
	}
	public InfusionInfo(String HISOrderID,String CardNo,String PatientName,String Gender
			,String BirthDate,String OrderDate,String OrderDoctorName,String OrderDeptName
			,String DiagInfo,String HISClinicNo){
		super();
		this.HISORDERID=HISOrderID;
		this.CARDNO=CardNo;
		this.PATIENTNAME=PatientName;
		this.GENDER=Gender;
		this.BIRTHDATE=BirthDate;
		this.ORDERDATE=OrderDate;
		this.ORDERDOCTORNAME=OrderDoctorName;
		this.ORDERDEPTNAME=OrderDeptName;
		this.DIAGINFO=DiagInfo;
		this.HISCLINICNO=HISClinicNo;
	}
	public InfusionInfo(){
		super();
	}
	@Override
	public String toString() {
		return "InfusionInfo [HISOrderID=" + HISORDERID +", CardNo=" + CARDNO +", PatientName=" + PATIENTNAME 
				 +", Gender=" + GENDER +", BirthDate=" + BIRTHDATE+", OrderDate=" + ORDERDATE 
				 +", OrderDoctorName=" + ORDERDOCTORNAME +", OrderDeptName=" + ORDERDEPTNAME 
				 +", DiagInfo=" + DIAGINFO+", HISClinicNo=" + HISCLINICNO+"]";
	}
}
