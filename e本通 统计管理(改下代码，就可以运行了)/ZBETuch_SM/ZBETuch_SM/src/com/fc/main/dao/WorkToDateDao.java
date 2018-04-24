package com.fc.main.dao;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.fc.main.tools.HttpUtil;

public class WorkToDateDao {
	/**
	 * 上传工作日志
	 * @param data
	 * @return
	 */
	public String setWorkDate(String filePath,Map<String, String> data){
		String url ="/Json/Set_WorkToDate.aspx";
		try {
			String valueString  = HttpUtil.upLoadImage(url, filePath, data);
			return valueString;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 得到工作日志列表
	 * @param data
	 * @return
	 */
	public String getWorkDateList(Map<String, String> data){
		String url = "/Json/Get_WorkToDate.aspx";
		//		String url = "/Json/Get_WorkToDate.aspx?page="+data.get("page")+"&rows="+data.get("rows");
		try {
			String valueStr = HttpUtil.postRequest(url, data);
			//			String valueStr = HttpUtil.getRequest(url);
			return valueStr;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 得到工作日志图片
	 * @param id
	 * @return
	 */
	public byte[] getWorkToDateImage(int id){
		String url = "/Json/Get_WorkToDate_Pic.aspx?id="+id;
		try {
			return HttpUtil.getImage(url);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getLoginInfo(Map<String, String> data){
		String url ="/Json/Get_Staff_Login.aspx";
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

	public String getLoginInfosString(Map<String, String> data){
		String url ="/Json/Get_Staff_Log.aspx";
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
	
	public String getERecordInfo(Map<String, String> data) {
		String url = "/Json/Get_TB_Staff_Pad_File.aspx";
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
}
