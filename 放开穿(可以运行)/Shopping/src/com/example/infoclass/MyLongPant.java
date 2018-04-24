package com.example.infoclass;

public class MyLongPant {

	private String longPantSize;
	private boolean checked;
	public MyLongPant(String longPantSize, boolean checked) {
		super();
		this.longPantSize = longPantSize;
		this.checked = checked;
	}
	public String getLongPantSize() {
		return longPantSize;
	}
	public void setLongPantSize(String longPantSize) {
		this.longPantSize = longPantSize;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
