package com.fc.first.beans;

import java.io.Serializable;

public class GetMsgBoardMaster implements Serializable{
	private String Id;
	private String Title;
	private String Create_time;
	private String Create_staff;
	private String Update_time;
	private String Update_staff;
	private String Staff;
	
	public GetMsgBoardMaster() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GetMsgBoardMaster(String id, String title, String create_time,
			String create_staff, String update_time, String update_staff,
			String staff) {
		super();
		Id = id;
		Title = title;
		Create_time = create_time;
		Create_staff = create_staff;
		Update_time = update_time;
		Update_staff = update_staff;
		Staff = staff;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getCreate_time() {
		return Create_time;
	}

	public void setCreate_time(String create_time) {
		Create_time = create_time;
	}

	public String getCreate_staff() {
		return Create_staff;
	}

	public void setCreate_staff(String create_staff) {
		Create_staff = create_staff;
	}

	public String getUpdate_time() {
		return Update_time;
	}

	public void setUpdate_time(String update_time) {
		Update_time = update_time;
	}

	public String getUpdate_staff() {
		return Update_staff;
	}

	public void setUpdate_staff(String update_staff) {
		Update_staff = update_staff;
	}

	public String getStaff() {
		return Staff;
	}

	public void setStaff(String staff) {
		Staff = staff;
	}

	@Override
	public String toString() {
		return "GetMsgBoardMaster [Id=" + Id + ", Title=" + Title
				+ ", Create_time=" + Create_time + ", Create_staff="
				+ Create_staff + ", Update_time=" + Update_time
				+ ", Update_staff=" + Update_staff + ", Staff=" + Staff + "]";
	}
		
}
