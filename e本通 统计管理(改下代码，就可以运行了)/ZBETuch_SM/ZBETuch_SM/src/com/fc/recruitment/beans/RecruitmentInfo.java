package com.fc.recruitment.beans;

import java.io.Serializable;

public class RecruitmentInfo implements Serializable{
	
	@Override
	public String toString() {
		return "RecruitmentInfo [Id=" + Id + ", Name=" + Name
				+ ", JobFairData=" + JobFairData + ", Address=" + Address
				+ ", CreateDate=" + CreateDate + ", CreateStaff=" + CreateStaff
				+ ", UpdateDate=" + UpdateDate + ", UpdateStaff=" + UpdateStaff
				+ ", RecordCount=" + RecordCount + ", CompanyNum=" + CompanyNum
				+ ", JobNum=" + JobNum + "]";
	}
	
	int Id;
	String Name;
	String JobFairData;
	String Address;
	String CreateDate;
	int CreateStaff;
	String UpdateDate;	
	int UpdateStaff;
	int RecordCount;
	int CompanyNum;
	int JobNum;
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getJobFairData() {
		return JobFairData;
	}
	public void setJobFairData(String jobFairData) {
		JobFairData = jobFairData;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(String createDate) {
		CreateDate = createDate;
	}
	public int getCreateStaff() {
		return CreateStaff;
	}
	public void setCreateStaff(int createStaff) {
		CreateStaff = createStaff;
	}
	public String getUpdateDate() {
		return UpdateDate;
	}
	public void setUpdateDate(String updateDate) {
		UpdateDate = updateDate;
	}
	public int getUpdateStaff() {
		return UpdateStaff;
	}
	public void setUpdateStaff(int updateStaff) {
		UpdateStaff = updateStaff;
	}
	public int getRecordCount() {
		return RecordCount;
	}
	public void setRecordCount(int recordCount) {
		RecordCount = recordCount;
	}
	public int getCompanyNum() {
		return CompanyNum;
	}
	public void setCompanyNum(int companyNum) {
		CompanyNum = companyNum;
	}
	public int getJobNum() {
		return JobNum;
	}
	public void setJobNum(int jobNum) {
		JobNum = jobNum;
	}
	
	public RecruitmentInfo(int id, String name, String jobFairData,
			String address, String createDate, int createStaff,
			String updateDate, int updateStaff, int recordCount,
			int companyNum, int jobNum) {
		super();
		Id = id;
		Name = name;
		JobFairData = jobFairData;
		Address = address;
		CreateDate = createDate;
		CreateStaff = createStaff;
		UpdateDate = updateDate;
		UpdateStaff = updateStaff;
		RecordCount = recordCount;
		CompanyNum = companyNum;
		JobNum = jobNum;
	}
	
	public RecruitmentInfo() {
		super();
	}
}