package com.example.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.client.ClientProtocolException;
import com.base.activity.ShopApplication;
import com.example.httpurl.HttpTool;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

public class MainDaos {


	public String getClassifyInfo() {

		// String url = "/Json/Get_MsgBoard_Detile.aspx";
		String url = "/Json/Get_FeaturedPhotos_Info.aspx";
		try {
			String value = HttpTool.getRequest(url);
			// Log.i("qwj", "value=="+value);
			return value;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}
	public String postClassifyInfo() {

		// String url = "/Json/Get_MsgBoard_Detile.aspx";
		String url = "/Json/Get_FeaturedPhotos_Info.aspx";
		try {
			String value = HttpTool.postRequest(url,null);
			// Log.i("qwj", "value=="+value);
			return value;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	public String getShopInfo(Map<String, String> data) {

		// String url = "/Json/Get_MsgBoard_Detile.aspx";
		String url = "/Json/Get_tbl_Items_Info.aspx";
		try {
			String value = HttpTool.postRequest(url, data);
			//Log.i("shop", "value==" + value);
			return value;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}
	/**
	 * 
	 * @param 加入收藏夹
	 * @return
	 */
	public String postCollectInfo(Map<String, String> data) {
		
		// String url = "/Json/Get_MsgBoard_Detile.aspx";
		String url = "/Json/Set_Favorites_Info.aspx";
		try {
			String value = HttpTool.postRequest(url, data);
			//Log.i("shop", "value==" + value);
			return value;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}
	/**
	 * 
	 * @param 删除收藏夹数据
	 * @return
	 */
	public String cancelCollectInfo(Map<String, String> data) {
		
		// String url = "/Json/Get_MsgBoard_Detile.aspx";
		String url = "/Json/Set_Favorites_Info.aspx";
		try {
			String value = HttpTool.postRequest(url, data);
			//Log.i("shop", "value==" + value);
			return value;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	public String getShopLeftInfo() {

		// String url = "/Json/Get_MsgBoard_Detile.aspx";
		String url = "/json/Get_tbl_Category_Info.aspx";
		try {
			String value = HttpTool.getRequest(url);
			// Log.i("qwj", "value=="+value);
			return value;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	public String getShopSizeInfo(int Category_Id) {

		// String url = "/Json/Get_MsgBoard_Detile.aspx";
		String url = "/Json/Get_Right_Info.aspx?Category_Id=" + Category_Id;
		try {
			String value = HttpTool.getRequest(url);
			// Log.i("qqq", "value=="+value);
			return value;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	public String getShopChooseInfo(int Category_Id,
			ArrayList<String> sizeList, ArrayList<String> colorList,
			float start, float end) {
		String url = "/Json/Get_tbl_Items_Info.aspx?AcctID=1&Category_Id="
				+ Category_Id + "&PageIndex=1&StartMoney=" + start
				+ "&EndMoney=" + end + "&ColorName=";
		Pattern p;
		Matcher m;
		String str1 = "";
		String str2 = "";
		String str3 = "";
		for (String s : colorList) {
			if (s.length() <= 6) {
				str1 = str1 + s + ",";
			} else {
				String[] bb = s.substring(3, s.length()).split("=");
				str1 = str1 + bb[1] + ",";
			}
		}
		for (String s : sizeList) {
			p = Pattern.compile("[a-zA-Z]");
			m = p.matcher(s);
			if (m.matches()) {
				str2 = str2 + "_" + s + ",";
			} else {
				str3 = str3 + s + ",";
			}

		}
		url = url + str1 + "&SizeName=" + str2 + "&ProOptName=" + str3;
		try {
			String value = HttpTool.getRequest(url);
			// Log.i("qqq", "value=="+value);
			return value;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

	public byte[] getClassifyImage(String url) {
		// String url=sharedPreferences.getString("imagepath1", "");
		try {
			byte[] value = HttpTool.getImage(url);
			// Log.i("qwj", "value=="+value);
			return value;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	
}
