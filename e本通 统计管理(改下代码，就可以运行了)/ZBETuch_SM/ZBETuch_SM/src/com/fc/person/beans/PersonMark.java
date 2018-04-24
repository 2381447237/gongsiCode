package com.fc.person.beans;

import java.io.Serializable;

public class PersonMark implements Serializable{
	private String personMarkId;
	private String personSFZ;
	private String personMarkName;
	private String personMarkCreatdate;
	private String personMarkSoure;
	public PersonMark() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PersonMark(String personMarkId, String personSFZ,
			String personMarkName, String personMarkCreatdate,
			String personMarkSoure) {
		super();
		this.personMarkId = personMarkId;
		this.personSFZ = personSFZ;
		this.personMarkName = personMarkName;
		this.personMarkCreatdate = personMarkCreatdate;
		this.personMarkSoure = personMarkSoure;
	}
	public String getPersonMarkId() {
		return personMarkId;
	}
	public void setPersonMarkId(String personMarkId) {
		this.personMarkId = personMarkId;
	}
	public String getPersonSFZ() {
		return personSFZ;
	}
	public void setPersonSFZ(String personSFZ) {
		this.personSFZ = personSFZ;
	}
	public String getPersonMarkName() {
		return personMarkName;
	}
	public void setPersonMarkName(String personMarkName) {
		this.personMarkName = personMarkName;
	}
	public String getPersonMarkCreatdate() {
		return personMarkCreatdate;
	}
	public void setPersonMarkCreatdate(String personMarkCreatdate) {
		this.personMarkCreatdate = personMarkCreatdate;
	}
	public String getPersonMarkSoure() {
		return personMarkSoure;
	}
	public void setPersonMarkSoure(String personMarkSoure) {
		this.personMarkSoure = personMarkSoure;
	}
	@Override
	public String toString() {
		return "PersonMark [personMarkId=" + personMarkId + ", personSFZ="
				+ personSFZ + ", personMarkName=" + personMarkName
				+ ", personMarkCreatdate=" + personMarkCreatdate
				+ ", personMarkSoure=" + personMarkSoure + "]";
	}
	
	
}
