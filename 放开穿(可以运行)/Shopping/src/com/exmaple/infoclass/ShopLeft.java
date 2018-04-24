package com.exmaple.infoclass;

import java.io.Serializable;
import java.util.ArrayList;

public class ShopLeft implements Serializable{
	public String $id;
	public ArrayList<String> tbl_FeaturedPhotos_Info;
	public ArrayList<String> tbl_CategoryProperty_Info;
	public ArrayList<String> tbl_ItemCategory_Info;
	public int CategoryID;
	public String CategoryName;
	public String Description;
	public int CategoryStatus;
	public int ParentCategoryID;
	public int levelId;
	public int Index;
}
