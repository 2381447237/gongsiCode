package com.fc.main.dao;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.fc.main.tools.HttpUtil;

public class ResourcesDao {

	/**
	 * 查询资源信息
	 * @param index
	 * @return
	 */
	public String getResourcesList(){
		String url ="/Json/Get_Resources_Query.aspx";
		try {
			String valueString  = HttpUtil.getRequest(url);
			return valueString;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 得到资源调查数据
	 * @param data
	 * @return
	 */
	public String getGetResourceSurvey(Map<String, String> data) {
		String url = "/Json/GetResourceSurvey.aspx";
		try {
			String value = HttpUtil.postRequest(url, data);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 提交失业详细
	 */
	public String setShiYeDetailInfo(Map<String, String> data){
		String url ="/Json/Set_Resource_Survey_Detil_SY.aspx";
		try {
			String valueString  = HttpUtil.postRequest(url,data);
			return valueString;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 提交无业详细
	 */
	public String setWuYeDetailInfo(Map<String, String> data){
		String url ="/Json/Set_Resource_Survey_Detil_WY.aspx";
		try {
			String valueString  = HttpUtil.postRequest(url,data);
			return valueString;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 查询无业或失业资源信息
	 * @param index
	 * @return
	 */
	public String getResourceSurvey(Map<String, String> data){
		String url ="";
		String type_NameString=data.get("typeName");
		String index=data.get("page");
		String Master_id=data.get("Master_id");
		String type=data.get("type");
		if ("失业".equals(type_NameString)) {
			url="/Json/Get_Resource_Survey_Detil_SY.aspx";
		} else {
			url="/Json/Get_Resource_Survey_Detil_WY.aspx";
		}
		Map<String, String> map=new HashMap<String, String>();
		map.put("page", index);
		map.put("rows", "15");
		map.put("Master_id",Master_id);
		map.put("type", type);
		try {
			String valueString  = HttpUtil.postRequest(url,map);
			return valueString;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 查询
	 * @param index
	 * @return
	 */
	public String getGetSfzResourceSurvey(Map<String, String> data){
		String url="";
		String type_NameString=data.get("typeName");
		String index=data.get("page");
		String Master_id=data.get("Master_id");
		String sfz=data.get("sfz");
		if ("失业".equals(type_NameString)) {
			url="/Json/Get_Resource_Survey_Detil_SY.aspx";
		} else {
			url="/Json/Get_Resource_Survey_Detil_WY.aspx";
		}
		Map<String, String> map=new HashMap<String, String>();
		map.put("page", index);
		map.put("rows", "15");
		map.put("Master_id",Master_id);
		map.put("sfz", sfz);
		try {
			String valueString  = HttpUtil.postRequest(url,map);
			return valueString;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
	 * 提交起航信息
	 */
	public String setQihang_info(Map<String, String> data){
		String url ="/Json/Set_Resource_Survey_Detil_QH.aspx";
		try {
			String valueString  = HttpUtil.postRequest(url,data);
			return valueString;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
