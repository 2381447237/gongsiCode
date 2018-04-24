package com.example.infoclass;

import java.io.Serializable;
import java.util.List;

public class EditAddress  implements Serializable{

	public String $id;
	public List<String> tbl_MyOrders_Info;
	public String tbl_Users_Info;
	public  int AddrID;
	public int UserID;
	public int ProvinceID;
	public String ProvinceName;//要显示  省
	public int CityID;
	public String CityName;//要显示  区
	public int DistrictID;
	public String DistrictName;//要显示  县
	public String DetailAddr;//要显示  详细地址
	public boolean IsDefault;
	public String Consigner;//要显示  收货人
	public String PhoneNumber;//要显示  电话号码
	public String ZipCode;//要显示  邮政编码
	
}

//{"$id":"1","tbl_MyOrders_Info":[],"tbl_Users_Info":null,"AddrID":331,"UserID":80,"ProvinceID":1,"ProvinceName":"上海",
//"CityID":1,"CityName":"上海市","DistrictID":23,"DistrictName":"黄埔区","DetailAddr":"圣人","IsDefault":true,"Consigner":"元始天尊",
//"PhoneNumber":"13658079063","ZipCode":"210000"}