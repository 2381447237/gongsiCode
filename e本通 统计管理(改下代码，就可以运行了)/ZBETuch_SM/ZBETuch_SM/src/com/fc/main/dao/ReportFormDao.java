package com.fc.main.dao;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.fc.main.tools.HttpUtil;

public class ReportFormDao {

	/**
	 * 上传报表
	 * @return 
	 */
	public String setReport(Map<String, String> data) {
		String url = "/Json/Set_Manage_Report.aspx";
		System.out.println("------======++++"+data.get("data"));
		String value = HttpUtil.postJson(url, data.get("data"),null);
		System.out.println("values==="+value);
		return value;

	}

	/**
	 * 获取会议通知列表
	 * @return 
	 */
	public String getReportList(Map<String, String> data) {
		String url = "/Json/Get_Manage_Report.aspx";
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
	 * 获取报表查阅读取状态
	 * @return 
	 */
	public String getManageReportCheck(Map<String, String> data) {
		String url = "/Json/Get_Manage_Report_Check.aspx";
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
	 * 上传报表查阅已读状态
	 * @return 
	 */
	public String setManageReportCheck(Map<String, String> data) {
		String url = "/Json/Set_Manage_Report_Check.aspx";
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
	 * 获取报表查阅附件
	 * @return 
	 */
	public String getManageReportFile(Map<String, String> data) {
		String url = "/Json/Get_Manage_Report_File.aspx";
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
