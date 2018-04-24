package com.example.infoclass;

public class BaozuContent {

	public String  $id;
	public int PackageID;
	public String PackageName;//要这个
	public String Description;//要这个
	public float Cost;
	public int Duration;
	public boolean IsEnabled;
	public int DonativeDuration;
	public int IsMonthInfo;
	
	private boolean isSelect;

	public BaozuContent(String packageName, String description, boolean isSelect) {
		super();
		PackageName = packageName;
		Description = description;
		this.isSelect = isSelect;
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		this.isSelect = isSelect;
	}
	
}
//[{"$id":"1","PackageID":1,"PackageName":"第一月体验","Description":"299元","Cost":0.01,"Duration":30,"IsEnabled":true,"DonativeDuration":0,"IsMonthInfo":0}