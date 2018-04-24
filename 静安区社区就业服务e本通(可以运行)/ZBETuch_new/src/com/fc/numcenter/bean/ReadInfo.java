package com.fc.numcenter.bean;

public class ReadInfo {

	private int A_COUNT;
	private int B_COUNT;
	private int METTING_STAFF;
	private String DATE;
	private double RATE;

	public ReadInfo() {
		super();
	}

	public ReadInfo(int a_COUNT, int b_COUNT, int mETTING_STAFF, String dATE,
			double rATE) {
		super();
		A_COUNT = a_COUNT;
		B_COUNT = b_COUNT;
		METTING_STAFF = mETTING_STAFF;
		DATE = dATE;
		RATE = rATE;
	}

	public int getA_COUNT() {
		return A_COUNT;
	}

	public void setA_COUNT(int a_COUNT) {
		A_COUNT = a_COUNT;
	}

	public int getB_COUNT() {
		return B_COUNT;
	}

	public void setB_COUNT(int b_COUNT) {
		B_COUNT = b_COUNT;
	}

	public int getMETTING_STAFF() {
		return METTING_STAFF;
	}

	public void setMETTING_STAFF(int mETTING_STAFF) {
		METTING_STAFF = mETTING_STAFF;
	}

	public String getDATE() {
		return DATE;
	}

	public void setDATE(String dATE) {
		DATE = dATE;
	}

	public double getRATE() {
		return RATE;
	}

	public void setRATE(double rATE) {
		RATE = rATE;
	}
}
