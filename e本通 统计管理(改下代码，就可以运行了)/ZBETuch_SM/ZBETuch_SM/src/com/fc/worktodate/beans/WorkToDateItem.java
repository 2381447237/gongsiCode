package com.fc.worktodate.beans;

import java.io.Serializable;

public class WorkToDateItem implements Serializable{
	private int id;
	private String title;
	private String doc;
	private int createStaff;
	private String createDate;
	private String pic;
	private int recordCount;

	public WorkToDateItem() {
	}

	public WorkToDateItem(int id, String title, String doc, int createStaff,
			String createDate, String pic, int recordCount) {
		this.id = id;
		this.title = title;
		this.doc = doc;
		this.createStaff = createStaff;
		this.createDate = createDate;
		this.pic = pic;
		this.recordCount = recordCount;
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

	public String getDoc() {
		return doc;
	}

	public void setDoc(String doc) {
		this.doc = doc;
	}

	public int getCreateStaff() {
		return createStaff;
	}

	public void setCreateStaff(int createStaff) {
		this.createStaff = createStaff;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	@Override
	public String toString() {
		return "WorkToDateItem [id=" + id + ", title=" + title + ", doc=" + doc
				+ ", createStaff=" + createStaff + ", createDate=" + createDate
				+ ", pic=" + pic + ", recordCount=" + recordCount + "]";
	}

}
