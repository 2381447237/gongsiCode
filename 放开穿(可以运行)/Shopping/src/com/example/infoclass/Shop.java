package com.example.infoclass;

import java.io.Serializable;
import java.util.ArrayList;

public class Shop implements Serializable{
	public String $id;
	public int ItemId;
	public String ItemName;
	public float RetailPrice;
	public float RentalPrice;
	public int IsCollect;
	public String ColorName;
	public String SizeName;
	public int PageCount;
	public int PageNum;
	public String OnShelvesDate;
	public ArrayList<String> img;

	public boolean isCheck;

	public boolean isCheck() {
		return isCheck;
	}

	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
	
	
}
//[{"$id":"1","ItemId":667,"ItemName":"PESARO黄/黑色时尚几何印花短袖上衣","RetailPrice":1488.00,
//		"RentalPrice":372.00,"IsCollect":1,"img":["/Goods/UpdateGood/ShowSmallImage.aspx?PhotoId=3553",
//		                                          "/Goods/UpdateGood/ShowSmallImage.aspx?PhotoId=3554",
//		                                          "/Goods/UpdateGood/ShowSmallImage.aspx?PhotoId=3555",
//		                                          "/Goods/UpdateGood/ShowSmallImage.aspx?PhotoId=3556"],
//"ColorName":"黄/黑","SizeName":"L","PageCount":0,"PageNum":0,"OnShelvesDate":"2016-07-15T00:00:00"},
