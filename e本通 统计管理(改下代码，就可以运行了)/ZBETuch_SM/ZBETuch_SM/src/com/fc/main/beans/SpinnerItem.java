package com.fc.main.beans;

public class SpinnerItem {
	private int id;
	private String name;
	private String code;
	
	public SpinnerItem(int id, String name, String code) {
		this.id = id;
		this.name = name;
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public SpinnerItem(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public SpinnerItem() {
	}

	@Override
	public String toString() {
		return this.name;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
