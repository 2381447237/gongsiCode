package com.example.infoclass;

public class MyStraight {

	private String straightSize;
	private boolean checked;
	public MyStraight(String straightSize, boolean checked) {
		super();
		this.straightSize = straightSize;
		this.checked = checked;
	}
	public String getStraightSize() {
		return straightSize;
	}
	public void setStraightSize(String straightSize) {
		this.straightSize = straightSize;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
