package com.example.hospital;

import java.io.Serializable;

public class AdminInfo implements Serializable{
	private String id;
	private int StaffId;
	private String StaffNo;
	private String Password;
	private String StaffName;
	private String StaffPhone;
	private int RoleId;
	private boolean IsEnabled;
	private String CreateTime;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getStaffId() {
		return StaffId;
	}

	public void setStaffId(int staffId) {
		StaffId = staffId;
	}

	public String getStaffNo() {
		return StaffNo;
	}

	public void setStaffNo(String staffNo) {
		StaffNo = staffNo;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getStaffName() {
		return StaffName;
	}

	public void setStaffName(String staffName) {
		StaffName = staffName;
	}

	public String getStaffPhone() {
		return StaffPhone;
	}

	public void setStaffPhone(String staffPhone) {
		StaffPhone = staffPhone;
	}

	public int getRoleId() {
		return RoleId;
	}

	public void setRoleId(int roleId) {
		RoleId = roleId;
	}

	public boolean isIsEnabled() {
		return IsEnabled;
	}

	public void setIsEnabled(boolean isEnabled) {
		IsEnabled = isEnabled;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public AdminInfo(String id, int staffId, String staffNo, String password,
			String staffName, String staffPhone, int roleId, boolean isEnabled,
			String createTime) {
		super();
		this.id = id;
		this.StaffId = staffId;
		this.StaffNo = staffNo;
		this.Password = password;
		this.StaffName = staffName;
		this.StaffPhone = staffPhone;
		this.RoleId = roleId;
		this.IsEnabled = isEnabled;
		this.CreateTime = createTime;
	}
	public AdminInfo(){
		super();
	}
	
}
