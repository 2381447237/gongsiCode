package com.fc.first.beans;

import java.io.Serializable;

public class GetUsrInformation implements Serializable{
	private String Id;
	private String Name;
	private String Input_Code;
	private String Pwd;
	private String Phone;
	private String Email;
	private String Photo;
	private String Create_Date;
	private String Create_Staff;
	private String Update_Date;
	private String Update_Staff;
	private String Stop;
	private String Enable;
	private String dept;
	
	
	public GetUsrInformation(String id, String name, String input_Code,
			String pwd, String phone, String email, String photo,
			String create_Date, String create_Staff, String update_Date,
			String update_Staff, String stop, String enable, String dept) {
		super();
		Id = id;
		Name = name;
		Input_Code = input_Code;
		Pwd = pwd;
		Phone = phone;
		Email = email;
		Photo = photo;
		Create_Date = create_Date;
		Create_Staff = create_Staff;
		Update_Date = update_Date;
		Update_Staff = update_Staff;
		Stop = stop;
		Enable = enable;
		this.dept = dept;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public GetUsrInformation() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getInput_Code() {
		return Input_Code;
	}

	public void setInput_Code(String input_Code) {
		Input_Code = input_Code;
	}

	public String getPwd() {
		return Pwd;
	}

	public void setPwd(String pwd) {
		Pwd = pwd;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String photo) {
		Photo = photo;
	}

	public String getCreate_Date() {
		return Create_Date;
	}

	public void setCreate_Date(String create_Date) {
		Create_Date = create_Date;
	}

	public String getCreate_Staff() {
		return Create_Staff;
	}

	public void setCreate_Staff(String create_Staff) {
		Create_Staff = create_Staff;
	}

	public String getUpdate_Date() {
		return Update_Date;
	}

	public void setUpdate_Date(String update_Date) {
		Update_Date = update_Date;
	}

	public String getUpdate_Staff() {
		return Update_Staff;
	}

	public void setUpdate_Staff(String update_Staff) {
		Update_Staff = update_Staff;
	}

	public String getStop() {
		return Stop;
	}

	public void setStop(String stop) {
		Stop = stop;
	}

	public String getEnable() {
		return Enable;
	}

	public void setEnable(String enable) {
		Enable = enable;
	}

	@Override
	public String toString() {
		return "GetUsrInformation [Id=" + Id + ", Name=" + Name
				+ ", Input_Code=" + Input_Code + ", Pwd=" + Pwd + ", Phone="
				+ Phone + ", Email=" + Email + ", Photo=" + Photo
				+ ", Create_Date=" + Create_Date + ", Create_Staff="
				+ Create_Staff + ", Update_Date=" + Update_Date
				+ ", Update_Staff=" + Update_Staff + ", Stop=" + Stop
				+ ", Enable=" + Enable + ", dept=" + dept + "]";
	}


	
	
}
