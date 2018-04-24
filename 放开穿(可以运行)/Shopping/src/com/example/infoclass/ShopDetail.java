package com.example.infoclass;

import java.io.Serializable;
import java.util.ArrayList;

public class ShopDetail implements Serializable{
	public String $id;
	public int ItemId;
	public int SellableStocks;
	public String ItemName;
	public String SizeName;
	public String ColorName;
	public ArrayList<String> img;
	public float RentalPrice;
	public float RetailPrice;
	public String Decsportion;
	public ArrayList<ShopSame> similrstylesList;
	public ArrayList<String> Starts;
	public int Comments;
	public int PageNum;
	public int LimitedDays;
	public int IsCollect;
}
