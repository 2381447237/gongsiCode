package com.example.infoclass;

public class MyModel {

	private String modelSize;
	private boolean checked;
	public MyModel(String modelSize, boolean checked) {
		super();
		this.modelSize = modelSize;
		this.checked = checked;
	}
	public String getModelSize() {
		return modelSize;
	}
	public void setModelSize(String modelSize) {
		this.modelSize = modelSize;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
}
