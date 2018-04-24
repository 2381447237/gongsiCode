package com.fc.first.beans;

import java.io.Serializable;

public class PendingWorkInformation implements Serializable{
	private String Id;
	private String Start_Time;
	private String End_Time;
	private String Title;
	private String Doc;
	private String Work_Staff;
	private String Create_Staff;
	private String Create_Time;
	private String Type;
	private String Update_time;
	private String Max;
	private String staff_Name;
	
	public PendingWorkInformation() {
		super();
	}

	

	public PendingWorkInformation(String id, String start_Time,
			String end_Time, String title, String doc, String work_Staff,
			String create_Staff, String create_Time, String type,
			String update_time, String max, String staff_Name) {
		super();
		Id = id;
		Start_Time = start_Time;
		End_Time = end_Time;
		Title = title;
		Doc = doc;
		Work_Staff = work_Staff;
		Create_Staff = create_Staff;
		Create_Time = create_Time;
		Type = type;
		Update_time = update_time;
		Max = max;
		this.staff_Name = staff_Name;
	}



	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getStart_Time() {
		return Start_Time;
	}

	public void setStart_Time(String start_Time) {
		Start_Time = start_Time;
	}

	public String getEnd_Time() {
		return End_Time;
	}

	public void setEnd_Time(String end_Time) {
		End_Time = end_Time;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDoc() {
		return Doc;
	}

	public void setDoc(String doc) {
		Doc = doc;
	}

	public String getWork_Staff() {
		return Work_Staff;
	}

	public void setWork_Staff(String work_Staff) {
		Work_Staff = work_Staff;
	}

	public String getCreate_Staff() {
		return Create_Staff;
	}

	public void setCreate_Staff(String create_Staff) {
		Create_Staff = create_Staff;
	}

	public String getCreate_Time() {
		return Create_Time;
	}

	public void setCreate_Time(String create_Time) {
		Create_Time = create_Time;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getUpdate_time() {
		return Update_time;
	}

	public void setUpdate_time(String update_time) {
		Update_time = update_time;
	}
	
	public String getMax() {
		return Max;
	}
	
	public void setMax(String max) {
		Max = max;
	}


	public String getStaff_Name() {
		return staff_Name;
	}

	public void setStaff_Name(String staff_Name) {
		this.staff_Name = staff_Name;
	}

	@Override
	public String toString() {
		return "PendingWorkInformation [Id=" + Id + ", Start_Time="
				+ Start_Time + ", End_Time=" + End_Time + ", Title=" + Title
				+ ", Doc=" + Doc + ", Work_Staff=" + Work_Staff
				+ ", Create_Staff=" + Create_Staff + ", Create_Time="
				+ Create_Time + ", Type=" + Type + ", Update_time="
				+ Update_time + ", Max=" + Max + ", staff_Name=" + staff_Name
				+ "]";
	}
	

		
}
