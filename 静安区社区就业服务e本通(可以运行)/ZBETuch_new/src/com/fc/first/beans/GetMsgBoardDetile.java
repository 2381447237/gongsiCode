package com.fc.first.beans;

import java.io.Serializable;

public class GetMsgBoardDetile implements Serializable {
	private String Id;
	private String Master_id;
	private String Create_staff;
	private String Update_date;
	private String Doc;
	private String Staff;
	private String RecordCount;

	public GetMsgBoardDetile() {
	}

	public GetMsgBoardDetile(String id, String master_id, String create_staff,
			String update_date, String doc, String staff, String recordCount) {
		Id = id;
		Master_id = master_id;
		Create_staff = create_staff;
		Update_date = update_date;
		Doc = doc;
		Staff = staff;
		RecordCount = recordCount;
	}

	public String getRecordCount() {
		return RecordCount;
	}

	public void setRecordCount(String recordCount) {
		RecordCount = recordCount;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getMaster_id() {
		return Master_id;
	}

	public void setMaster_id(String master_id) {
		Master_id = master_id;
	}

	public String getCreate_staff() {
		return Create_staff;
	}

	public void setCreate_staff(String create_staff) {
		Create_staff = create_staff;
	}

	public String getUpdate_date() {
		return Update_date;
	}

	public void setUpdate_date(String update_date) {
		Update_date = update_date;
	}

	public String getDoc() {
		return Doc;
	}

	public void setDoc(String doc) {
		Doc = doc;
	}

	public String getStaff() {
		return Staff;
	}

	public void setStaff(String staff) {
		Staff = staff;
	}

	@Override
	public String toString() {
		return "GetMsgBoardDetile [Id=" + Id + ", Master_id=" + Master_id
				+ ", Create_staff=" + Create_staff + ", Update_date="
				+ Update_date + ", Doc=" + Doc + ", Staff=" + Staff
				+ ", RecordCount=" + RecordCount + "]";
	}

}
