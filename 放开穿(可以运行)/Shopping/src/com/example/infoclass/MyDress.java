package com.example.infoclass;

public class MyDress {

	private String dressSize;
	private boolean checked;
	public MyDress(String dressSize, boolean checked) {
		super();
		this.dressSize = dressSize;
		this.checked = checked;
	}
	public String getDressSize() {
		return dressSize;
	}
	public void setDressSize(String dressSize) {
		this.dressSize = dressSize;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
