package com.example.hospitalapp.entity;

public class RecordAllContent {

	public int ID;
	public String NAME;//��ʾ
	public String PHONE;//��ʾ
	public String  CREATE_TIME;
	public int CREATE_USERID;
	public int MASTERID;
	public int RecordCount;
	public float ScoreSum;//��ʾ
	public String YGXM;//��ʾ
	public String CreateTime;//��ʾ
	/*public RecordContent(String nAME, String pHONE, float scoreSum,
			String yGXM, String createTime) {
		super();
		NAME = nAME;
		PHONE = pHONE;
		ScoreSum = scoreSum;
		YGXM = yGXM;
		CreateTime = createTime;
	}*/
	
	
}


// http://172.27.35.2:89/json/Get_Investigators_Info.aspx?MasterId=3;
//[{"ID":10,"NAME":"�ֳ�","PHONE":"5","CREATE_TIME":"2016-11-18T16:20:00","CREATE_USERID":1,"MASTERID":3,"RecordCount":0,"YGXM":"���S","CreateTime":"2016-11-18"}
//,{"ID":11,"NAME":"����","PHONE":"4","CREATE_TIME":"2016-11-18T16:21:00","CREATE_USERID":1,"MASTERID":3,"RecordCount":0,"YGXM":"���S","CreateTime":"2016-11-18"}]