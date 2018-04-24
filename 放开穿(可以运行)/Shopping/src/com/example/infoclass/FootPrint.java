package com.example.infoclass;

public class FootPrint {

	public String $id;
	public int CookieID;
	public String ItemName;//名字
	public float RentalPrice;//租凭价
	public String Img;//图片地址
	public float RetailPrice;//零售价
	public int ItemId;
	public FootPrint(String itemName, float rentalPrice, float retailPrice) {
		super();
		ItemName = itemName;
		RentalPrice = rentalPrice;
		RetailPrice = retailPrice;
	}
	
}


//[{"$id":"1","CookieID":805,"ItemName":"oudifu时尚短款无袖系扣上衣（附内搭）","RentalPrice":300.00,
//"Img":"/Goods/UpdateGood/ShowSmallImage.aspx?PhotoId=2152","RetailPrice":1198.00,"ItemId":321}]