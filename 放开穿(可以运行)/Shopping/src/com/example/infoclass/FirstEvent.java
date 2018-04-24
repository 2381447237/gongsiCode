package com.example.infoclass;

public class FirstEvent {
	private int CategoryID;
	private String CateGoryName;
	public FirstEvent(int categoryID,String CateGoryName) {
		super();
		CategoryID = categoryID;
		this.CateGoryName=CateGoryName;
	}
	public FirstEvent(int categoryID) {
		super();
		CategoryID = categoryID;
	}
	public FirstEvent() {
		super();
	}


	public int getCategoryID() {
		return CategoryID;
	}

	public void setCategoryID(int categoryID) {
		CategoryID = categoryID;
	}


	public String getCateGoryName() {
		return CateGoryName;
	}


	public void setCateGoryName(String cateGoryName) {
		CateGoryName = cateGoryName;
	}
	
	
}
