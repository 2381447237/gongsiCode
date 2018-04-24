package com.base.activity;

import java.util.ArrayList;

import com.example.infoclass.ShopLeft;

public class BizUtil {

	/**
	 * 这个方法只是第一次，为了获取第一级菜单
	 * @param list
	 * @return
	 */
	public static ArrayList<ShopLeft> getMenu(ArrayList<ShopLeft> list) {
		ArrayList<ShopLeft> ShopLeftList = new ArrayList<ShopLeft>();
		for (int i = 0, length = list.size(); i < length; i++) {
			if (list.get(i).ParentCategoryID == (-1)) {
				ShopLeftList.add(list.get(i));
			}
		}
		if (empty(ShopLeftList)) {
			return null;
		}
		return ShopLeftList;
	}

	/**
	 * 点击每个菜单项时，调用这个方法，返回子菜单
	 * @param ShopLeft 你点击的产品对象
	 * @param list 所有的产品列表
	 * @return
	 */
	public static ArrayList<ShopLeft> getSubMenu(ShopLeft shopLeft,
			ArrayList<ShopLeft> list) {

		int number = shopLeft.CategoryID;
		ArrayList<ShopLeft> ShopLeftList = new ArrayList<ShopLeft>();
		for (int i = 0, length = list.size(); i < length; i++) {
			if (number == list.get(i).ParentCategoryID) {
				ShopLeftList.add(list.get(i));
			}
		}
		if (empty(ShopLeftList)) {
			return null;
		}
		return ShopLeftList;
	}

	private static boolean empty(ArrayList<ShopLeft> ShopLeftList) {
		if (ShopLeftList == null || ShopLeftList.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

}
