package com.fc.person.beans;

import java.io.Serializable;
import java.util.Arrays;

import com.fc.first.beans.Center;

/**
 * 人员的基本信息
 * 
 * @author hxl
 * 
 */
@SuppressWarnings("serial")
public class PersonalBaseInformation implements Serializable {
	private String personName;// 姓名
	private String personBorn;// 出生年月
	private String personSex;// 性别
	private String personCardId;// 身份证号码
	private String personEducation;// 文化程度
	private byte[] personHead;// 头像字节数组
	private String personNational;// 民族
	private String personNativePlace;// 籍贯
	private String personCurrentStatus;// 当前状态
	private String personMobilePhone;// 手机号码
	private String personCurrentAddress;// 当前住址
	private String personStreet;// 街道
	private String personjuweihui;// 居委会
	private String personIntention; // 意向
	private Center center;// 市中心数据
	private String personLevelmsg;// 用户是否有权限信息
	private String personType;// 类别
	private String personBeizhu;// 人员备注
	private String personRoad;
	private String personNong;
	private String personNumber;
	private String personRoom;
	private String personNowRoad;
	private String personNowNong;
	private String personNowNumber;
	private String personNowRoom;
	private String updateTime;
	private String compare_result;
	private String street_id;
	private String committee_id;

	public String getStreet_id() {
		return street_id;
	}

	public void setStreet_id(String street_id) {
		this.street_id = street_id;
	}

	public String getCommittee_id() {
		return committee_id;
	}

	public void setCommittee_id(String committee_id) {
		this.committee_id = committee_id;
	}

	public String getCompare_result() {
		return compare_result;
	}

