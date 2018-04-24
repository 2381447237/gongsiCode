package com.example.Seats.beans;

import java.io.Serializable;

import android.text.TextUtils;

public class SeatsInfo implements Serializable{
	private String SEATNO;
	private String SEATSTATUS;
	private String INFUSIONID;
	private String PATIENTID;
	private String CARDNO;
	private String PATIENTNAME;
	private String GENDER;
	private String AGE;
	private String BAGCNTS;
	private String COMPLETEDBAGCNTS;
	private String TOTALDURATION;
	private String TOTALVOLUME;
	//private String INFUSIONDETAILID;
	private String REMAINDERRATE;
	private String DRIPCNTSPERMINUTE;
	private String TUBEID;
	private String DRIPCNTSPERML;
	private String TUBENAME;
	private String INFUSIONSTATUS;
	private String INFUSIONSTATUSNAME;
	private String REMAINDERVOLUME;//Κ£ΣΰΜε»ύ
	private String REMAINDERTIME;
	private int INFUSIONGROUPNO;
    
	public int getINFUSIONGROUPNO() {
		return INFUSIONGROUPNO;
	}
	public void setINFUSIONGROUPNO(int iNFUSIONGROUPNO) {
		INFUSIONGROUPNO = iNFUSIONGROUPNO;
	}
	public String getSEATNO() {
		return SEATNO;
	}
	public void setSEATNO(String sEATNO) {
		SEATNO = sEATNO;
	}
	public String getSEATSTATUS() {
		return SEATSTATUS;
	}
	public void setSEATSTATUS(String sEATSTATUS) {
		SEATSTATUS = sEATSTATUS;
	}
	public String getINFUSIONID() {
		return INFUSIONID;
	}
	public void setINFUSIONID(String iNFUSIONID) {
		INFUSIONID = iNFUSIONID;
	}
	public String getPATIENTID() {
		return PATIENTID;
	}
	public void setPATIENTID(String pATIENTID) {
		PATIENTID = pATIENTID;
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
	public String getAGE() {
		return AGE;
	}
	public void setAGE(String aGE) {
		AGE = aGE;
	}
	public String getBAGCNTS() {
		return BAGCNTS;
	}
	public void setBAGCNTS(String bAGCNTS) {
		BAGCNTS = bAGCNTS;
	}
	public String getCOMPLETEDBAGCNTS() {
		return COMPLETEDBAGCNTS;
	}
	public void setCOMPLETEDBAGCNTS(String cOMPLETEDBAGCNTS) {
		COMPLETEDBAGCNTS = cOMPLETEDBAGCNTS;
	}
	public String getTOTALDURATION() {
		return TOTALDURATION;
	}
	public void setTOTALDURATION(String tOTALDURATION) {
		TOTALDURATION = tOTALDURATION;
	}
	public String getTOTALVOLUME() {
		return TOTALVOLUME;
	}
	public void setTOTALVOLUME(String tOTALVOLUME) {
		TOTALVOLUME = tOTALVOLUME;
	}
//	public String getINFUSIONDETAILID() {
//		return INFUSIONDETAILID;
//	}
//	public void setINFUSIONDETAILID(String iNFUSIONDETAILID) {
//		INFUSIONDETAILID = iNFUSIONDETAILID;
//	}
	public String getREMAINDERRATE() {
		return REMAINDERRATE;
	}
	public void setREMAINDERRATE(String rEMAINDERRATE) {
		REMAINDERRATE = rEMAINDERRATE;
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
	public String getTUBENAME() {
		return TUBENAME;
	}
	public void setTUBENAME(String tUBENAME) {
		TUBENAME = tUBENAME;
	}
	public String getINFUSIONSTATUS() {
		return INFUSIONSTATUS;
	}
	public void setINFUSIONSTATUS(String iNFUSIONSTATUS) {
		INFUSIONSTATUS = iNFUSIONSTATUS;
	}
	public String getINFUSIONSTATUSNAME() {
		return INFUSIONSTATUSNAME;
	}
	public void setINFUSIONSTATUSNAME(String iNFUSIONSTATUSNAME) {
		INFUSIONSTATUSNAME = iNFUSIONSTATUSNAME;
	}
	public String getREMAINDERVOLUME() {
		return REMAINDERVOLUME;
	}
	public void setREMAINDERVOLUME(String rEMAINDERVOLUME) {
		REMAINDERVOLUME = rEMAINDERVOLUME;
	}
	public String getREMAINDERTIME() {
		return REMAINDERTIME;
	}
	public void setREMAINDERTIME(String rEMAINDERTIME) {
		REMAINDERTIME = rEMAINDERTIME;
	}
	
	public SeatsInfo(String sEATNO, String sEATSTATUS, String iNFUSIONID,
			String pATIENTID, String cARDNO, String pATIENTNAME, String gENDER,
			String aGE, String bAGCNTS, String cOMPLETEDBAGCNTS,
			String tOTALDURATION, String tOTALVOLUME, String rEMAINDERRATE,
			String dRIPCNTSPERMINUTE, String tUBEID, String dRIPCNTSPERML,
			String tUBENAME, String iNFUSIONSTATUS, String iNFUSIONSTATUSNAME,
			String rEMAINDERVOLUME, String rEMAINDERTIME, int iNFUSIONGROUPNO) {
		super();
		SEATNO = sEATNO;
		SEATSTATUS = sEATSTATUS;
		INFUSIONID = iNFUSIONID;
		PATIENTID = pATIENTID;
		CARDNO = cARDNO;
		PATIENTNAME = pATIENTNAME;
		GENDER = gENDER;
		AGE = aGE;
		BAGCNTS = bAGCNTS;
		COMPLETEDBAGCNTS = cOMPLETEDBAGCNTS;
		TOTALDURATION = tOTALDURATION;
		TOTALVOLUME = tOTALVOLUME;
		REMAINDERRATE = rEMAINDERRATE;
		DRIPCNTSPERMINUTE = dRIPCNTSPERMINUTE;
		TUBEID = tUBEID;
		DRIPCNTSPERML = dRIPCNTSPERML;
		TUBENAME = tUBENAME;
		INFUSIONSTATUS = iNFUSIONSTATUS;
		INFUSIONSTATUSNAME = iNFUSIONSTATUSNAME;
		REMAINDERVOLUME = rEMAINDERVOLUME;
		REMAINDERTIME = rEMAINDERTIME;
		INFUSIONGROUPNO = iNFUSIONGROUPNO;
	}
	public SeatsInfo(){
		super();
	}
	@Override
	public String toString() {
		return "SeatsInfo [SEATNO=" + SEATNO + ", SEATSTATUS=" + SEATSTATUS
				+ ", INFUSIONID=" + INFUSIONID + ", PATIENTID=" + PATIENTID
				+ ", CARDNO=" + CARDNO + ", PATIENTNAME=" + PATIENTNAME
				+ ", GENDER=" + GENDER + ", AGE=" + AGE + ", BAGCNTS="
				+ BAGCNTS + ", COMPLETEDBAGCNTS=" + COMPLETEDBAGCNTS
				+ ", TOTALDURATION=" + TOTALDURATION + ", TOTALVOLUME="
				+ TOTALVOLUME + ", REMAINDERRATE=" + REMAINDERRATE
				+ ", DRIPCNTSPERMINUTE=" + DRIPCNTSPERMINUTE + ", TUBEID="
				+ TUBEID + ", DRIPCNTSPERML=" + DRIPCNTSPERML + ", TUBENAME="
				+ TUBENAME + ", INFUSIONSTATUS=" + INFUSIONSTATUS
				+ ", INFUSIONSTATUSNAME=" + INFUSIONSTATUSNAME
				+ ", REMAINDERVOLUME=" + REMAINDERVOLUME + ", REMAINDERTIME="
				+ REMAINDERTIME + ", INFUSIONGROUPNO=" + INFUSIONGROUPNO + "]";
	}
	
	
	public SeatsInfo(String sEATNO, String pATIENTID, String cARDNO,
			String pATIENTNAME, String aGE) {
		super();
		SEATNO = sEATNO;
		PATIENTID = pATIENTID;
		CARDNO = cARDNO;
		PATIENTNAME = pATIENTNAME;
		AGE = aGE;
	}
	@Override
	public boolean equals(Object o) {
		if(TextUtils.equals(this.SEATNO,((SeatsInfo)o).SEATNO)&&TextUtils.equals(this.PATIENTNAME,((SeatsInfo)o).PATIENTNAME)&&TextUtils.equals(this.PATIENTID,((SeatsInfo)o).PATIENTID)
				&&TextUtils.equals(this.CARDNO,((SeatsInfo)o).CARDNO)&&TextUtils.equals(this.AGE, ((SeatsInfo)o).AGE)){
			return true;
		}
		
		return false;
	}
	
}
