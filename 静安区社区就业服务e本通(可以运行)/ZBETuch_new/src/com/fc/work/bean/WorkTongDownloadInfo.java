package com.fc.work.bean;

public class WorkTongDownloadInfo {

	private int ID;
	private String FILE_PATH;
	private String FILE_NAME;
	private int MASTER_ID;
	private int RecordCount;
	private int type;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getFILE_PATH() {
		return FILE_PATH;
	}

	public void setFILE_PATH(String fILE_PATH) {
		FILE_PATH = fILE_PATH;
	}

	public String getFILE_NAME() {
		return FILE_NAME;
	}

	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}

	public int getMASTER_ID() {
		return MASTER_ID;
	}

	public void setMASTER_ID(int mASTER_ID) {
		MASTER_ID = mASTER_ID;
	}

	public int getRecordCount() {
		return RecordCount;
	}

	public void setRecordCount(int recordCount) {
		RecordCount = recordCount;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