	public void setCompare_result(String compare_result) {
		this.compare_result = compare_result;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public PersonalBaseInformation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PersonalBaseInformation(String personName, String personBorn,
			String personSex, String personCardId, String personEducation,
			byte[] personHead, String personNational, String personNativePlace,
			String personCurrentStatus, String personMobilePhone,
			String personCurrentAddress, String personStreet,
			String personjuweihui, String personIntention, Center center,
			String personLevelmsg, String personType, String personBeizhu,
			String personRoad, String personNong, String personNumber,
			String personRoom, String personNowRoad, String personNowNong,
			String personNowNumber, String personNowRoom) {
		super();
		this.personName = personName;
		this.personBorn = personBorn;
		this.personSex = personSex;
		this.personCardId = personCardId;
		this.personEducation = personEducation;
		this.personHead = personHead;
		this.personNational = personNational;
		this.personNativePlace = personNativePlace;
		this.personCurrentStatus = personCurrentStatus;
		this.personMobilePhone = personMobilePhone;
		this.personCurrentAddress = personCurrentAddress;
		this.personStreet = personStreet;
		this.personjuweihui = personjuweihui;
		this.personIntention = personIntention;
		this.center = center;
		this.personLevelmsg = personLevelmsg;
		this.personType = personType;
		this.personBeizhu = personBeizhu;
		this.personRoad = personRoad;
		this.personNong = personNong;
		this.personNumber = personNumber;
		this.personRoom = personRoom;
		this.personNowRoad = personNowRoad;
		this.personNowNong = personNowNong;
		this.personNowNumber = personNowNumber;
		this.personNowRoom = personNowRoom;
	}

	public String getPersonBeizhu() {
		return personBeizhu;
	}

	public void setPersonBeizhu(String personBeizhu) {
		this.personBeizhu = personBeizhu;
	}

	public String getPersonNowRoad() {
		return personNowRoad;
	}

	public void setPersonNowRoad(String personNowRoad) {
		this.personNowRoad = personNowRoad;
	}

	public String getPersonNowNong() {
		return personNowNong;
	}

	public void setPersonNowNong(String personNowNong) {
		this.personNowNong = personNowNong;
	}

	public String getPersonNowNumber() {
		return personNowNumber;
	}

	public void setPersonNowNumber(String personNowNumber) {
		this.personNowNumber = personNowNumber;
	}

	public String getPersonNowRoom() {
		return personNowRoom;
	}

	public void setPersonNowRoom(String personNowRoom) {
		this.personNowRoom = personNowRoom;
	}

	public String getPersonRoad() {
		return personRoad;
	}

	public void setPersonRoad(String personRoad) {
		this.personRoad = personRoad;
	}

	public String getPersonNong() {
		return personNong;
	}

	public void setPersonNong(String personNong) {
		this.personNong = personNong;
	}

	public String getPersonNumber() {
		return personNumber;
	}

	public void setPersonNumber(String personNumber) {
		this.personNumber = personNumber;
	}

	public String getPersonRoom() {
		return personRoom;
	}

	public void setPersonRoom(String personRoom) {
		this.personRoom = personRoom;
	}

	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonBorn() {
		return personBorn;
	}

	public void setPersonBorn(String personBorn) {
		this.personBorn = personBorn;
	}

	public String getPersonSex() {
		return personSex;
	}

	public void setPersonSex(String personSex) {
		this.personSex = personSex;
	}

	public String getPersonCardId() {
		return personCardId;
	}

	public void setPersonCardId(String personCardId) {
		this.personCardId = personCardId;
	}

	public String getPersonEducation() {
		return personEducation;
	}

	public void setPersonEducation(String personEducation) {
		this.personEducation = personEducation;
	}

	public byte[] getPersonHead() {
		return personHead;
	}

	public void setPersonHead(byte[] personHead) {
		this.personHead = personHead;
	}

	public String getPersonNational() {
		return personNational;
	}

	public void setPersonNational(String personNational) {
		this.personNational = personNational;
	}

	public String getPersonNativePlace() {
		return personNativePlace;
	}

	public void setPersonNativePlace(String personNativePlace) {
		this.personNativePlace = personNativePlace;
	}

	public String getPersonCurrentStatus() {
		return personCurrentStatus;
	}

	public void setPersonCurrentStatus(String personCurrentStatus) {
		this.personCurrentStatus = personCurrentStatus;
	}

	public String getPersonMobilePhone() {
		return personMobilePhone;
	}

	public void setPersonMobilePhone(String personMobilePhone) {
		this.personMobilePhone = personMobilePhone;
	}

	public String getPersonCurrentAddress() {
		return personCurrentAddress;
	}

	public void setPersonCurrentAddress(String personCurrentAddress) {
		this.personCurrentAddress = personCurrentAddress;
	}

	public String getPersonStreet() {
		return personStreet;
	}

	public void setPersonStreet(String personStreet) {
		this.personStreet = personStreet;
	}

	public String getPersonjuweihui() {
		return personjuweihui;
	}

	public void setPersonjuweihui(String personjuweihui) {
		this.personjuweihui = personjuweihui;
	}

	public String getPersonIntention() {
		return personIntention;
	}

	public void setPersonIntention(String personIntention) {
		this.personIntention = personIntention;
	}

	public Center getCenter() {
		return center;
	}

	public void setCenter(Center center) {
		this.center = center;
	}

	public String getPersonLevelmsg() {
		return personLevelmsg;
	}

	public void setPersonLevelmsg(String personLevelmsg) {
		this.personLevelmsg = personLevelmsg;
	}

	@Override
	public String toString() {
		return "PersonalBaseInformation [personName=" + personName
				+ ", personBorn=" + personBorn + ", personSex=" + personSex
				+ ", personCardId=" + personCardId + ", personEducation="
				+ personEducation + ", personHead="
				+ Arrays.toString(personHead) + ", personNational="
				+ personNational + ", personNativePlace=" + personNativePlace
				+ ", personCurrentStatus=" + personCurrentStatus
				+ ", personMobilePhone=" + personMobilePhone
				+ ", personCurrentAddress=" + personCurrentAddress
				+ ", personStreet=" + personStreet + ", personjuweihui="
				+ personjuweihui + ", personIntention=" + personIntention
				+ ", center=" + center + ", personLevelmsg=" + personLevelmsg
				+ ", personType=" + personType + ", personBeizhu="
				+ personBeizhu + ", personRoad=" + personRoad + ", personNong="
				+ personNong + ", personNumber=" + personNumber
				+ ", personRoom=" + personRoom + ", personNowRoad="
				+ personNowRoad + ", personNowNong=" + personNowNong
				+ ", personNowNumber=" + personNowNumber + ", personNowRoom="
				+ personNowRoom + ", updateTime=" + updateTime
				+ ", compare_result=" + compare_result + "]";
	}

}