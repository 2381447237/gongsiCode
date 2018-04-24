package com.fc.main.beans;

import java.io.Serializable;

public class PersonItem implements Serializable {
	private int staff_id;
	private String name;
	private int line_id;

	public PersonItem() {
	}

	public PersonItem(int staff_id, String name, int line_id) {
		this.staff_id = staff_id;
		this.name = name;
		this.line_id = line_id;
	}

	public int getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLine_id() {
		return line_id;
	}

	public void setLine_id(int line_id) {
		this.line_id = line_id;
	}

	@Override
	public String toString() {
		return "PersonItem [staff_id=" + staff_id + ", name=" + name
				+ ", line_id=" + line_id + "]";
	}

}
