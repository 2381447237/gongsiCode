package com.example.infoclass;

public class FootPrint {

	public String $id;
	public int CookieID;
	public String ItemName;//����
	public float RentalPrice;//��ƾ��
	public String Img;//ͼƬ��ַ
	public float RetailPrice;//���ۼ�
	public int ItemId;
	public FootPrint(String itemName, float rentalPrice, float retailPrice) {
		super();
		ItemName = itemName;
		RentalPrice = rentalPrice;
		RetailPrice = retailPrice;
	}
	
}


//[{"$id":"1","CookieID":805,"ItemName":"oudifuʱ�ж̿�����ϵ�����£����ڴ","RentalPrice":300.00,
//"Img":"/Goods/UpdateGood/ShowSmallImage.aspx?PhotoId=2152","RetailPrice":1198.00,"ItemId":321}]