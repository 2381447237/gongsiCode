package com.fc.first.beans;

import java.io.Serializable;

public class LostJobInformation implements Serializable {
	private String time;
	private String man;
	private String woman;

	public LostJobInformation() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LostJobInformation(String time, String man, String woman) {
		super();
		this.time = time;
		this.man = man;
		this.woman = woman;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMan() {
		return man;
	}

	public void setMan(String lostJobMale) {
		this.man = lostJobMale;
	}

	public String getWoman() {
		return woman;
	}

	public void setWoman(String lostJobFeMale) {
		this.woman = lostJobFeMale;
	}

	@Override
	public String toString() {
		return "LostJobInformation [time=" + time + ", man=" + man + ", woman="
				+ woman + "]";
	}

}
