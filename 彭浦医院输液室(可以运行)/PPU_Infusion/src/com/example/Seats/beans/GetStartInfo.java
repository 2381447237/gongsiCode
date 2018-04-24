package com.example.Seats.beans;

import java.io.Serializable;
//这个实体类对应的url:http://192.168.11.11:8088/json/GetStart.aspx?STAFFID=1&PATIENTID=45
//[{"INFUSIONDETAILID":1163,"INFUSIONID":1114,"DRUGNAME":"500毫升(氯化钠注射液、注射用头孢曲松钠)","DRUGSPEC":"0.9%×500ml/瓶","DRUGTRADENAME":"","DRUGMANUFACTURER":"",
//"DRUGYBCODE":"X00986740150030","DOSAGE":500.0,"DOSAGEUNIT":"ml","INFUSIONGROUPNO":1,"BAGVOLUME":500,"REMAINDERVOLUME":500,"DRIPCNTSPERMINUTE":80,"TUBEID":59,
//"DRIPCNTSPERML":23,"DRIPSTARTTIME":"2017-01-03T14:29:44.197","DRIPLASTSTARTTIME":"2017-01-03T14:29:44.197","DRIPFINISHTIME":null,"INFUSIONSTATUS":1,"DURATION":0,
//"QRCODE":"20170103000001","RecordCount":0}]

public class GetStartInfo implements Serializable{
	
	private String INFUSIONDETAILID;
	private String INFUSIONID;//要这个
	private String DRUGNAME;
	private String DRUGSPEC;//这个显示 XX
	private String DRUGTRADENAME;
	private String DRUGMANUFACTURER;
	private String DRUGYBCODE;
	private String DOSAGE;//===
	private String DOSAGEUNIT;//====
	private String INFUSIONGROUPNO;//要这个
	private String BAGVOLUME;
	private String REMAINDERVOLUME;
	private String DRIPCNTSPERMINUTE;
	private String TUBEID;
	private String DRIPCNTSPERML;
	private String DRIPSTARTTIME;
	private String DRIPLASTSTARTTIME;
	private String DRIPFINISHTIME;
	private String INFUSIONSTATUS;
	private String DURATION;
	private String QRCODE;
	private int RecordCount;
	private String InfusionStatusName;
	
	
	public String getInfusionStatusName() {
		return InfusionStatusName;
	}

	public void setInfusionStatusName(String infusionStatusName) {
		InfusionStatusName = infusionStatusName;
	}



	//注意这个
	private String TUBENAME;
	
	public String getTUBENAME() {
		return TUBENAME;
	}

	public void setTUBENAME(String tUBENAME) {
		TUBENAME = tUBENAME;
	}



//	public String getINFUSIONDETAILID() {
//		return INFUSIONDETAILID;
//	}
//
//
//
//	public void setINFUSIONDETAILID(String iNFUSIONDETAILID) {
//		INFUSIONDETAILID = iNFUSIONDETAILID;
//	}



	public String getINFUSIONID() {
		return INFUSIONID;
	}



	public void setINFUSIONID(String iNFUSIONID) {
		INFUSIONID = iNFUSIONID;
	}



	public String getDRUGNAME() {
		return DRUGNAME;
	}



	public void setDRUGNAME(String dRUGNAME) {
		DRUGNAME = dRUGNAME;
	}



	public String getDRUGSPEC() {
		return DRUGSPEC;
	}



	public void setDRUGSPEC(String dRUGSPEC) {
		DRUGSPEC = dRUGSPEC;
	}



	public String getDRUGTRADENAME() {
		return DRUGTRADENAME;
	}



	public void setDRUGTRADENAME(String dRUGTRADENAME) {
		DRUGTRADENAME = dRUGTRADENAME;
	}



	public String getDRUGMANUFACTURER() {
		return DRUGMANUFACTURER;
	}



	public void setDRUGMANUFACTURER(String dRUGMANUFACTURER) {
		DRUGMANUFACTURER = dRUGMANUFACTURER;
	}



	public String getDRUGYBCODE() {
		return DRUGYBCODE;
	}



	public void setDRUGYBCODE(String dRUGYBCODE) {
		DRUGYBCODE = dRUGYBCODE;
	}



	public String getDOSAGE() {
		return DOSAGE;
	}



	public void setDOSAGE(String dOSAGE) {
		DOSAGE = dOSAGE;
	}



	public String getDOSAGEUNIT() {
		return DOSAGEUNIT;
	}



	public void setDOSAGEUNIT(String dOSAGEUNIT) {
		DOSAGEUNIT = dOSAGEUNIT;
	}



	public String getINFUSIONGROUPNO() {
		return INFUSIONGROUPNO;
	}



	public void setINFUSIONGROUPNO(String iNFUSIONGROUPNO) {
		INFUSIONGROUPNO = iNFUSIONGROUPNO;
	}



	public String getBAGVOLUME() {
		return BAGVOLUME;
	}



	public void setBAGVOLUME(String bAGVOLUME) {
		BAGVOLUME = bAGVOLUME;
	}



	public String getREMAINDERVOLUME() {
		return REMAINDERVOLUME;
	}



	public void setREMAINDERVOLUME(String rEMAINDERVOLUME) {
		REMAINDERVOLUME = rEMAINDERVOLUME;
	}



	public String getDRIPCNTSPERMINUTE() {
		return DRIPCNTSPERMINUTE;
	}



