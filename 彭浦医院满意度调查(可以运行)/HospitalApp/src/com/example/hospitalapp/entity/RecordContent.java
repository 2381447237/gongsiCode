package com.example.hospitalapp.entity;

public class RecordContent {

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
//[{"ID":2,"NAME":"q","PHONE":"q","CREATE_TIME":"2016-08-29T18:41:34.13","CREATE_USERID":1,"MASTERID":3,"RecordCount":0,
//"ScoreSum":64.0,"YGXM":"�·�"}]