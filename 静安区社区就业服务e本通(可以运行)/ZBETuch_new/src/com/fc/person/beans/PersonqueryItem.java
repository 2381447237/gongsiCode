package com.fc.person.beans;

public class PersonqueryItem {
	private String personqueryItem_name;
	private String personqueryItem_born;
	private String personqueryItem_sex;
	private String personqueryItem_education;
	// private String personqueryItem_national;
	private String personqueryItem_currentStatus;

	public PersonqueryItem() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PersonqueryItem(String personqueryItem_name,
			String personqueryItem_born, String personqueryItem_sex,
			String personqueryItem_education,
			String personqueryItem_currentStatus) {
		super();
		this.personqueryItem_name = personqueryItem_name;
		this.personqueryItem_born = personqueryItem_born;
		this.personqueryItem_sex = personqueryItem_sex;
		this.personqueryItem_education = personqueryItem_education;
		// this.personqueryItem_national = personqueryItem_national;
		this.personqueryItem_currentStatus = personqueryItem_currentStatus;
	}

	public String getPersonqueryItem_name() {
		return personqueryItem_name;
	}

	public void setPersonqueryItem_name(String personqueryItem_name) {
		this.personqueryItem_name = personqueryItem_name;
	}

	public String getPersonqueryItem_born() {
		return personqueryItem_born;
	}

	public void setPersonqueryItem_born(String personqueryItem_born) {
		this.personqueryItem_born = personqueryItem_born;
	}

	public String getPersonqueryItem_sex() {
		return personqueryItem_sex;
	}

	public void setPersonqueryItem_sex(String personqueryItem_sex) {
		this.personqueryItem_sex = personqueryItem_sex;
	}

	public String getPersonqueryItem_education() {
		return personqueryItem_education;
	}

	public void setPersonqueryItem_education(String personqueryItem_education) {
		this.personqueryItem_education = personqueryItem_education;
	}

	// public String getPersonqueryItem_national() {
	// return personqueryItem_national;
	// }

	public String getPersonqueryItem_currentStatus() {
		return personqueryItem_currentStatus;
	}

	public void setPersonqueryItem_currentStatus(
			String personqueryItem_currentStatus) {
		this.personqueryItem_currentStatus = personqueryItem_currentStatus;
	}

	@Override
	public String toString() {
		return "PersonqueryItem [personqueryItem_name=" + personqueryItem_name
				+ ", personqueryItem_born=" + personqueryItem_born
				+ ", personqueryItem_sex=" + personqueryItem_sex
				+ ", personqueryItem_education=" + personqueryItem_education
				+ ", personqueryItem_national="
				+ ", personqueryItem_currentStatus="
				+ personqueryItem_currentStatus + "]";
	}

}
