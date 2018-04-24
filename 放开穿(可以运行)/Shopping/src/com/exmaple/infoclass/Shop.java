package com.exmaple.infoclass;

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
	public ArrayList<String> img;
}
