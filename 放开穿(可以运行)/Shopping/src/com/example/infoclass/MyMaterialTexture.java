package com.example.infoclass;

public class MyMaterialTexture {

	private String materialTextureSize;
	private boolean checked;
	public MyMaterialTexture(String materialTextureSize, boolean checked) {
		super();
		this.materialTextureSize = materialTextureSize;
		this.checked = checked;
	}
	public String getMaterialTextureSize() {
		return materialTextureSize;
	}
	public void setMaterialTextureSize(String materialTextureSize) {
		this.materialTextureSize = materialTextureSize;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
