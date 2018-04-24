package com.fc.person.beans;

import java.io.Serializable;

public class PersonInfoList implements Serializable {
	private String personlistName;
	private String personlistBorn;
	private String personlistSex;
	private String personlistCurrentStatus;
	private String personlistEducation;
	private String personlistSFZ;
	private String personlistType;
	private String pesonlistModi;
	private int recordCount;

	public PersonInfoList() {
	}

	public PersonInfoList(String personlistName, String personlistBorn,
			String personlistSex, String personlistCurrentStatus,
			String personlistEducation, String personlistSFZ,
			String personlistType, String pesonlistModi, int recordCount) {
		this.personlistName = personlistName;
		this.personlistBorn = personlistBorn;
		this.personlistSex = personlistSex;
		this.personlistCurrentStatus = personlistCurrentStatus;
		this.personlistEducation = personlistEducation;
		this.personlistSFZ = personlistSFZ;
		this.personlistType = personlistType;
		this.pesonlistModi = pesonlistModi;
		this.recordCount = recordCount;
	}

	public String getPersonlistType() {
		return personlistType;
	}

	public void setPersonlistType(String personlistType) {
		this.personlistType = personlistType;
	}

	public String getPersonlistSFZ() {
		return personlistSFZ;
	}

	public void setPersonlistSFZ(String personlistSFZ) {
		this.personlistSFZ = personlistSFZ;
	}

	public String getPersonlistName() {
		return personlistName;
	}

	public void setPersonlistName(String personlistName) {
		this.personlistName = personlistName;
	}

	public String getPersonlistBorn() {
		return personlistBorn;
	}

	public void setPersonlistBorn(String personlistBorn) {
		this.personlistBorn = personlistBorn;
	}

	public String getPersonlistSex() {
		return personlistSex;
	}

	public void setPersonlistSex(String personlistSex) {
		this.personlistSex = personlistSex;
	}

	public String getPersonlistCurrentStatus() {
		return personlistCurrentStatus;
	}

	public void setPersonlistCurrentStatus(String personlistCurrentStatus) {
		this.personlistCurrentStatus = personlistCurrentStatus;
	}

	public String getPersonlistEducation() {
		return personlistEducation;
	}

	public void setPersonlistEducation(String personlistEducation) {
		this.personlistEducation = personlistEducation;
	}

	public String getPesonlistModi() {
		return pesonlistModi;
	}

	public void setPesonlistModi(String pesonlistModi) {
		this.pesonlistModi = pesonlistModi;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	@Override
	public String toString() {
		return "PersonInfoList [personlistName=" + personlistName
				+ ", personlistBorn=" + personlistBorn + ", personlistSex="
				+ personlistSex + ", personlistCurrentStatus="
				+ personlistCurrentStatus + ", personlistEducation="
				+ personlistEducation + ", personlistSFZ=" + personlistSFZ
				+ ", personlistType=" + personlistType + ", pesonlistModi="
				+ pesonlistModi + ", recordCount=" + recordCount + "]";
	}

}
