package com.fc.gradeate.beans;

import java.io.Serializable;

public class GradeateInfo implements Serializable {

	private int id;
	private String name;
	private String sfz; //身份证
	private String sex; //性别
	private String nations; //民族
	private String politicalStatus; //政治面貌
	private String edu;  //学历
	private String school; //学校
	private String specialty; //专业
	private int streetId; //街道ID
	private int committeeId; //居委ID
	private String address; 
	private String nowAddress; //现居住地址
	private String contactNumber; //家庭电话
	private String mark; 
	private String survey; //数据采集形式
	private String creatDate; //创建时间
	private int creatStaff;
	private String updateDate; //最新修改时间
	private int updateStaff;
	private int recordCount;

	public GradeateInfo() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSfz() {
		return sfz;
	}

	public void setSfz(String sfz) {
		this.sfz = sfz;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNations() {
		return nations;
	}

	public void setNations(String nations) {
		this.nations = nations;
	}

	public String getPoliticalStatus() {
		return politicalStatus;
	}

	public void setPoliticalStatus(String politicalStatus) {
		this.politicalStatus = politicalStatus;
	}

	public String getEdu() {
		return edu;
	}

	public void setEdu(String edu) {
		this.edu = edu;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public int getStreetId() {
		return streetId;
	}

	public void setStreetId(int streetId) {
		this.streetId = streetId;
	}

	public int getCommitteeId() {
		return committeeId;
	}

	public void setCommitteeId(int committeeId) {
		this.committeeId = committeeId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNowAddress() {
		return nowAddress;
	}

	public void setNowAddress(String nowAddress) {
		this.nowAddress = nowAddress;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getSurvey() {
		return survey;
	}

	public void setSurvey(String survey) {
		this.survey = survey;
	}

	public String getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(String creatDate) {
		this.creatDate = creatDate;
	}

	public int getCreatStaff() {
		return creatStaff;
	}

	public void setCreatStaff(int creatStaff) {
		this.creatStaff = creatStaff;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public int getUpdateStaff() {
		return updateStaff;
	}

	public void setUpdateStaff(int updateStaff) {
		this.updateStaff = updateStaff;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	@Override
	public String toString() {
		return "GradeateInfo [id=" + id + ", name=" + name + ", sfz=" + sfz
				+ ", sex=" + sex + ", nations=" + nations
				+ ", politicalStatus=" + politicalStatus + ", edu=" + edu
				+ ", school=" + school + ", specialty=" + specialty
				+ ", streetId=" + streetId + ", committeeId=" + committeeId
				+ ", address=" + address + ", nowAddress=" + nowAddress
				+ ", contactNumber=" + contactNumber + ", mark=" + mark
				+ ", survey=" + survey + ", creatDate=" + creatDate
				+ ", creatStaff=" + creatStaff + ", updateDate=" + updateDate
				+ ", updateStaff=" + updateStaff + ", recordCount="
				+ recordCount + "]";
	}

}
