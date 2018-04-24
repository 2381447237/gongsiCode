package com.example.infoclass;

import java.util.List;

public class DetailedAddressInfo {

	public String $id;
	public List<String> tbl_MyOrders_Info;
	public String tbl_Users_Info;
	public int AddrID;
	public int UserID;
	public int ProvinceID;
	public String ProvinceName;
	public int CityID;
	public String CityName;
	public int DistrictID;//��Ҫ���ֵ
	public String DistrictName;
	public String DetailAddr;//��Ҫ���ֵ
	public boolean IsDefault;
	public String Consigner;
	public String PhoneNumber;
	public String ZipCode;//��Ҫ���ֵ
}

//{"$id":"1","tbl_MyOrders_Info":[],"tbl_Users_Info":null,"AddrID":373,"UserID":80,
//	"ProvinceID":1,"ProvinceName":"�Ϻ�","CityID":1,"CityName":"�Ϻ���","DistrictID":5,
//	"DistrictName":"������","DetailAddr":"444","IsDefault":true,"Consigner":"444","PhoneNumber":"13698340934","ZipCode":"210000"}