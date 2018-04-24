package com.example.infoclass;

import java.io.Serializable;
import java.util.ArrayList;
import com.imooc.treeview.utils.annotation.TreeNodeId;
import com.imooc.treeview.utils.annotation.TreeNodeLabel;
import com.imooc.treeview.utils.annotation.TreeNodePid;


public class ShopLeft implements Serializable{
	public String $id;
	public ArrayList<String> tbl_FeaturedPhotos_Info;
	public ArrayList<String> tbl_CategoryProperty_Info;
	public ArrayList<String> tbl_ItemCategory_Info;
	@TreeNodeId
	public int CategoryID;
	@TreeNodeLabel
	public String CategoryName;
	public String Description;
	public int CategoryStatus;
	@TreeNodePid
	public int ParentCategoryID;
	public int levelId;
	public int Index;
	
	public ShopLeft(int CategoryID, int ParentCategoryID, String CategoryName)
	{
		this.CategoryID = CategoryID;
		this.ParentCategoryID = ParentCategoryID;
		this.CategoryName= CategoryName;
	}
}
