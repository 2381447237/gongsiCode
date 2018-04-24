package com.fc.meetingpost.beans;

import java.io.Serializable;

public class MeetingInfo implements Serializable {

	private int id;
	private String title;
	private String doc;
	private String meetingTime;
	private String meetingAdd;
	private int createStaff;
	private String createDate;
	private int recordCount;
	private String createStaffName;
	private String meetingStaff;

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

	public String getMeetingTime() {
		return meetingTime;
	}

	public void setMeetingTime(String meetingTime) {
		this.meetingTime = meetingTime;
	}

	public String getMeetingAdd() {
		return meetingAdd;
	}

	public void setMeetingAdd(String meetingAdd) {
		this.meetingAdd = meetingAdd;
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

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public String getCreateStaffName() {
		return createStaffName;
	}

	public void setCreateStaffName(String createStaffName) {
		this.createStaffName = createStaffName;
	}

	public String getMeetingStaff() {
		return meetingStaff;
	}

	public void setMeetingStaff(String meetingStaff) {
		this.meetingStaff = meetingStaff;
	}

	public MeetingInfo(int id, String title, String doc, String meetingTime,
			String meetingAdd, int createStaff, String createDate,
			int recordCount, String createStaffName, String meetingStaff) {
		super();
		this.id = id;
		this.title = title;
		this.doc = doc;
		this.meetingTime = meetingTime;
		this.meetingAdd = meetingAdd;
		this.createStaff = createStaff;
		this.createDate = createDate;
		this.recordCount = recordCount;
		this.createStaffName = createStaffName;
		this.meetingStaff = meetingStaff;
	}

	public MeetingInfo() {
		super();
	}

	@Override
	public String toString() {
		return "MeetingInfo [id=" + id + ", title=" + title + ", doc=" + doc
				+ ", meetingTime=" + meetingTime + ", meetingAdd=" + meetingAdd
				+ ", createStaff=" + createStaff + ", createDate=" + createDate
				+ ", recordCount=" + recordCount + ", createStaffName="
				+ createStaffName + ", meetingStaff=" + meetingStaff + "]";
	}

}
