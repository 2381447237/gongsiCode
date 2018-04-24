package com.example.infoclass;

public class MyZipper {

	private String zipperSize;
	private boolean checked;
	public MyZipper(String zipperSize, boolean checked) {
		super();
		this.zipperSize = zipperSize;
		this.checked = checked;
	}
	public String getZipperSize() {
		return zipperSize;
	}
	public void setZipperSize(String zipperSize) {
		this.zipperSize = zipperSize;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
