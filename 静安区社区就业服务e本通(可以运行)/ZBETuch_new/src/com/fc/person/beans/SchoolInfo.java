package com.fc.person.beans;

import java.io.Serializable;

public class SchoolInfo implements Serializable {
	private int id;
	private String sfz;
	private String school;
	private String education;
	private String specialty;
	private String start_date;
	private String end_date;
	private String create_date;
	private String update_date;
	private String create_staff;
	private String update_staff;
	private String recordcount;
	private String type;

	public SchoolInfo() {
	}

	public SchoolInfo(int id, String sfz, String school, String education,
			String specialty, String start_date, String end_date,
			String create_date, String update_date, String create_staff,
			String update_staff, String recordcount, String type) {
		this.id = id;
		this.sfz = sfz;
		this.school = school;
		this.education = education;
		this.specialty = specialty;
		this.start_date = start_date;
		this.end_date = end_date;
		this.create_date = create_date;
		this.update_date = update_date;
		this.create_staff = create_staff;
		this.update_staff = update_staff;
		this.recordcount = recordcount;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSfz() {
		return sfz;
	}

	public void setSfz(String sfz) {
		this.sfz = sfz;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getSpecialty() {
		return specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public String getCreate_staff() {
		return create_staff;
	}

	public void setCreate_staff(String create_staff) {
		this.create_staff = create_staff;
	}

	public String getUpdate_staff() {
		return update_staff;
	}

	public void setUpdate_staff(String update_staff) {
		this.update_staff = update_staff;
	}

	public String getRecordcount() {
		return recordcount;
	}

	public void setRecordcount(String recordcount) {
		this.recordcount = recordcount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "SchoolInfo [id=" + id + ", sfz=" + sfz + ", school=" + school
				+ ", education=" + education + ", specialty=" + specialty
				+ ", start_date=" + start_date + ", end_date=" + end_date
				+ ", create_date=" + create_date + ", update_date="
				+ update_date + ", create_staff=" + create_staff
				+ ", update_staff=" + update_staff + ", recordcount="
				+ recordcount + ", type=" + type + "]";
	}

}
