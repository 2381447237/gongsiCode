package com.fc.company.beans;

public class CompanyItem {
	private int comid;
	private String comcode;
	private String comname;
	private int compropertyid;
	private String comscope;
	private String agreeno;
	private String agreestartdate;
	private String agreeenddate;
	private String contactor;
	private String telephone;
	private String address;
	private String zipcode;
	private String email;
	private String traffic;
	private String comintroduction;
	private String createtime;
	private String createuser;

	public CompanyItem() {
	}

	public CompanyItem(int comid, String comcode, String comname,
			int compropertyid, String comscope, String agreeno,
			String agreestartdate, String agreeenddate, String contactor,
			String telephone, String address, String zipcode, String email,
			String traffic, String comintroduction, String createtime,
			String createuser) {
		this.comid = comid;
		this.comcode = comcode;
		this.comname = comname;
		this.compropertyid = compropertyid;
		this.comscope = comscope;
		this.agreeno = agreeno;
		this.agreestartdate = agreestartdate;
		this.agreeenddate = agreeenddate;
		this.contactor = contactor;
		this.telephone = telephone;
		this.address = address;
		this.zipcode = zipcode;
		this.email = email;
		this.traffic = traffic;
		this.comintroduction = comintroduction;
		this.createtime = createtime;
		this.createuser = createuser;
	}

	public int getComid() {
		return comid;
	}

	public void setComid(int comid) {
		this.comid = comid;
	}

	public String getComcode() {
		return comcode;
	}

	public void setComcode(String comcode) {
		this.comcode = comcode;
	}

	public String getComname() {
		return comname;
	}

	public void setComname(String comname) {
		this.comname = comname;
	}

	public int getCompropertyid() {
		return compropertyid;
	}

	public void setCompropertyid(int compropertyid) {
		this.compropertyid = compropertyid;
	}

	public String getComscope() {
		return comscope;
	}

	public void setComscope(String comscope) {
		this.comscope = comscope;
	}

	public String getAgreeno() {
		return agreeno;
	}

	public void setAgreeno(String agreeno) {
		this.agreeno = agreeno;
	}

	public String getAgreestartdate() {
		return agreestartdate;
	}

	public void setAgreestartdate(String agreestartdate) {
		this.agreestartdate = agreestartdate;
	}

	public String getAgreeenddate() {
		return agreeenddate;
	}

	public void setAgreeenddate(String agreeenddate) {
		this.agreeenddate = agreeenddate;
	}

	public String getContactor() {
		return contactor;
	}

	public void setContactor(String contactor) {
		this.contactor = contactor;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getComintroduction() {
		return comintroduction;
	}

	public void setComintroduction(String comintroduction) {
		this.comintroduction = comintroduction;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getCreateuser() {
		return createuser;
	}

	public void setCreateuser(String createuser) {
		this.createuser = createuser;
	}

}
