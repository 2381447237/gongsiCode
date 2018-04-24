package com.fc.reportform.beans;

import java.io.Serializable;

public class ReportFormInfo implements Serializable{
	private int id;
	private String title;
	private String type;
	private String mark;
	private String reportTime;
	private int createStaff;
	private String createdate;
	private int updateStaff;
	private String updateDate;
	private int recordCount;
	private String type_name;
	private String create_staff_name;
	private String typename;
	
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getReportTime() {
		return reportTime;
	}
	public void setReportTime(String reportTime) {
		this.reportTime = reportTime;
	}
	public int getCreateStaff() {
		return createStaff;
	}
	public void setCreateStaff(int createStaff) {
		this.createStaff = createStaff;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public int getUpdateStaff() {
		return updateStaff;
	}
	public void setUpdateStaff(int updateStaff) {
		this.updateStaff = updateStaff;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	
	public String getCreate_staff_name() {
		return create_staff_name;
	}
	public void setCreate_staff_name(String create_staff_name) {
		this.create_staff_name = create_staff_name;
	}
	
	
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}
	public ReportFormInfo(int id, String title, String type, String mark,
			String reportTime, int createStaff, String createdate,
			int updateStaff, String updateDate, int recordCount,
			String type_name,String create_staff_name) {
		super();
		this.id = id;
		this.title = title;
		this.type = type;
		this.mark = mark;
		this.reportTime = reportTime;
		this.createStaff = createStaff;
		this.createdate = createdate;
		this.updateStaff = updateStaff;
		this.updateDate = updateDate;
		this.recordCount = recordCount;
		this.type_name = type_name;
		this.create_staff_name=create_staff_name;
	}
	
	public ReportFormInfo() {
		super();
	}
	
	@Override
	public String toString() {
		return "ReportFormInfo [id=" + id + ", title=" + title + ", type="
				+ type + ", mark=" + mark + ", reportTime=" + reportTime
				+ ", createStaff=" + createStaff + ", createdate=" + createdate
				+ ", updateStaff=" + updateStaff + ", updateDate=" + updateDate
				+ ", recordCount=" + recordCount + ", type_name=" + type_name
				+ ",typename=+"+typename+" create_staff_name= +"+create_staff_name+"]";
	}
	
}
