package com.fc.main.dao;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fc.main.tools.HttpUtil;

public class SecurityDao {

	public String sendApps(Map<String, String> data){
		String jsonString=parseToJson(data.get("apps"), data.get("films"));
		String url ="/Json/Set_TB_Staff_Pad_File.aspx";
		String valueString  = HttpUtil.postJson(url, jsonString,null);
		return valueString;
	}

	private String parseToJson(String apps,String films){
		String[] appStrings=apps.split(",");
		System.out.println(appStrings.length);
		String[] filmsStrings=films.split(",");
		System.out.println(filmsStrings.length);
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject;
		try {
			for (int i = 0; i < appStrings.length; i++) {
				jsonObject=new JSONObject();
				jsonObject.put("NAME",appStrings[i]);
				jsonObject.put("TYPE", "程序");
				jsonArray.put(jsonObject);
			}
			for (int i = 0; i < filmsStrings.length; i++) {
				jsonObject=new JSONObject();
				jsonObject.put("NAME",filmsStrings[i]);
				jsonObject.put("TYPE", "视频");
				jsonArray.put(jsonObject);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonArray.toString();
	}
}
