package com.fc.person.beans;

import java.io.Serializable;


public class PersonalInformation implements Serializable {
	private String personCard;
	private String personNum;
	private String personName;
	private String personType;
	private String personHuji;
	private String personDegree;
	private String agelow;
	private String ageup;
	private String isCheck_a;
	private String isCheck_b;
	private String province;
	private String city;
	private String county;
	private String street;
	private String committee;
	private String road;
	private String nong;
	private String number;
	private String room;

	public PersonalInformation() {
		super();
	}

	public PersonalInformation(String personCard, String personNum,
			String personName, String personType, String personHuji,
			String personDegree, String agelow, String ageup, String isCheck_a,
			String isCheck_b, String province, String city, String county,
			String street, String committee, String road, String nong,
			String number, String room) {
		super();
		this.personCard = personCard;
		this.personNum = personNum;
		this.personName = personName;
		this.personType = personType;
		this.personHuji = personHuji;
		this.personDegree = personDegree;
		this.agelow = agelow;
		this.ageup = ageup;
		this.isCheck_a = isCheck_a;
		this.isCheck_b = isCheck_b;
		this.province = province;
		this.city = city;
		this.county = county;
		this.street = street;
		this.committee = committee;
		this.road = road;
		this.nong = nong;
		this.number = number;
		this.room = room;
	}

	public String getPersonCard() {
		return personCard;
	}

	public void setPersonCard(String personCard) {
		this.personCard = personCard;
	}

	public String getPersonNum() {
		return personNum;
	}

	public void setPersonNum(String personNum) {
		this.personNum = personNum;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public String getPersonHuji() {
		return personHuji;
	}

	public void setPersonHuji(String personHuji) {
		this.personHuji = personHuji;
	}

	public String getPersonDegree() {
		return personDegree;
	}

	public void setPersonDegree(String personDegree) {
		this.personDegree = personDegree;
	}

	public String getAgelow() {
		return agelow;
	}

	public void setAgelow(String agelow) {
		this.agelow = agelow;
	}

	public String getAgeup() {
		return ageup;
	}

	public void setAgeup(String ageup) {
		this.ageup = ageup;
	}

	public String getIsCheck_a() {
		return isCheck_a;
	}

	public void setIsCheck_a(String isCheck_a) {
		this.isCheck_a = isCheck_a;
	}

	public String getIsCheck_b() {
		return isCheck_b;
	}

	public void setIsCheck_b(String isCheck_b) {
		this.isCheck_b = isCheck_b;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCommittee() {
		return committee;
	}

	public void setCommittee(String committee) {
		this.committee = committee;
	}

	public String getRoad() {
		return road;
	}

	public void setRoad(String road) {
		this.road = road;
	}

	public String getNong() {
		return nong;
	}

	public void setNong(String nong) {
		this.nong = nong;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	@Override
	public String toString() {
		return "PersonalInformation [personCard=" + personCard + ", personNum="
				+ personNum + ", personName=" + personName + ", personType="
				+ personType + ", personHuji=" + personHuji + ", personDegree="
				+ personDegree + ", agelow=" + agelow + ", ageup=" + ageup
				+ ", isCheck_a=" + isCheck_a + ", isCheck_b=" + isCheck_b
				+ ", province=" + province + ", city=" + city + ", county="
				+ county + ", street=" + street + ", committee=" + committee
				+ ", road=" + road + ", nong=" + nong + ", number=" + number
				+ ", room=" + room + "]";
	}

}
