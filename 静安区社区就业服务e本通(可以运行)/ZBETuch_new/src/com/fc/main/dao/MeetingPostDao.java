package com.fc.main.dao;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.fc.main.tools.HttpUtil;

public class MeetingPostDao {

	/**
	 * 获取会议通知列表
	 * 
	 * @return
	 */
	public String getMeetList(Map<String, String> data) {
		String url = "/Json/Get_Meeting_Master.aspx";
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
	 * 获取会议通知读取数目
	 * 
	 * @return
	 */
	public String getMeetingRead() {
		String url = "/Json/Get_Meeting_Read.aspx";
		try {
			String value = HttpUtil.getRequest(url);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取会议通知读取状态
	 * 
	 * @return
	 */
	public String getMeetCheck(Map<String, String> data) {
		String url = "/Json/Get_Meeting_Check.aspx";
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
	 * 上传会议通知已读状态
	 * 
	 * @return
	 */
	public String setMeetCheck(Map<String, String> data) {
		String url = "/Json/Set_Meeting_Check.aspx";
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
	 * 获取会议通知附件
	 * 
	 * @param Master_id
	 * @return
	 */
	public String getMeetFile(Map<String, String> data) {
		String url = "/Json/Get_Meeting_File.aspx";
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

	public String getMeetingReadGroup() {
		String url = "/Json/Get_Meeting_Read_GRoup.aspx";
		try {
			String value = HttpUtil.getRequest(url);
			return value;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
