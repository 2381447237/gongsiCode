package com.example.infoclass;

public class MyColorCheck {
	private String imageButtonType;
	private String imageButtonBg;
	private int imageButtonMiddle;
	private boolean checked;
	
	public MyColorCheck(String imageButtonType, String imageButtonBg,int imageButtonMiddle,boolean checked) {
		super();
		this.imageButtonType=imageButtonType;
		this.imageButtonBg=imageButtonBg;
		this.imageButtonMiddle= imageButtonMiddle;
		this.checked=checked;
	}

	public String getImageButtonType() {
		return imageButtonType;
	}

	public void setImageButtonType(String imageButtonType) {
		this.imageButtonType = imageButtonType;
	}

	public int getImageButtonMiddle() {
		return imageButtonMiddle;
	}

	public void setImageButtonMiddle(int imageButtonMiddle) {
		this.imageButtonMiddle = imageButtonMiddle;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getImageButtonBg() {
		return imageButtonBg;
	}

	public void setImageButtonBg(String imageButtonBg) {
		this.imageButtonBg = imageButtonBg;
	}
	
}
