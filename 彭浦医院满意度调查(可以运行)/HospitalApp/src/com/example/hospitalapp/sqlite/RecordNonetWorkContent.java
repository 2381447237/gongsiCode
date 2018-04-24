package com.example.hospitalapp.sqlite;

import java.io.Serializable;
import java.util.List;

import com.example.hospitalapp.nonetwork.entity.AnswerNonetWorkContent;
import com.google.gson.annotations.Expose;

public class RecordNonetWorkContent implements Serializable{

	@Expose
	public int Id; //主键自增               
	public int ID;               
	@Expose
	public String NAME;//显示                             对应的是Name
	@Expose
	public String PHONE;//显示                            对应的是Phone
	public String  CREATE_TIME;
	@Expose
	public int CREATE_USERID;//对应的是Create_UserId
	@Expose
	public int MASTERID;           // 对应的是MasterId
	public int RecordCount;
	public float ScoreSum;//显示
	public String YGXM;//显示
	@Expose
	public String CreateTime;//显示           对应的是Create_Time
	
	@Expose
	public List<AnswerNonetWorkContent> MyAnswerList;
	
	public List<AnswerNonetWorkContent> getMyAnswerList() {
		return MyAnswerList;
	}
	public void setMyAnswerList(List<AnswerNonetWorkContent> myAnswerList) {
		MyAnswerList = myAnswerList;
	}
	public RecordNonetWorkContent() {
		super();
	}
	public RecordNonetWorkContent(int id, int cREATE_USERID, String nAME, String pHONE,
			int mASTERID, String createTime) {
		super();
		Id = id;
		CREATE_USERID= cREATE_USERID;
		NAME = nAME;
		PHONE = pHONE;
		MASTERID = mASTERID;
		CreateTime = createTime;
	}
	
	public RecordNonetWorkContent(int id, int cREATE_USERID, String nAME, String pHONE,
			int mASTERID,String createTime,
			List<AnswerNonetWorkContent> myAnswerList) {
		super();
		Id = id;
		CREATE_USERID = cREATE_USERID;
		NAME = nAME;
		PHONE = pHONE;
		MASTERID = mASTERID;
		CreateTime = createTime;
		MyAnswerList = myAnswerList;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
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
	public String getPHONE() {
		return PHONE;
	}
	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}
	public int getMASTERID() {
		return MASTERID;
	}
	public void setMASTERID(int mASTERID) {
		MASTERID = mASTERID;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public int getCREATE_USERID() {
		return CREATE_USERID;
	}
	public void setCREATE_USERID(int cREATE_USERID) {
		CREATE_USERID = cREATE_USERID;
	}
	
	//数据库的字段
//	public int Id;
//	public String Name;
//	public String Phone;
//	public String Create_Time;
//	public int Create_UserId;
//	public int MasterId;
	
}


// http://172.27.35.2:89/json/Get_Investigators_Info.aspx?MasterId=3;
//[{"ID":2,"NAME":"q","PHONE":"q","CREATE_TIME":"2016-08-29T18:41:34.13","CREATE_USERID":1,"MASTERID":3,"RecordCount":0,
//"ScoreSum":64.0,"YGXM":"陈服"}]