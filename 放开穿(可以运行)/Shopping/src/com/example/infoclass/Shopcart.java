package com.example.infoclass;

import java.io.Serializable;

public class Shopcart implements Serializable{
	public String $id;
	public int CartID;
	public int UserID;
	public int ItemID;
	public String ColorName;//要显示
	public String SizeName;//要显示
	public float RentalPrice;//要显示
	public int Quantity;
	public float Cost;
	public String AddTime;
	public String img; //要显示
	public String ItemName;//要显示
	public int LimitedDays;
	public Shopcart(String $id, int cartID, int userID, int itemID,
			String colorName, String sizeName, float rentalPrice, int quantity,
			float cost, String addTime, String img, String itemName,
			int limitedDays) {
		super();
		this.$id = $id;
		CartID = cartID;
		UserID = userID;
		ItemID = itemID;
		ColorName = colorName;
		SizeName = sizeName;
		RentalPrice = rentalPrice;
		Quantity = quantity;
		Cost = cost;
		AddTime = addTime;
		this.img = img;
		ItemName = itemName;
		LimitedDays = limitedDays;
	}
	
	
}


//[{"$id":"1","CartID":463,"UserID":80,"ItemID":488,"ColorName":"绿色",
//	"SizeName":"L","RentalPrice":547.00,"Quantity":2,"Cost":547.00,"AddTime":"2016-07-13",
//	"img":"/Goods/UpdateGood/ShowSmallImage.aspx?PhotoId=2835","ItemName":"PESARO绿色翻领开襟两件套无袖连衣裙","LimitedDays":30},