package com.fc.download.beans;

public class DownLoadItem {
	private int id;
	private String title;
	private String filePath;

	public DownLoadItem() {

	}

	public DownLoadItem(int id, String title, String filePath) {
		this.id = id;
		this.title = title;
		this.filePath = filePath;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
