package com.example.infoclass;

public class BaozuContent {

	public String  $id;
	public int PackageID;
	public String PackageName;//Ҫ���
	public String Description;//Ҫ���
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
//[{"$id":"1","PackageID":1,"PackageName":"��һ������","Description":"299Ԫ","Cost":0.01,"Duration":30,"IsEnabled":true,"DonativeDuration":0,"IsMonthInfo":0}