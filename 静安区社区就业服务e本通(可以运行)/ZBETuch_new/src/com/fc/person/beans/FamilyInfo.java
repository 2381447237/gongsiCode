package com.fc.person.beans;

import java.util.Arrays;

public class FamilyInfo {
	private int id = -1;
	private String name = "";
	private String sex = "";
	private String sfz = "";
	private String birth_date = "";
	private byte[] imagedata;

	public FamilyInfo() {
	}

	public FamilyInfo(int id, String name, String sex, String sfz,
			String birth_date, byte[] imagedata) {
		this.id = id;
		this.name = name;
		this.sex = sex;
		this.sfz = sfz;
		this.birth_date = birth_date;
		this.imagedata = imagedata;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSfz() {
		return sfz;
	}

	public void setSfz(String sfz) {
		this.sfz = sfz;
	}

	public String getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}

	public byte[] getImagedata() {
		return imagedata;
	}

	public void setImagedata(byte[] imagedata) {
		this.imagedata = imagedata;
	}

	@Override
	public String toString() {
		return "FamilyInfo [id=" + id + ", name=" + name + ", sex=" + sex
				+ ", sfz=" + sfz + ", birth_date=" + birth_date
				+ ", imagedata=" + Arrays.toString(imagedata) + "]";
	}

}
