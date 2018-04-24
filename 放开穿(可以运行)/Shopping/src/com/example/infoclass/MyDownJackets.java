package com.example.infoclass;

public class MyDownJackets {

	private String downJacketsSize;
	private boolean checked;
	public MyDownJackets(String downJacketsSize, boolean checked) {
		super();
		this.downJacketsSize = downJacketsSize;
		this.checked = checked;
	}
	public String getDownJacketsSize() {
		return downJacketsSize;
	}
	public void setDownJacketsSize(String downJacketsSize) {
		this.downJacketsSize = downJacketsSize;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
