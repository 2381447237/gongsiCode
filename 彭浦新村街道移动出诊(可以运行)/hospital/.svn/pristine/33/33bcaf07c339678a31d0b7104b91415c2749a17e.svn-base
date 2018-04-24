package com.fc.helper;

import java.util.List;

import com.fc.unidentified.beans.ItemsInfo;

import android.app.Application;

public class CustomApplication extends Application {
	private String freque;// 频率
	private String usage;// 用法
	private int StaffId;// 开方医生序号
	private List<ItemsInfo> itemsinfos;//处方信息

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		setFreque("");
		setUsage("");
		setStaffId(-1);
		setItemsinfo(null);
	}

	public List<ItemsInfo> getItemsinfo() {
		return itemsinfos;
	}

	public void setItemsinfo(List<ItemsInfo> itemsinfos) {
		this.itemsinfos = itemsinfos;
	}

	public int getStaffId() {
		return StaffId;
	}

	public void setStaffId(int staffId) {
		StaffId = staffId;
	}

	public String getFreque() {
		return freque;
	}

	public void setFreque(String freque) {
		this.freque = freque;
	}

	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}

}
