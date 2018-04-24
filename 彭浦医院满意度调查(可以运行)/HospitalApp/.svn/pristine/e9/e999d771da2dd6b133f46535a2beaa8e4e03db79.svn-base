package com.example.hospitalapp.nonetwork.entity;

import java.io.Serializable;

import com.google.gson.annotations.Expose;

public class AnswerNonetWorkContent implements Serializable{

	@Expose
	public int Pid;// 表需要*******,用户ID
	@Expose
	public String AnswerStr;// 表需要*******,答案ID
	@Expose
	public int ID;// 要这个 表需要*********
	
	public int MASTERID;

	public String TITLE;

	public boolean ISANSWER;// "ISANSWER":true就是题目，否则是选项
	
	public int PARENTID;
	
	public boolean IsCheck;// "IsCheck":true就是多选，否则是单选
	
	public boolean ISINPUT;// "ISINPUT":true就是填空，否则就不是
	
	public String TITLE_END;
	
	public AnswerNonetWorkContent(int pid, int iD, String answerStr) {
		super();
		Pid = pid;
		ID = iD;
		AnswerStr = answerStr;
	}

	@Override
	public String toString() {
		return "AnswerNonetWorkContent [Pid=" + Pid + ", AnswerStr="
				+ AnswerStr + ", ID=" + ID + "]";
	}

	public int getPid() {
		return Pid;
	}

	public void setPid(int pid) {
		Pid = pid;
	}

	public String getAnswerStr() {
		return AnswerStr;
	}

	public void setAnswerStr(String answerStr) {
		AnswerStr = answerStr;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
	public String getTITLE() {
		return TITLE;
	}

	public void setTITLE(String tITLE) {
		TITLE = tITLE;
	}

}
// [{"ID":39,"TITLE":"1、窗口、工作人员挂牌服务。（多选）","PARENTID":0,"MASTERID":3,"NUMBER":1.00,"ISANSWER":true,"TITLE_END":"","ISINPUT":false,
// "INPUTTYPE":"","CODE":"1","JUMP_CODE":"","REMOVE_CODE":null,"SCORE":0.0,"RecordCount":0,"IsCreate":false,"IsUpdate":false,"IsDelete":false,
// "Level":0,"InvestigatorId":0,"Score":0.0,"TitleInfo":"1、窗口、工作人员挂牌服务。（多选）","IsCheck":true},
// {"ID":1438,"TITLE":"满意","PARENTID":39,"MASTERID":3,"NUMBER":1.10,"ISANSWER":false,"TITLE_END":null,"ISINPUT":false,
// "INPUTTYPE":null,"CODE":null,"JUMP_CODE":null,"REMOVE_CODE":null,"SCORE":0.0,"RecordCount":0,"IsCreate":false,"IsUpdate":false,"IsDelete":false,
// "Level":1,"InvestigatorId":0,"Score":0.0,"TitleInfo":"满意","IsCheck":false},
// {"ID":1439,"TITLE":"较满意","PARENTID":39,"MASTERID":3,"NUMBER":1.20,"ISANSWER":false,"TITLE_END":null,
// "ISINPUT":false,"INPUTTYPE":null,"CODE":null,"JUMP_CODE":null,"REMOVE_CODE":null,"SCORE":0.0,"RecordCount":0,"IsCreate":false,
// "IsUpdate":false,"IsDelete":false,"Level":1,"InvestigatorId":0,"Score":0.0,"TitleInfo":"较满意","IsCheck":false},{"ID":1440,"TITLE":"一般",
// "PARENTID":39,"MASTERID":3,"NUMBER":1.30,"ISANSWER":false,"TITLE_END":null,"ISINPUT":false,"INPUTTYPE":null,"CODE":null,"JUMP_CODE":null,
// "REMOVE_CODE":null,"SCORE":0.0,"RecordCount":0,"IsCreate":false,"IsUpdate":false,"IsDelete":false,"Level":1,"InvestigatorId":0,"Score":0.0,
// "TitleInfo":"一般","IsCheck":false},{"ID":1441,"TITLE":"不满意","PARENTID":39,"MASTERID":3,"NUMBER":1.40,"ISANSWER":false,"TITLE_END":null,
// "ISINPUT":false,"INPUTTYPE":null,"CODE":null,"JUMP_CODE":null,"REMOVE_CODE":null,"SCORE":0.0,"RecordCount":0,"IsCreate":false,
// "IsUpdate":false,"IsDelete":false,"Level":1,"InvestigatorId":0,"Score":0.0,"TitleInfo":"不满意","IsCheck":false},{"ID":1442,"TITLE":"很不满意",
// "PARENTID":39,"MASTERID":3,"NUMBER":1.50,"ISANSWER":false,"TITLE_END":null,"ISINPUT":false,"INPUTTYPE":null,"CODE":null,"JUMP_CODE":null,
// "REMOVE_CODE":null,"SCORE":0.0,"RecordCount":0,"IsCreate":false,"IsUpdate":false,"IsDelete":false,"Level":1,"InvestigatorId":0,"Score":0.0,
// "TitleInfo":"很不满意","IsCheck":false},{"ID":1443,"TITLE":"不了解","PARENTID":39,"MASTERID":3,"NUMBER":1.60,"ISANSWER":false,"TITLE_END":null,
// "ISINPUT":false,"INPUTTYPE":null,"CODE":null,"JUMP_CODE":null,"REMOVE_CODE":null,"SCORE":0.0,"RecordCount":0,"IsCreate":false,
// "IsUpdate":false,"IsDelete":false,"Level":1,"InvestigatorId":0,"Score":0.0,"TitleInfo":"不了解","IsCheck":false},