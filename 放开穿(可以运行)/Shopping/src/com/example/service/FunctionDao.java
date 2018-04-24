package com.example.service;

import java.util.Map;

import org.json.JSONException;

import android.content.Context;

import com.base.activity.ShopApplication;
import com.base.activity.VollyUtils.VolleyListener;
import com.example.httpurl.HttpUrl;

public class FunctionDao {
	VolleyListener listener;
	public void getCollectJson(Context context,Map<String, String> map){
		try {
			ShopApplication.utils
			.init(context)
			.setListener(listener)
			.postJson(HttpUrl.HttpURL+"/Json/Get_tbl_Items_Info.aspx",map);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
