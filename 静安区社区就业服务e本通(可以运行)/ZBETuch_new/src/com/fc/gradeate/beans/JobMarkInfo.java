package com.fc.gradeate.beans;

import java.io.Serializable;

public class JobMarkInfo implements Serializable {

	private int id;
	private int master_id;
	private String visit_date;
	private String base_situation;
	private String detail_situation1;
	private String detail_situation2;
	private String detail_company;
	private String creat_date;
	private int creat_staff;
	private String update_date;
	private int update_staff;
	private int recordCount;
	private String remark;
	private String inquirer;

	public JobMarkInfo(int id, int master_id, String visit_date,
			String base_situation, String detail_situation1,
			String detail_situation2, String detail_company, String creat_date,
			int creat_staff, String update_date, int update_staff,
			int recordCount, String remark, String inquirer) {
		this.id = id;
		this.master_id = master_id;
		this.visit_date = visit_date;
		this.base_situation = base_situation;
		this.detail_situation1 = detail_situation1;
		this.detail_situation2 = detail_situation2;
		this.detail_company = detail_company;
		this.creat_date = creat_date;
		this.creat_staff = creat_staff;
		this.update_date = update_date;
		this.update_staff = update_staff;
		this.recordCount = recordCount;
		this.remark = remark;
		this.inquirer = inquirer;
	}

	public JobMarkInfo() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMaster_id() {
		return master_id;
	}

	public void setMaster_id(int master_id) {
		this.master_id = master_id;
	}

	public String getVisit_date() {
		return visit_date;
	}

	public void setVisit_date(String visit_date) {
		this.visit_date = visit_date;
	}

	public String getBase_situation() {
		return base_situation;
	}

	public void setBase_situation(String base_situation) {
		this.base_situation = base_situation;
	}

	public String getDetail_situation1() {
		return detail_situation1;
	}

	public void setDetail_situation1(String detail_situation1) {
		this.detail_situation1 = detail_situation1;
	}

	public String getDetail_situation2() {
		return detail_situation2;
	}

	public void setDetail_situation2(String detail_situation2) {
		this.detail_situation2 = detail_situation2;
	}

	public String getDetail_company() {
		return detail_company;
	}

	public void setDetail_company(String detail_company) {
		this.detail_company = detail_company;
	}

	public String getCreat_date() {
		return creat_date;
	}

	public void setCreat_date(String creat_date) {
		this.creat_date = creat_date;
	}

	public int getCreat_staff() {
		return creat_staff;
	}

	public void setCreat_staff(int creat_staff) {
		this.creat_staff = creat_staff;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public int getUpdate_staff() {
		return update_staff;
	}

	public void setUpdate_staff(int update_staff) {
		this.update_staff = update_staff;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getInquirer() {
		return inquirer;
	}

	public void setInquirer(String inquirer) {
		this.inquirer = inquirer;
	}

	@Override
	public String toString() {
		return "JobMarkInfo [id=" + id + ", master_id=" + master_id
				+ ", visit_date=" + visit_date + ", base_situation="
				+ base_situation + ", detail_situation1=" + detail_situation1
				+ ", detail_situation2=" + detail_situation2
				+ ", detail_company=" + detail_company + ", creat_date="
				+ creat_date + ", creat_staff=" + creat_staff
				+ ", update_date=" + update_date + ", update_staff="
				+ update_staff + ", recordCount=" + recordCount + ", remark="
				+ remark + ", inquirer=" + inquirer + "]";
	}

}
