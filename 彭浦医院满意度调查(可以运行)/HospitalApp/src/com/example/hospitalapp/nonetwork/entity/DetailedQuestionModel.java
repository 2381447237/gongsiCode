package com.example.hospitalapp.nonetwork.entity;

public class DetailedQuestionModel {
	private int ID;
	private String TITLE;
	private int PARENTID;
	private int MASTERID;
	private float NUMBER;
	private boolean ISANSWER;
	private String TITLE_END;
	private boolean ISINPUT;
	private String INPUTTYPE;
	private String CODE;
	private String JUMP_CODE;
	private String REMOVE_CODE;
	private int SCORE;
	private int RecordCount;
	private boolean IsCreate;
	private boolean IsUpdate;
	private boolean IsDelete;
	private int Level;
	private int InvestigatorId;
	private int Score;
	private String TitleInfo;
	private String IsCheck;
	
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

	public int getPARENTID() {
		return PARENTID;
	}

	public void setPARENTID(int pARENTID) {
		PARENTID = pARENTID;
	}

	public int getMASTERID() {
		return MASTERID;
	}

	public void setMASTERID(int mASTERID) {
		MASTERID = mASTERID;
	}

	public float getNUMBER() {
		return NUMBER;
	}

	public void setNUMBER(int nUMBER) {
		NUMBER = nUMBER;
	}

	public boolean isISANSWER() {
		return ISANSWER;
	}

	public void setISANSWER(boolean iSANSWER) {
		ISANSWER = iSANSWER;
	}

	public String getTITLE_END() {
		return TITLE_END;
	}

	public void setTITLE_END(String tITLE_END) {
		TITLE_END = tITLE_END;
	}

	public boolean isISINPUT() {
		return ISINPUT;
	}

	public void setISINPUT(boolean iSINPUT) {
		ISINPUT = iSINPUT;
	}

	public String getINPUTTYPE() {
		return INPUTTYPE;
	}

	public void setINPUTTYPE(String iNPUTTYPE) {
		INPUTTYPE = iNPUTTYPE;
	}

	public String getCODE() {
		return CODE;
	}

	public void setCODE(String cODE) {
		CODE = cODE;
	}

	public String getJUMP_CODE() {
		return JUMP_CODE;
	}

	public void setJUMP_CODE(String jUMP_CODE) {
		JUMP_CODE = jUMP_CODE;
	}

	public String getREMOVE_CODE() {
		return REMOVE_CODE;
	}

	public void setREMOVE_CODE(String rEMOVE_CODE) {
		REMOVE_CODE = rEMOVE_CODE;
	}

	public int getSCORE() {
		return SCORE;
	}

	public void setSCORE(int sCORE) {
		SCORE = sCORE;
	}

	public int getRecordCount() {
		return RecordCount;
	}

	public void setRecordCount(int recordCount) {
		RecordCount = recordCount;
	}

	public boolean isIsCreate() {
		return IsCreate;
	}

	public void setIsCreate(boolean isCreate) {
		IsCreate = isCreate;
	}

	public boolean isIsUpdate() {
		return IsUpdate;
	}

	public void setIsUpdate(boolean isUpdate) {
		IsUpdate = isUpdate;
	}

	public boolean isIsDelete() {
		return IsDelete;
	}

	public void setIsDelete(boolean isDelete) {
		IsDelete = isDelete;
	}

	public int getLevel() {
		return Level;
	}

	public void setLevel(int level) {
		Level = level;
	}

	public int getInvestigatorId() {
		return InvestigatorId;
	}

	public void setInvestigatorId(int investigatorId) {
		InvestigatorId = investigatorId;
	}

	public int getScore() {
		return Score;
	}

	public void setScore(int score) {
		Score = score;
	}

	public String getTitleInfo() {
		return TitleInfo;
	}

	public void setTitleInfo(String titleInfo) {
		TitleInfo = titleInfo;
	}

	public String getIsCheck() {
		return IsCheck;
	}

	public void setIsCheck(String isCheck) {
		IsCheck = isCheck;
	}

}