	public void setDRIPCNTSPERMINUTE(String dRIPCNTSPERMINUTE) {
		DRIPCNTSPERMINUTE = dRIPCNTSPERMINUTE;
	}



	public String getTUBEID() {
		return TUBEID;
	}



	public void setTUBEID(String tUBEID) {
		TUBEID = tUBEID;
	}



	public String getDRIPCNTSPERML() {
		return DRIPCNTSPERML;
	}



	public void setDRIPCNTSPERML(String dRIPCNTSPERML) {
		DRIPCNTSPERML = dRIPCNTSPERML;
	}



	public String getDRIPSTARTTIME() {
		return DRIPSTARTTIME;
	}



	public void setDRIPSTARTTIME(String dRIPSTARTTIME) {
		DRIPSTARTTIME = dRIPSTARTTIME;
	}



	public String getDRIPLASTSTARTTIME() {
		return DRIPLASTSTARTTIME;
	}



	public void setDRIPLASTSTARTTIME(String dRIPLASTSTARTTIME) {
		DRIPLASTSTARTTIME = dRIPLASTSTARTTIME;
	}



	public String getDRIPFINISHTIME() {
		return DRIPFINISHTIME;
	}



	public void setDRIPFINISHTIME(String dRIPFINISHTIME) {
		DRIPFINISHTIME = dRIPFINISHTIME;
	}



	public String getINFUSIONSTATUS() {
		return INFUSIONSTATUS;
	}



	public void setINFUSIONSTATUS(String iNFUSIONSTATUS) {
		INFUSIONSTATUS = iNFUSIONSTATUS;
	}



	public String getDURATION() {
		return DURATION;
	}



	public void setDURATION(String dURATION) {
		DURATION = dURATION;
	}



	public String getQRCODE() {
		return QRCODE;
	}



	public void setQRCODE(String qRCODE) {
		QRCODE = qRCODE;
	}



	public int getRecordCount() {
		return RecordCount;
	}



	public void setRecordCount(int recordCount) {
		RecordCount = recordCount;
	}



	public GetStartInfo(String iNFUSIONDETAILID, String iNFUSIONID,
			String dRUGNAME, String dRUGSPEC, String dRUGTRADENAME,
			String dRUGMANUFACTURER, String dRUGYBCODE, String dOSAGE,
			String dOSAGEUNIT, String iNFUSIONGROUPNO, String bAGVOLUME,
			String rEMAINDERVOLUME, String dRIPCNTSPERMINUTE, String tUBEID,
			String dRIPCNTSPERML, String dRIPSTARTTIME,
			String dRIPLASTSTARTTIME, String dRIPFINISHTIME,
			String iNFUSIONSTATUS, String dURATION, String qRCODE,
			int recordCount) {
		super();
		INFUSIONDETAILID = iNFUSIONDETAILID;
		INFUSIONID = iNFUSIONID;
		DRUGNAME = dRUGNAME;
		DRUGSPEC = dRUGSPEC;
		DRUGTRADENAME = dRUGTRADENAME;
		DRUGMANUFACTURER = dRUGMANUFACTURER;
		DRUGYBCODE = dRUGYBCODE;
		DOSAGE = dOSAGE;
		DOSAGEUNIT = dOSAGEUNIT;
		INFUSIONGROUPNO = iNFUSIONGROUPNO;
		BAGVOLUME = bAGVOLUME;
		REMAINDERVOLUME = rEMAINDERVOLUME;
		DRIPCNTSPERMINUTE = dRIPCNTSPERMINUTE;
		TUBEID = tUBEID;
		DRIPCNTSPERML = dRIPCNTSPERML;
		DRIPSTARTTIME = dRIPSTARTTIME;
		DRIPLASTSTARTTIME = dRIPLASTSTARTTIME;
		DRIPFINISHTIME = dRIPFINISHTIME;
		INFUSIONSTATUS = iNFUSIONSTATUS;
		DURATION = dURATION;
		QRCODE = qRCODE;
		RecordCount = recordCount;
	}



	public GetStartInfo(){
		super();
	}



	@Override
	public String toString() {
		return "GetStartInfo [INFUSIONDETAILID=" + INFUSIONDETAILID
				+ ", INFUSIONID=" + INFUSIONID + ", DRUGNAME=" + DRUGNAME
				+ ", DRUGSPEC=" + DRUGSPEC + ", DRUGTRADENAME=" + DRUGTRADENAME
				+ ", DRUGMANUFACTURER=" + DRUGMANUFACTURER + ", DRUGYBCODE="
				+ DRUGYBCODE + ", DOSAGE=" + DOSAGE + ", DOSAGEUNIT="
				+ DOSAGEUNIT + ", INFUSIONGROUPNO=" + INFUSIONGROUPNO
				+ ", BAGVOLUME=" + BAGVOLUME + ", REMAINDERVOLUME="
				+ REMAINDERVOLUME + ", DRIPCNTSPERMINUTE=" + DRIPCNTSPERMINUTE
				+ ", TUBEID=" + TUBEID + ", DRIPCNTSPERML=" + DRIPCNTSPERML
				+ ", DRIPSTARTTIME=" + DRIPSTARTTIME + ", DRIPLASTSTARTTIME="
				+ DRIPLASTSTARTTIME + ", DRIPFINISHTIME=" + DRIPFINISHTIME
				+ ", INFUSIONSTATUS=" + INFUSIONSTATUS + ", DURATION="
				+ DURATION + ", QRCODE=" + QRCODE + ", RecordCount="
				+ RecordCount + "]";
	}

}
