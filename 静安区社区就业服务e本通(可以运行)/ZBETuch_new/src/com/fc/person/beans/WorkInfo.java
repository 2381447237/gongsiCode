package com.fc.person.beans;

import java.io.Serializable;

public class WorkInfo implements Serializable {

	private int id;
	private String sfz;
	private String dw_name;
	private String start_date;
	private String end_date;
	private String dw_type;
	private String trade;
	private String dept;
	private String position;
	private String create_date;
	private String create_staff;
	private String update_date;
	private String update_staff;
	private String type;

	public WorkInfo() {
	}

	public WorkInfo(int id, String sfz, String dw_name, String start_date,
			String end_date, String dw_type, String trade, String dept,
			String position, String create_date, String create_staff,
			String update_date, String update_staff, String type) {
		this.id = id;
		this.sfz = sfz;
		this.dw_name = dw_name;
		this.start_date = start_date;
		this.end_date = end_date;
		this.dw_type = dw_type;
		this.trade = trade;
		this.dept = dept;
		this.position = position;
		this.create_date = create_date;
		this.create_staff = create_staff;
		this.update_date = update_date;
		this.update_staff = update_staff;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSfz() {
		return sfz;
	}

	public void setSfz(String sfz) {
		this.sfz = sfz;
	}

	public String getDw_name() {
		return dw_name;
	}

	public void setDw_name(String dw_name) {
		this.dw_name = dw_name;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getDw_type() {
		return dw_type;
	}

	public void setDw_type(String dw_type) {
		this.dw_type = dw_type;
	}

	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public String getCreate_staff() {
		return create_staff;
	}

	public void setCreate_staff(String create_staff) {
		this.create_staff = create_staff;
	}

	public String getUpdate_date() {
		return update_date;
	}

	public void setUpdate_date(String update_date) {
		this.update_date = update_date;
	}

	public String getUpdate_staff() {
		return update_staff;
	}

	public void setUpdate_staff(String update_staff) {
		this.update_staff = update_staff;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
