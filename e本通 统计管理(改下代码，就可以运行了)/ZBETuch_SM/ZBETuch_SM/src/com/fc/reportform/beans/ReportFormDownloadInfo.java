package com.fc.reportform.beans;

public class ReportFormDownloadInfo{
	private int id;
	private String filePath;
	private String fileName;
	private String masterId;
	private int recordCount;
	private int type;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getMasterId() {
		return masterId;
	}
	public void setMasterId(String masterId) {
		this.masterId = masterId;
	}
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public ReportFormDownloadInfo(int id, String filePath, String fileName,
			String masterId, int recordCount, int type) {
		super();
		this.id = id;
		this.filePath = filePath;
		this.fileName = fileName;
		this.masterId = masterId;
		this.recordCount = recordCount;
		this.type = type;
	}
	
	public ReportFormDownloadInfo() {
		super();
		
	}
	
	@Override
	public String toString() {
		return "MeetingDownloadInfo [id=" + id + ", filePath=" + filePath
				+ ", fileName=" + fileName + ", masterId=" + masterId
				+ ", recordCount=" + recordCount + ", type=" + type + "]";
	}
		
}
