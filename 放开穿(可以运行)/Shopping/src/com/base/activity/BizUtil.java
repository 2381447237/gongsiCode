package com.base.activity;

import java.util.ArrayList;

import com.example.infoclass.ShopLeft;

public class BizUtil {

	/**
	 * �������ֻ�ǵ�һ�Σ�Ϊ�˻�ȡ��һ���˵�
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
	 * ���ÿ���˵���ʱ��������������������Ӳ˵�
	 * @param ShopLeft �����Ĳ�Ʒ����
	 * @param list ���еĲ�Ʒ�б�
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
