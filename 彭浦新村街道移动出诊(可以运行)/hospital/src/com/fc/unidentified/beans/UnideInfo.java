package com.fc.unidentified.beans;

import java.io.Serializable;

public class UnideInfo implements Serializable {
	private String $id;
	private int RegId;
	private int PatientId;
	private String card;//卡号
	private String name;//姓名
	private String sex;//性别
	private String age;//年龄
	private String address;//地址
	private String BirthDate;//出生日期
	private String RCName;//所属居委
	private String AppointVisitDate;//出诊时间
	private String Contactor;//联系人姓名
	private String ContactPhone;//联系人电话
	private String RegRemark;//备注
	
	
	

	public int getRegId() {
		return RegId;
	}
	public void setRegId(int regId) {
		RegId = regId;
	}
	public int getPatientId() {
		return PatientId;
	}
	public void setPatientId(int patientId) {
		PatientId = patientId;
	}
	public String getBirthDate() {
		return BirthDate;
	}
	public void setBirthDate(String birthDate) {
		BirthDate = birthDate;
	}
	public String getRCName() {
		return RCName;
	}
	public void setRCName(String rCName) {
		RCName = rCName;
	}
	public String getAppointVisitDate() {
		return AppointVisitDate;
	}
	public void setAppointVisitDate(String appointVisitDate) {
		AppointVisitDate = appointVisitDate;
	}
	public String getContactor() {
		return Contactor;
	}
	public void setContactor(String contactor) {
		Contactor = contactor;
	}
	public String getContactPhone() {
		return ContactPhone;
	}
	public void setContactPhone(String contactPhone) {
		ContactPhone = contactPhone;
	}
	public String getRegRemark() {
		return RegRemark;
	}
	public void setRegRemark(String regRemark) {
		RegRemark = regRemark;
	}
	public String getCard() {
		return card;
	}
	public void setCard(String card) {
		this.card = card;
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
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String get$id() {
		return $id;
	}
	public void set$id(String $id) {
		this.$id = $id;
	}
	public UnideInfo(String $id,int regId,int patientId,String card,String name,String sex,  String age,String address,
			String BirthDate,String RCName,String AppointVisitDate, 
			String Contactor,String ContactPhone,String RegRemark) {
		super();
		this.card=card;
		this.name=name;
		this.sex = sex;
		this.age = age;
		this.address = address;
		this.BirthDate=BirthDate;
		this.RCName=RCName;
		this.AppointVisitDate=AppointVisitDate;
		this.Contactor=Contactor;
		this.ContactPhone=ContactPhone;
		this.RegRemark=RegRemark;
		this.RegId = regId;
		this.PatientId = patientId;
		this.$id=$id;
	}
	
	public UnideInfo() {
		super();
	}
	
	@Override
	public String toString() {
		return "UnideInfo [card=" + card +", name=" + name + ", sex=" + sex + ", age=" + age + 
				", address=" + address+", BirthDate=" + BirthDate +", RCName=" + RCName +
				", AppointVisitDate=" + AppointVisitDate +", Contactor=" + Contactor +
				", ContactPhone=" + ContactPhone +", RegRemark=" + RegRemark +
				", RegId=" + RegId +", PatientId=" + PatientId+"]";
	}
	
	
}
