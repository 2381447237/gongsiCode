package com.fc.person.beans;

import java.io.Serializable;
import java.util.Arrays;

public class PersonUpdataImg implements Serializable {
	private String personSfz;
	private byte[] personheadImg;

	public PersonUpdataImg() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PersonUpdataImg(String personSfz, byte[] personheadImg) {
		super();
		this.personSfz = personSfz;
		this.personheadImg = personheadImg;
	}

	public String getPersonSfz() {
		return personSfz;
	}

	public void setPersonSfz(String personSfz) {
		this.personSfz = personSfz;
	}

	public byte[] getPersonheadImg() {
		return personheadImg;
	}

	public void setPersonheadImg(byte[] personheadImg) {
		this.personheadImg = personheadImg;
	}

	@Override
	public String toString() {
		return "PersonUpdataImg [personSfz=" + personSfz + ", personheadImg="
				+ Arrays.toString(personheadImg) + "]";
	}

}
