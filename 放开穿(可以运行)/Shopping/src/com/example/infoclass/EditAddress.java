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
	public String ProvinceName;//Ҫ��ʾ  ʡ
	public int CityID;
	public String CityName;//Ҫ��ʾ  ��
	public int DistrictID;
	public String DistrictName;//Ҫ��ʾ  ��
	public String DetailAddr;//Ҫ��ʾ  ��ϸ��ַ
	public boolean IsDefault;
	public String Consigner;//Ҫ��ʾ  �ջ���
	public String PhoneNumber;//Ҫ��ʾ  �绰����
	public String ZipCode;//Ҫ��ʾ  ��������
	
}

//{"$id":"1","tbl_MyOrders_Info":[],"tbl_Users_Info":null,"AddrID":331,"UserID":80,"ProvinceID":1,"ProvinceName":"�Ϻ�",
//"CityID":1,"CityName":"�Ϻ���","DistrictID":23,"DistrictName":"������","DetailAddr":"ʥ��","IsDefault":true,"Consigner":"Ԫʼ����",
//"PhoneNumber":"13658079063","ZipCode":"210000"}