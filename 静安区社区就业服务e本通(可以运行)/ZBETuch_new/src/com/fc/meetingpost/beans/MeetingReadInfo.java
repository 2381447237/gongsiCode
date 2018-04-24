package com.fc.meetingpost.beans;

import java.io.Serializable;

import android.R.integer;

public class MeetingReadInfo implements Serializable {
	private int value1;
	private int value2;
	private double value3;

	public int getValue1() {
		return value1;
	}

	public void setValue1(int value1) {
		this.value1 = value1;
	}

	public int getValue2() {
		return value2;
	}

	public void setValue2(int value2) {
		this.value2 = value2;
	}

	public double getValue3() {
		return value3;
	}

	public void setValue3(double value3) {
		this.value3 = value3;
	}

	public MeetingReadInfo(int value1, int value2, double value3) {
		super();
		this.value1 = value1;
		this.value2 = value2;
		this.value3 = value3;
	}

	public MeetingReadInfo() {
		super();
	}

	@Override
	public String toString() {
		return "MeetingReadInfo [value1=" + value1 + ", value2=" + value2
				+ ", value3=" + value3 + "]";
	}

}
