package com.example.infoclass;

public class MySize {

	private String sizeType;
	private boolean isSelected;
	
	public MySize() {
	
	}
	public MySize(String sizeType, boolean isSelected) {
		
		this.sizeType = sizeType;
		this.isSelected = isSelected;
	}
	public String getSizeType() {
		return sizeType;
	}
	public void setSizeType(String sizeType) {
		this.sizeType = sizeType;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	
}
